package rr.chrd.messengernirichard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import rr.chrd.messengernirichard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  _binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.registerButton.setOnClickListener {

            val email = _binding.editUsername.text.toString()
            val password = _binding.editPassword.text.toString()

            Log.d("Main Activity", "emails is: " + email)
            Log.d("Main Activity", "password:$password")
        }
        _binding.registerButton.setOnClickListener {
            Log.d("MainActivity", "Try to show login activity")
            startFun()
        }

    }

    private fun startFun() {
        val intent = Intent (this,LoginActivity::class.java)
        startActivity(intent)
    }
}