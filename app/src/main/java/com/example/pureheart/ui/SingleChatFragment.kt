package com.example.pureheart.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pureheart.databinding.FragmentSingleChatBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.models.User
import com.example.pureheart.utilits.*
import com.google.firebase.database.DatabaseReference


class SingleChatFragment(private  val contact: CommonModel) : Fragment() {

    private var _binding: FragmentSingleChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var  mListenerInfoToolbar:AppValueEventListener
    private lateinit var  mReceivingUser: User
    private lateinit var  mRefUser:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showToast("Chat")
        mListenerInfoToolbar =  AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfo()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
        binding.chatImageView.setOnClickListener{
            val message = binding.chatInputMessage.text.toString()
            if(message.isEmpty()){
                showToast("Введите сообщение")
            }else sendMessage(message, contact.id, TYPE_TEXT){
                binding.chatInputMessage.setText("")
            }
        }
        return root
    }




    private fun initInfo() {

        if(mReceivingUser.fullname.isEmpty()){
            binding.userNameChat.text = contact.fullname
        }else  binding.userNameChat.text = mReceivingUser.fullname

        binding.toolbarChatImage.donwloadAndSetImage(mReceivingUser.photoUrl)
        binding.settingsStatusChat.text = mReceivingUser.state

    }



    override fun onPause() {
        super.onPause()
        mRefUser.removeEventListener(mListenerInfoToolbar)
    }

}