package rr.chrd.messengernirichard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import rr.chrd.messengernirichard.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit  var _binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _binding.loginButton.setOnClickListener {
            loginFun()
        }
        _binding.backRegister.setOnClickListener {
            finish()
        }
    }
    private fun loginFun() {
        val email = _binding.loginEmail.text.toString()
        val password = _binding.loginPassword.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
    }
}