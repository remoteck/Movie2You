package br.remoteck.movietoyou.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.remoteck.movietoyou.data.api.MovieApi
import br.remoteck.movietoyou.data.api.POST_PER_PAGE
import br.remoteck.movietoyou.data.repository.MovieDataSource
import br.remoteck.movietoyou.data.repository.MovieDataSourceFactory
import br.remoteck.movietoyou.data.repository.NetworkState
import br.remoteck.movietoyou.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService: MovieApi) {

  private lateinit var moviePagedList: LiveData<PagedList<Movie>>
  private lateinit var moviesDataSourceFactory: MovieDataSourceFactory

  fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
    moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

    val config: PagedList.Config =
      PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(POST_PER_PAGE).build()

    moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

    return moviePagedList
  }

  fun getNetworkState(): LiveData<NetworkState> {
    return Transformations.switchMap<MovieDataSource, NetworkState>(
      moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
    )
  }

}