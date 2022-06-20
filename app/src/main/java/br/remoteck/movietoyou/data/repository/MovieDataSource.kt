package br.remoteck.movietoyou.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.remoteck.movietoyou.data.api.FIRST_PAGE
import br.remoteck.movietoyou.data.api.MovieApi
import br.remoteck.movietoyou.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
  private val apiService: MovieApi, private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

  private var page = FIRST_PAGE

  val networkState: MutableLiveData<NetworkState> = MutableLiveData()

  override fun loadInitial(
    params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>
  ) {
    networkState.postValue(NetworkState.LOADING)

    compositeDisposable.add(
      apiService.getPopularMovie(page).subscribeOn(Schedulers.io()).subscribe({
        callback.onResult(it.movieList, null, page + 1)
        networkState.postValue(NetworkState.LOADED)
      }, {
        networkState.postValue(NetworkState.ERROR)
        it.message?.let { it1 -> Log.e("MovieDataSource", it1) }
      })
    )
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    networkState.postValue(NetworkState.LOADING)

    compositeDisposable.add(
      apiService.getPopularMovie(params.key).subscribeOn(Schedulers.io()).subscribe({
        if(it.totalPages >= params.key) {
          callback.onResult(it.movieList, params.key + 1)
          networkState.postValue(NetworkState.LOADED)
        } else {
          networkState.postValue(NetworkState.ENDOFLIST)
        }
      }, {
        networkState.postValue(NetworkState.ERROR)
        it.message?.let { it1 -> Log.e("MovieDataSource", it1) }
      })
    )
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

  }

}