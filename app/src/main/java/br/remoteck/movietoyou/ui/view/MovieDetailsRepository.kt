package br.remoteck.movietoyou.ui.view

import androidx.lifecycle.LiveData
import br.remoteck.movietoyou.data.api.MovieApi
import br.remoteck.movietoyou.data.repository.MovieDetailsNetworkDataSource
import br.remoteck.movietoyou.data.repository.NetworkState
import br.remoteck.movietoyou.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: MovieApi) {
  lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

  fun fetchSingleMovieDetails(
    compositeDisposable: CompositeDisposable, movieId: Int
  ): LiveData<MovieDetails> {
    movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
    movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

    return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
  }

  fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
    return movieDetailsNetworkDataSource.networkState
  }
}