package com.example.pureheart.ui.name

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.pureheart.MainActivity
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentChangeNameBinding
import com.example.pureheart.utilits.*


class ChangeNameFragment : Fragment() {

    private var _binding: FragmentChangeNameBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentChangeNameBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val fullnameList = USER.fullname.split(" ")
        if(fullnameList.size>1) {
            binding.settingsInputName.setText(fullnameList[0])
            binding.settingsInputSurname.setText(fullnameList[1])
        }else binding.settingsInputName.setText(fullnameList[0])

        showToast("Изменить имя")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_confirm_change -> changeName()
        }
        return true
    }

    private fun changeName() {

        val name = binding.settingsInputName.text.toString()
        val surname = binding.settingsInputSurname.text.toString()

        if (name.isEmpty()) {
            showToast("Имя не может быть пустым")
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Данные обновлены")
                        USER.fullname = fullname
                        fragmentManager?.popBackStack()
                    }

                }
        }

    }
}