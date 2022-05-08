package rr.chrd.messengernirichard.adapter

import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import rr.chrd.messengernirichard.R
import rr.chrd.messengernirichard.databinding.MessageViewBinding
import rr.chrd.messengernirichard.model.User

class UserItem(val user : User): Item<GroupieViewHolder>(){
    lateinit var binding: MessageViewBinding
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        binding = MessageViewBinding.bind(viewHolder.itemView)
        binding.username.text = user.username
        if(!user.profileImageUrl.isNullOrEmpty()) Picasso.get().load(user.profileImageUrl).into(binding.profilePicture)
    }
    override fun getLayout(): Int {
        return R.layout.message_view
    }
}