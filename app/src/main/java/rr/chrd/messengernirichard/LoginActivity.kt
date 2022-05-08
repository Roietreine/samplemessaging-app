package rr.chrd.messengernirichard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import rr.chrd.messengernirichard.databinding.ActivityLoginBinding
import rr.chrd.messengernirichard.view.LatestMessageActivity

class LoginActivity : AppCompatActivity() {

    private lateinit  var _binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        supportActionBar?.hide()

        _binding.loginButton.setOnClickListener {
            _binding.progressBar.visibility = View.VISIBLE
            loginFun()
        }
        _binding.backRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loginFun() {
        val email = _binding.loginEmail.text.toString()
        val password = _binding.loginPassword.text.toString()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnSuccessListener{
                _binding.progressBar.visibility = View.GONE
                val intent = Intent(this,LatestMessageActivity::class.java)
                startActivity(intent)
                Log.d("Main","Succesfully login user with uid: ${it.user!!.uid}")
            }
            .addOnFailureListener{
                Toast.makeText(this,"Please enter a correct email/password", Toast.LENGTH_LONG).show()}
    }
}
