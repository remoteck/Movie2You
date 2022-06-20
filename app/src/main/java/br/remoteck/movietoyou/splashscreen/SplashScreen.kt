package br.remoteck.movietoyou.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.remoteck.movietoyou.R
import br.remoteck.movietoyou.ui.popular_movie.MainActivity
import kotlinx.android.synthetic.main.splash_screen.*

class SplashScreen : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.splash_screen)

    ic_splash_screen.alpha = 0f
    ic_splash_screen.animate().setDuration(2000).alpha(1f).withEndAction {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
      overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
      finish()
    }
  }
}