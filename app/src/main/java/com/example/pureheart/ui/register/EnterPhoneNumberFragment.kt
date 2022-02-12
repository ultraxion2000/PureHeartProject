package com.example.pureheart.ui.register

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pureheart.R
import com.example.pureheart.utilits.showToast

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {
    @SuppressLint("CutPasteId")

    override fun onStart() {
        super.onStart()
        var btn = view?.findViewById<Button>(R.id.btnRegister)
        val registerNumber = view?.findViewById<EditText>(R.id.register_input_phone_number)

        btn?.setOnClickListener{
            if (registerNumber?.text.toString().isEmpty()) {
                showToast("Введите номер телефона")
            } else{
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.place_holder, EnterCodeFragment())
                    ?.addToBackStack(null)
                    ?.commit()

        }
    }


    }
}