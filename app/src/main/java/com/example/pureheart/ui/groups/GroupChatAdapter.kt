package com.example.pureheart.ui.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.models.CommonModel
import com.example.pureheart.utilits.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.massage_item.view.*


class GroupChatAdapter : RecyclerView.Adapter<GroupChatAdapter.GroupChatHolder>() {


    private var listMessagesCache = mutableListOf<CommonModel>()


    class GroupChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Text
        val blocUserMessage: ConstraintLayout = view.block_user_message
        val chatUserMassage: TextView = view.chat_user_message
        val chatUserTime: TextView = view.chat_time
        val blocReceivedMessage: ConstraintLayout = view.block_received_message
        val chatReceivedMessage: TextView = view.chat_received_message
        val chatReceivedTime: TextView = view.chat_received_time

        //Image
        val blocReceivedImageMessage: ConstraintLayout = view.block_received_image_message
        val blocUserImageMessage: ConstraintLayout = view.block_user_image_message
        val chatUserImage: ImageView = view.chat_user_image
        val chatReceivedImage: ImageView = view.chat_received_image
        val chatUserImageMessageTime: TextView = view.chat_user_image_time
        val chatReceivedImageMessageTime: TextView = view.chat_received_image_time


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.massage_item, parent, false)
        return GroupChatHolder(view)
    }

    override fun getItemCount(): Int = listMessagesCache.size

    override fun onBindViewHolder(holder: GroupChatHolder, position: Int) {
        when (listMessagesCache[position].type) {
            TYPE_MESSAGE_TEXT -> drawMessageText(holder, position)
            TYPE_MESSAGE_IMAGE -> drawMessageImage(holder, position)
        }

    }

    private fun drawMessageImage(holder: GroupChatHolder, position: Int) {
        holder.blocUserMessage.visibility = View.GONE
        holder.blocReceivedMessage.visibility = View.GONE
        if (listMessagesCache[position].from == CURRENT_UID) {
            holder.blocReceivedImageMessage.visibility = View.GONE
            holder.blocUserImageMessage.visibility = View.VISIBLE
            holder.chatUserImage.downloadAndSetImage2(listMessagesCache[position].imageUrl)
            holder.chatUserImageMessageTime.text =
                listMessagesCache[position].timeStamp.toString().asTime()

        } else {
            holder.blocReceivedImageMessage.visibility = View.VISIBLE
            holder.blocUserImageMessage.visibility = View.GONE
            holder.chatReceivedImage.downloadAndSetImage2(listMessagesCache[position].imageUrl)
            holder.chatReceivedImageMessageTime.text =
                listMessagesCache[position].timeStamp.toString().asTime()
        }
    }


    private fun drawMessageText(holder: GroupChatHolder, position: Int) {
        holder.blocReceivedImageMessage.visibility = View.GONE
        holder.blocUserImageMessage.visibility = View.GONE
        if (listMessagesCache[position].from == CURRENT_UID) {
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMassage.text = listMessagesCache[position].text
            holder.chatUserTime.text =
                listMessagesCache[position].timeStamp.toString().asTime()
        } else {
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = listMessagesCache[position].text
            holder.chatReceivedTime.text =
                listMessagesCache[position].timeStamp.toString().asTime()
        }
    }

    fun addItemToBottom(item: CommonModel, onSuccess: () -> Unit) {
        if (!listMessagesCache.contains(item)) {
            listMessagesCache.add(item)
            notifyItemInserted(listMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: CommonModel, onSuccess: () -> Unit) {
        if (!listMessagesCache.contains(item)) {
            listMessagesCache.add(item)
            listMessagesCache.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }


}

private fun ImageView.downloadAndSetImage2(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.camera_14117)
        .into(this)
}


