package br.remoteck.movietoyou.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.remoteck.movietoyou.data.api.MovieApi
import br.remoteck.movietoyou.data.api.MovieClient
import br.remoteck.movietoyou.data.api.POSTER_BASE_URL
import br.remoteck.movietoyou.data.repository.NetworkState
import br.remoteck.movietoyou.data.vo.MovieDetails
import br.remoteck.movietoyou.databinding.SingleMovieDetailsBinding
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*

class SingleMovieDetails : AppCompatActivity() {

  private lateinit var viewModel: SingleMovieDetailsViewModel
  private lateinit var movieDetailsRepository: MovieDetailsRepository
  private lateinit var binding: SingleMovieDetailsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = SingleMovieDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val movieId: Int = intent.getIntExtra("id", 1)

    val apiService: MovieApi = MovieClient.getClient()
    movieDetailsRepository = MovieDetailsRepository(apiService)

    viewModel = getViewModel(movieId)
    viewModel.movieDetails.observe(this, Observer {
      bindUI(it)
    })

    viewModel.networkState.observe(this, Observer {
      binding.progressBar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
      binding.txtError.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
    })

  }

  private fun bindUI(it: MovieDetails) {
    binding.movieTitle.text = it.title
    binding.movieTagline.text = it.tagline
    binding.movieReleaseDate.text = it.releaseDate
    binding.movieRating.text = it.voteAverage.toString()
    binding.movieRuntime.text = it.runtime.toString() + " minutes"
    binding.movieOverview.text = it.overview

    val formatCurrency: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    binding.movieBudget.text = formatCurrency.format(it.budget)

    val moviePosterURL = POSTER_BASE_URL + it.posterPath
    Glide.with(this).load(moviePosterURL).into(binding.ivMoviePoster)
  }

  private fun getViewModel(movieId: Int): SingleMovieDetailsViewModel {
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") return SingleMovieDetailsViewModel(
          movieDetailsRepository, movieId
        ) as T
      }
    })[SingleMovieDetailsViewModel::class.java]
  }
}