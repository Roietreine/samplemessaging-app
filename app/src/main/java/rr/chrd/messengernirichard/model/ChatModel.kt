package rr.chrd.messengernirichard.model

data class ChatModel (
    val id : String? = "",
    val text : String? = "",
    val senderId : String? = "",
    val receiverId : String? = "",
    val timeStamp : Long = -1
        )