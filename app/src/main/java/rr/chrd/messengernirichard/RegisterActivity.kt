package rr.chrd.messengernirichard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import rr.chrd.messengernirichard.databinding.ActivityMainBinding
import rr.chrd.messengernirichard.model.User
import rr.chrd.messengernirichard.view.LatestMessageActivity
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

        supportActionBar?.hide()

    }
    private fun selectPhoto() {
        _binding.selectPhoto.setOnClickListener {
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
            _binding.selectPhotoUnder.setImageBitmap(bitmap)
            _binding.selectPhoto.alpha = 0f

//            val bitmapDrawable = BitmapDrawable(bitmap)
//            _binding.selectPhotoUnder.setBackgroundDrawable(bitmapDrawable)
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
                    Toast.makeText(this,"Successfully created user account",Toast.LENGTH_LONG).show()
                    Log.d("RegisterActivity","Successfully created user with uid: ${it.user!!.uid}")
                    uploadImageToFirebase()
                }
                .addOnFailureListener{
                Toast.makeText(this,it.message.toString(),Toast.LENGTH_LONG).show()}
        }
    }
        private fun uploadImageToFirebase() {
            if (selectedPhotoUri == null) {
                saveUsertoFirebase("")
                return
            }
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/image/$filename")
            ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Uploaded", Toast.LENGTH_LONG).show()
                    Log.d("RegisterActivity","Successfully uploaded Image : ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener { url ->
                        Log.d("RegisterActivity", "File Location: $url")
                        saveUsertoFirebase(url.toString())
                    }
                }
                .addOnProgressListener {
                    val progress = 100 * (it.bytesTransferred/it.totalByteCount)
                    Log.d("RegisterActivity", "Upload Progress: $progress")
                }
                .addOnFailureListener {
                    Log.d("RegisterActivity", "Error: ${it.message}")
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
                }
    }

    private fun saveUsertoFirebase(profileImageUrl : String) {
        val uid = FirebaseAuth.getInstance().uid?: ""

//        if(uid.isNullOrEmpty()) {
//            Log.d("RegisterActivity", "UID is empty")
//            return
//        }
        val ref = FirebaseDatabase.getInstance().reference.child("user").child(uid)
        val users = User(uid,_binding.editUsername.text.toString(),_binding.editEmail.text.toString(),profileImageUrl)
        ref.setValue(users)
            .addOnSuccessListener {
                Log.d("RegisterActivity","Successfully saved user to database")
                val intent = Intent(this,LatestMessageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Error: ${it.message}")
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
            }
    }
}



