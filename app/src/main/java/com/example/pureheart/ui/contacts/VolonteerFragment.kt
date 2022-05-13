package com.example.pureheart.ui.contacts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentVolonteerBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.ui.single_chat.SingleChatFragment
import com.example.pureheart.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView


class VolonteerFragment : Fragment() {

    private lateinit var mRecycleView: RecyclerView
    private lateinit var mAdapter:FirebaseRecyclerAdapter<CommonModel, ContactsHolder>
    private lateinit var mRefContacts:DatabaseReference
    private lateinit var mRefUsers:DatabaseReference
    private lateinit var mRefUsersListener: AppValueEventListener
    private  var mapListeners = hashMapOf<DatabaseReference, AppValueEventListener>()

    private var _binding: FragmentVolonteerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVolonteerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showToast("Volunteer")

        initRecycleView()
        return root
    }


    private fun initRecycleView() {
        mRecycleView = binding.volonteerRecycleView
        mRefContacts = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java)
            .build()

        mAdapter = object:FirebaseRecyclerAdapter<CommonModel, ContactsHolder>(options){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
               val view = LayoutInflater.from(parent.context).inflate(R.layout.volonteer_item,parent,false)
                return ContactsHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactsHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)

                mRefUsersListener = AppValueEventListener {

                    val contact = it.getCommonModel()

                    if(contact.fullname.isEmpty()){
                        holder.name.text = model.fullname
                    }else holder.name.text = contact.fullname

                    holder.status.text = contact.state
                    holder.photo.downloadAndSetImage(contact.photoUrl)
                    holder.itemView.setOnClickListener{replaceFragment(SingleChatFragment(model))}
                }

                mRefUsers.addValueEventListener(mRefUsersListener)
                mapListeners[mRefUsers] = mRefUsersListener

            }

        }

    mRecycleView.adapter = mAdapter
        mAdapter.startListening()
    }

    class ContactsHolder(view: View):RecyclerView.ViewHolder(view){

        val name: TextView = view.findViewById(R.id.volonteer_fullname)
        val status:TextView = view.findViewById(R.id.vol_st)
        val photo:CircleImageView = view.findViewById(R.id.volonteer_photo)
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
        mapListeners.forEach{
            it.key.removeEventListener(it.value)
        }
    }

}




