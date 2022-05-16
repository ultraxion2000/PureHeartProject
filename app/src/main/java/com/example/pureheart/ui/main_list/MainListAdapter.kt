package com.example.pureheart.ui.main_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.models.CommonModel
import com.example.pureheart.ui.groups.GroupChatFragment
import com.example.pureheart.ui.single_chat.SingleChatFragment
import com.example.pureheart.utilits.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainListHolder>() {

    private var listItems = mutableListOf<CommonModel>()



    class  MainListHolder(view: View): RecyclerView.ViewHolder(view){
        val itemName: TextView = view.main_list_item_name
        val itemLastMessage: TextView = view.main_list_last_message
        val itemPhoto:CircleImageView = view.main_list_item_photo


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)
        
        val holder = MainListHolder(view)
        holder.itemView.setOnClickListener {
            when(listItems[holder.adapterPosition].type){
                TYPE_CHAT ->  APP_ACTIVITY.replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
                TYPE_GROUP -> APP_ACTIVITY.replaceFragment(GroupChatFragment(listItems[holder.adapterPosition]))
            }
        }

        return holder
    }





    override fun onBindViewHolder(holder: MainListHolder, position: Int) {
        holder.itemName.text = listItems[position].fullname
        holder.itemLastMessage.text = listItems[position].lastMessage
        holder.itemPhoto.downloadAndSetImage(listItems[position].photoUrl)
    }

    override fun getItemCount(): Int = listItems.size

    fun updateListItems(item: CommonModel){
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }
}