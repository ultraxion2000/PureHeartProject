package com.example.pureheart.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pureheart.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

  private lateinit var profileViewModel: ProfileViewModel
private var _binding: FragmentProfileBinding? = null

  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

    _binding = FragmentProfileBinding.inflate(inflater, container, false)
    val root: View = binding.root

    //val textView: TextView = binding.textProfile
    profileViewModel.text.observe(viewLifecycleOwner, Observer {
      //textView.text = it
    })
    Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show()
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

