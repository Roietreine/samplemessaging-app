package rr.chrd.messengernirichard.adapter

import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chatview_right.view.*
import rr.chrd.messengernirichard.R
import rr.chrd.messengernirichard.model.ChatModel

class ChatItemRight (val chat : ChatModel, val listener: Listener): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder.itemView){
            listener.onImageLoaded(chat.senderId!!){
                Picasso.get()
                    .load(it).into(chat_picture)
            }
            receiveView.text = chat.text
        }
    }
    override fun getLayout(): Int {
        return R.layout.chatview_right
    }

    interface Listener {
        fun onImageLoaded(userId: String,urlCallback: (String) -> Unit)
    }

}