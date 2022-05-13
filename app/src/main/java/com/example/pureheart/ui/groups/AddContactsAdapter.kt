package com.example.pureheart.ui.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.models.CommonModel
import com.example.pureheart.utilits.downloadAndSetImage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.add_contacts_item.view.*
import kotlinx.android.synthetic.main.main_list_item.view.*

class AddContactsAdapter : RecyclerView.Adapter<AddContactsAdapter.AddContactsHolder>() {

    private var listItems = mutableListOf<CommonModel>()



    class  AddContactsHolder(view: View): RecyclerView.ViewHolder(view){
        val itemName: TextView = view.add_contacts_item_name
        val itemLastMessage: TextView = view.add_contacts_last_message
        val itemPhoto:CircleImageView = view.add_contacts_item_photo
        val itemChoice:CircleImageView = view.add_contacts_item_choice


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddContactsHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.add_contacts_item, parent, false)
            val holder = AddContactsHolder(view)
            holder.itemView.setOnClickListener {
                if(listItems[holder.adapterPosition].choice){
                    holder.itemChoice.visibility = View.INVISIBLE
                    listItems[holder.adapterPosition].choice = false
                    HelpFragment.listContacts.remove(listItems[holder.adapterPosition])
                }else{
                    holder.itemChoice.visibility = View.VISIBLE
                    listItems[holder.adapterPosition].choice = true
                    HelpFragment.listContacts.add(listItems[holder.adapterPosition])
                }
            }
        return holder
    }




    override fun onBindViewHolder(holder: AddContactsHolder, position: Int) {
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