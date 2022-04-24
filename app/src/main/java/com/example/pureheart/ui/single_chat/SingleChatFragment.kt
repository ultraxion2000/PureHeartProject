package com.example.pureheart.ui.single_chat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.databinding.FragmentSingleChatBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.models.User
import com.example.pureheart.utilits.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_single_chat.*


class SingleChatFragment(private val contact: CommonModel) : Fragment() {

    private var _binding: FragmentSingleChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: User
    private lateinit var mRefUser: DatabaseReference

    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: ChildEventListener
    private var mListMessages = mutableListOf<CommonModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showToast("Chat")
        initToolbar()
        initRecycleView()


        return root
    }

    private fun initToolbar() {
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfo()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
        binding.chatImageView.setOnClickListener {
            val message = binding.chatInputMessage.text.toString()
            if (message.isEmpty()) {
                showToast("Введите сообщение")
            } else sendMessage(message, contact.id, TYPE_TEXT) {
                binding.chatInputMessage.setText("")
            }
        }
    }

    private fun initRecycleView() {
        mRecyclerView = binding.chatRecycleView
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT
            .child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        mRecyclerView.adapter = mAdapter

       mMessagesListener = AppChildEventListener{
           mAdapter.addItem(it.getCommonModel())
           mRecyclerView.smoothScrollToPosition(mAdapter.itemCount)
       }

        mRefMessages.addChildEventListener(mMessagesListener)
    }


    private fun initInfo() {

        if (mReceivingUser.fullname.isEmpty()) {
            binding.userNameChat.text = contact.fullname
        } else binding.userNameChat.text = mReceivingUser.fullname

        binding.toolbarChatImage.donwloadAndSetImage(mReceivingUser.photoUrl)
        binding.settingsStatusChat.text = mReceivingUser.state

    }


    override fun onPause() {
        super.onPause()
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessages.removeEventListener(mMessagesListener)
    }

}