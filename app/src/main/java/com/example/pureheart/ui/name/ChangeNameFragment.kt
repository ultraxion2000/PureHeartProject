package com.example.pureheart.ui.name

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        val registerName = view?.findViewById<EditText>(R.id.settings_input_name)
        val registerSurName = view?.findViewById<EditText>(R.id.settings_input_surname)
        val name = registerName?.text.toString()
        val surname = registerSurName?.text.toString()
        if (name.isEmpty()) {
            showToast("Имя не может быть пустым")
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
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