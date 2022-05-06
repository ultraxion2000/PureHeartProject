package com.example.pureheart.ui.register

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.pureheart.activities.MainActivity
import com.example.pureheart.R
import com.example.pureheart.activities.RegisterActivity
import com.example.pureheart.utilits.AUTH
import com.example.pureheart.utilits.replaceActivity
import com.example.pureheart.utilits.replaceFragment
import com.example.pureheart.utilits.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {
    @SuppressLint("CutPasteId")

    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onStart() {
        super.onStart()

        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
               AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Добро пожаловать")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else showToast(task.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }

        var btn = view?.findViewById<Button>(R.id.btnRegister)
        val registerNumber = view?.findViewById<EditText>(R.id.register_input_phone_number)

        btn?.setOnClickListener {
            if (registerNumber?.text.toString().isEmpty()) {
                showToast("Введите номер телефона")
            } else {
                authUser()
            }
        }
    }

    private fun authUser() {
        val registerNumber = view?.findViewById<EditText>(R.id.register_input_phone_number)
        mPhoneNumber = registerNumber?.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            mCallback
        )
    }
}