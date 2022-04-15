package com.example.pureheart.ui.code

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pureheart.MainActivity
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentChangeBioBinding
import com.example.pureheart.databinding.FragmentCodeBinding
import com.example.pureheart.ui.profile.ProfileFragment
import com.example.pureheart.utilits.*


class CodeFragment : Fragment() {

    private var _binding: FragmentCodeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        _binding = FragmentCodeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.inputCode.setText(USER.code)


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
        val newCode = binding.inputCode.text.toString()
        val newStatus = binding.userStatus.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATUS).setValue(newStatus)
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_CODE).setValue(newCode)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    showToast("Данные обновлены")
                    USER.code = newCode
                    USER.status = newStatus
                    fragmentManager?.popBackStack()
                }
            }

    }

}