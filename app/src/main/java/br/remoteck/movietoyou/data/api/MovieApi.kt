package br.remoteck.movietoyou.data.api

import br.remoteck.movietoyou.data.vo.MovieDetails
import br.remoteck.movietoyou.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {


  @GET("movie/popular")
  fun getPopularMovie(@Query("page")page: Int): Single<MovieResponse>

  @GET("movie/{movie_id}")
  fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

}