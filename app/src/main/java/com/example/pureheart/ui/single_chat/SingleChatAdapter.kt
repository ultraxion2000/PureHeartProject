package com.example.pureheart.ui.single_chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentSingleChatBinding
import com.example.pureheart.databinding.MassageItemBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.utilits.CURRENT_UID
import com.example.pureheart.utilits.DIFUTILCALLBACK
import com.example.pureheart.utilits.asTime
import kotlinx.android.synthetic.main.massage_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var listMessagesCache = emptyList<CommonModel>()
    private lateinit var mDiffResult: DiffUtil.DiffResult

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blocUserMessage: ConstraintLayout = view.block_user_message
        val chatUserMassage: TextView = view.chat_user_message
        val chatUserTime: TextView = view.chat_time

        val blocReceivedMessage: ConstraintLayout = view.block_received_message
        val chatReceivedMessage: TextView = view.chat_received_message
        val chatReceivedTime: TextView = view.chat_received_time

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.massage_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (listMessagesCache[position].from == CURRENT_UID) {
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMassage.text = listMessagesCache[position].text
            holder.chatUserTime.text =
                listMessagesCache[position].timeStamp.toString().asTime()
        }else{
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = listMessagesCache[position].text
            holder.chatReceivedTime.text =
                listMessagesCache[position].timeStamp.toString().asTime()
        }
    }

    override fun getItemCount(): Int = listMessagesCache.size


    fun setList(list: List<CommonModel>) {
        mDiffResult.dispatchUpdatesTo(this)
        listMessagesCache = list
    }
    fun addItem(item:CommonModel){
        val newList = mutableListOf<CommonModel>()
        newList.addAll(listMessagesCache)
        newList.add(item)
        mDiffResult = DiffUtil.calculateDiff(DIFUTILCALLBACK(listMessagesCache, newList))
        mDiffResult.dispatchUpdatesTo(this)
        listMessagesCache = newList
    }

}


