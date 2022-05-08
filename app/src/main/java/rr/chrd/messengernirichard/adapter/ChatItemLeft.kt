package rr.chrd.messengernirichard.adapter

import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chatview_left.view.*
import kotlinx.android.synthetic.main.chatview_left.view.chat_picture
import rr.chrd.messengernirichard.R
import rr.chrd.messengernirichard.model.ChatModel

class ChatItemLeft (val chat : ChatModel, val listener: ChatItemRight.Listener): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder.itemView){
            listener.onImageLoaded(chat.senderId!!){
                Picasso.get()
                    .load(it).into(chat_picture)
            }
            senderView.text = chat.text
        }
    }
    override fun getLayout(): Int {
        return R.layout.chatview_left
    }
}