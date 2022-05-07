package com.example.pureheart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentChangeBioBinding
import com.example.pureheart.databinding.FragmentChatsBinding


class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onResume(){
        super.onResume()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}