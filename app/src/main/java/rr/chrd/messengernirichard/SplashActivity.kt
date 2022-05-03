package rr.chrd.messengernirichard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import rr.chrd.messengernirichard.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var _splash : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _splash = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_splash.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

//        Handler().postDelayed({
//            val intent = Intent(this,RegisterActivity::class.java)
//            startActivity(intent)
//            finish()
//        },3000)
        _splash.splashRegisterButton.setOnClickListener {
            val intent = Intent (this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        _splash.splashLoginButton.setOnClickListener {
            val intent = Intent (this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}