package com.example.pureheart.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pureheart.databinding.FragmentHelpBinding
import com.example.pureheart.utilits.showToast


class HelpFragment : Fragment() {

    private lateinit var helpViewModel: HelpViewModel
    private var _binding: FragmentHelpBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        helpViewModel =
            ViewModelProvider(this).get(HelpViewModel::class.java)

        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textSlideshow
       helpViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        showToast("Help")
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}