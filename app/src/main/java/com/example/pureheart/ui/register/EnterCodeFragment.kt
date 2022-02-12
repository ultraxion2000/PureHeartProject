package com.example.pureheart.ui.register

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pureheart.R
import com.example.pureheart.utilits.AppTextWatcher


class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        val register_input_code =
            view?.findViewById<EditText>(R.id.register_input_code)

        register_input_code?.addTextChangedListener(AppTextWatcher{

                val string = register_input_code.text.toString()
            if (string.length==6){
                verifiCode()
            }
        })
    }
    fun verifiCode(){
        Toast.makeText(activity, "Ок", Toast.LENGTH_SHORT).show()
    }
}