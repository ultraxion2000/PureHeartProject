package com.example.pureheart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentSingleChatBinding
import com.example.pureheart.databinding.FragmentVolonteerBinding
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
        return root
    }

    private fun initInfo() {
        binding.toolbarChatImage.donwloadAndSetImage(mReceivingUser.photoUrl)
        binding.userNameChat.text = mReceivingUser.fullname
        binding.settingsStatusChat.text = mReceivingUser.state

    }

    override fun onPause() {
        super.onPause()
        mRefUser.removeEventListener(mListenerInfoToolbar)
    }

}