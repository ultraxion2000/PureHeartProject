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
import com.example.pureheart.utilits.APP_ACTIVITY
import com.example.pureheart.utilits.showToast


class SingleChatFragment(contact: CommonModel) : Fragment() {

    private var _binding: FragmentSingleChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showToast("Chat")

        return root
    }

    override fun onPause() {
        super.onPause()
    }

}