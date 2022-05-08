package rr.chrd.messengernirichard.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import rr.chrd.messengernirichard.adapter.UserItem
import rr.chrd.messengernirichard.databinding.ActivityMessageBinding
import rr.chrd.messengernirichard.model.User

class MessageActivity : AppCompatActivity() {

    companion object {
        val USER_KEYS = "USER_KEY"
    }
    private lateinit var _binding : ActivityMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        supportActionBar?.title = "Select User"

        val adapts = GroupieAdapter()
        _binding.recyclerNewmessage.adapter = adapts
        fetchUser()
    }
    private fun fetchUser() {
        val ref = FirebaseDatabase.getInstance().getReference("/user")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val adapts = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach {
                    Log.d("New message",it.toString())
                    val user = it.getValue(User::class.java)
                    if(user != null){
                        adapts.add(UserItem(user))
                    }
                }
                adapts.setOnItemClickListener { item, view ->
                    val userItems = item as UserItem
                    val intent = Intent(view.context,ChatLogActivity::class.java)
                    intent.putExtra(USER_KEYS,userItems.user)
                    startActivity(intent)
                }
                _binding.recyclerNewmessage.adapter = adapts
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error", error.message)
            }
        })
    }
}
