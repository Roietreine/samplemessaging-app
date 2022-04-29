package rr.chrd.messengernirichard

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import rr.chrd.messengernirichard.databinding.ActivityMainBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var  _binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        registerButton()
        showLogin()
        selectPhoto()
    }
    private fun selectPhoto() {
        _binding.selectPhotoUnder.setOnClickListener {
            Log.d("RegisterActivity","Try to show photo selector")

            //intent to start activity for result
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }
    }
    var selectedPhotoUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //capture result of activity

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Log.d("RegisterActivity","Photo was selected")

             selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            _binding.selectPhotoUnder.setBackgroundDrawable(bitmapDrawable)
        }
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
                    uploadImageToFirebase()
                }
                .addOnFailureListener{
                Toast.makeText(this,"Please enter a correct email format",Toast.LENGTH_LONG).show()}
        }
    }
        private fun uploadImageToFirebase() {
            val filename = UUID.randomUUID().toString()
            val ref =FirebaseStorage.getInstance().getReference("/images/$filename")
            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d("RegisterActivity","Successfully uploaded Image : ${it.metadata?.path}")}
    }
}