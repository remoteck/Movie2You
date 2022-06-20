package br.remoteck.movietoyou.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.remoteck.movietoyou.data.api.MovieApi
import br.remoteck.movietoyou.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
  private val apiService: MovieApi, private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

  val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

  override fun create(): DataSource<Int, Movie> {
    val movieDataSource = MovieDataSource(apiService, compositeDisposable)

    moviesLiveDataSource.postValue(movieDataSource)
    return movieDataSource
  }

}