package br.remoteck.movietoyou.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.remoteck.movietoyou.data.repository.NetworkState
import br.remoteck.movietoyou.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieDetailsViewModel(
  private val movieDetailsRepository: MovieDetailsRepository, movieId: Int
) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()

  val movieDetails: LiveData<MovieDetails> by lazy {
    movieDetailsRepository.fetchSingleMovieDetails(
      compositeDisposable, movieId
    )
  }

  val networkState: LiveData<NetworkState> by lazy {
    movieDetailsRepository.getMovieDetailsNetworkState()
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.dispose()
  }
}