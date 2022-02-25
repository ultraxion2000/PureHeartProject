package com.example.pureheart.ui.register

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pureheart.MainActivity
import com.example.pureheart.R
import com.example.pureheart.activities.RegisterActivity
import com.example.pureheart.utilits.AUTH
import com.example.pureheart.utilits.AppTextWatcher
import com.example.pureheart.utilits.replaceActivity
import com.example.pureheart.utilits.showToast
import com.google.firebase.auth.PhoneAuthProvider


class EnterCodeFragment(private val phoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()

        (activity as RegisterActivity).title = phoneNumber

        val register_input_code = view?.findViewById<EditText>(R.id.register_input_code)

        register_input_code?.addTextChangedListener(AppTextWatcher {

            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val register_input_code = view?.findViewById<EditText>(R.id.register_input_code)
        val code  = register_input_code?.text.toString()
       val credential = PhoneAuthProvider.getCredential(id,code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Добро пожаловать")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else showToast(task.exception?.message.toString())
        }
    }
}