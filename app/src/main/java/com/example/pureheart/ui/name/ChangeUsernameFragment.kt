package com.example.pureheart.ui.name

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pureheart.MainActivity
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentChangeNameBinding
import com.example.pureheart.databinding.FragmentChangeUsernameBinding
import com.example.pureheart.utilits.*
import java.util.*


class ChangeUsernameFragment : Fragment() {

    lateinit var mNewUsername: String

    private var _binding: FragmentChangeUsernameBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        setHasOptionsMenu(true)


        _binding = FragmentChangeUsernameBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.settingsInputUsername.setText(USER.username)

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
        mNewUsername = binding.settingsInputUsername.text.toString().toLowerCase(Locale.getDefault())
        if(mNewUsername.isEmpty()){
            showToast("Поле пустое")
        }else{
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener{
                    if (it.hasChild(mNewUsername)){
                        showToast("Такой пользователь уже существует")
                    }else{
                        changeUsername()
                    }
                })
            changeUsername()
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(UID)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    showToast("Данные обновлены")
                    deleteOldUsername()
                }else{
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    showToast("Данные обновлены")
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                }else{
                    showToast(it.exception?.message.toString())
                }
            }
    }
}