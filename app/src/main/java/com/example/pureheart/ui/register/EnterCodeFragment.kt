package com.example.pureheart.ui.register

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.pureheart.activities.MainActivity
import com.example.pureheart.R
import com.example.pureheart.activities.RegisterActivity
import com.example.pureheart.utilits.*
import com.google.firebase.auth.PhoneAuthProvider


class EnterCodeFragment(private val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {

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
        val code = register_input_code?.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                
                REF_DATABASE_ROOT.child(NODE_USERS).child(uid)
                    .addListenerForSingleValueEvent(AppValueEventListener{
                        if(!it.hasChild(CHILD_USERNAME)){
                            dateMap[CHILD_USERNAME] = uid
                        }
                        REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                            .addOnFailureListener { showToast(it.message.toString()) }
                            .addOnSuccessListener {
                                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                                    .addOnSuccessListener {
                                        showToast("Добро пожаловать")
                                        (activity as RegisterActivity).replaceActivity(MainActivity())
                                    }
                                    .addOnFailureListener {showToast(it.message.toString())}
                            }
                    })


            } else showToast(task.exception?.message.toString())
        }
    }
}