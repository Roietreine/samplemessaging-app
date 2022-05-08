package rr.chrd.messengernirichard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import rr.chrd.messengernirichard.adapter.ChatItemLeft
import rr.chrd.messengernirichard.adapter.ChatItemRight

import rr.chrd.messengernirichard.databinding.ActivityChatLogBinding
import rr.chrd.messengernirichard.model.ChatModel
import rr.chrd.messengernirichard.model.User

class ChatLogActivity : AppCompatActivity(), ChatItemRight.Listener {

    companion object {
        val TAG = "ChatLog"
    }
    private val user by lazy {
        intent.getParcelableExtra<User>(MessageActivity.USER_KEYS)
    }
    private lateinit var _binding : ActivityChatLogBinding
    private lateinit var adapts : GroupAdapter<GroupieViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatLogBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        sendFun()
        listenMessage()
        val user = intent.getParcelableExtra<User>(MessageActivity.USER_KEYS)
        supportActionBar?.title = user?.username

         adapts = GroupAdapter<GroupieViewHolder>()
        _binding.chatlogRecycler.adapter = adapts
    }
    private fun listenMessage() {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")
        ref.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatModel::class.java)
                Log.d(TAG, chatMessage?.text!!)
                if(fromThisChat(chatMessage.receiverId!!, chatMessage.senderId!!)){
                    if(fromMe(chatMessage.receiverId, chatMessage.senderId))
                        adapts.add(ChatItemRight(chatMessage, this@ChatLogActivity))
                    else adapts.add(ChatItemLeft(chatMessage, this@ChatLogActivity))
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
        })
    }

    fun fromMe(reciever: String, sender: String): Boolean {
        val me = FirebaseAuth.getInstance().currentUser?.uid
        return sender == me && reciever == user?.uid
    }

    fun fromThisChat(reciever: String, sender: String): Boolean{
        val me = FirebaseAuth.getInstance().currentUser?.uid
        return reciever == user?.uid && sender == me ||
                sender == user?.uid && reciever == me
    }
    private fun sendFun() {
        _binding.sendId.setOnClickListener {
            Log.d(TAG,"Try to send a message")
            performSendMessage()
        }
    }
    private fun performSendMessage() {
        val text = _binding.messageText.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val toId = user?.uid
        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val chatMessage = ChatModel(reference.key,text,fromId,toId,System.currentTimeMillis()/1000)
        reference.setValue(chatMessage)

            .addOnSuccessListener {
                Log.d(TAG,"Saved message: ${reference.key}")
                _binding.messageText.text.clear()
                _binding.chatlogRecycler.scrollToPosition(adapts.itemCount -1)
            }
    }
    override fun onImageLoaded(userId: String, urlCallback: (String) -> Unit) {
        val ref = FirebaseDatabase.getInstance().getReference("/user/${userId}/profileImageUrl")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                urlCallback(snapshot.getValue(String::class.java) ?:"")
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}