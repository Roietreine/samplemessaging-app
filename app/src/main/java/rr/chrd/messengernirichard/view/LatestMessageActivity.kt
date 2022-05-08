package rr.chrd.messengernirichard.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import rr.chrd.messengernirichard.LoginActivity
import rr.chrd.messengernirichard.R
import rr.chrd.messengernirichard.RegisterActivity
import rr.chrd.messengernirichard.databinding.ActivityLatestMessageBinding

class LatestMessageActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityLatestMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLatestMessageBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        verifyUserLog()
    }
    private fun verifyUserLog() {
        val uid = FirebaseAuth.getInstance().uid
        if(uid == null){
            val intent = Intent(this,RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.message -> {
                val intent = Intent(this,MessageActivity::class.java)
                startActivity(intent)
            }
            R.id.exit -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}