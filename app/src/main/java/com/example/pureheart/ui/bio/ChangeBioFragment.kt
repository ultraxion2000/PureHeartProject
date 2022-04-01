package com.example.pureheart.ui.bio

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pureheart.MainActivity
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentChangeBioBinding
import com.example.pureheart.utilits.*


class ChangeBioFragment : Fragment() {

    private var _binding: FragmentChangeBioBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentChangeBioBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.settingsInputBio.setText(USER.bio)

        return root

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    private fun change() {
        val newBio = binding.settingsInputBio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    showToast("Данные обновлены")
                    USER.bio = newBio
                    fragmentManager?.popBackStack()
                }
            }
    }

}