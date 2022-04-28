package rr.chrd.messengernirichard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import rr.chrd.messengernirichard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  _binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        registerButton()
        showLogin()
    }
    private fun showLogin() {
        _binding.textAccount.setOnClickListener {
            val intent = Intent (this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun registerButton() {
        _binding.registerButton.setOnClickListener {
            val email = _binding.editEmail.text.toString()
            val password = _binding.editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,"Please enter your desire e-mail and password",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener{
                    Log.d("Main","Succesfully created user with uid: ${it.user!!.uid}")
                }
                .addOnFailureListener{
                Toast.makeText(this,"Please enter a correct email format",Toast.LENGTH_LONG).show()}
        }
    }
}