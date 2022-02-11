package com.example.pureheart.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pureheart.R


class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {



    override fun onStart() {
        super.onStart()
        val register_input_code =
            view?.findViewById<EditText>(R.id.register_input_code)

        register_input_code?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val string = register_input_code.text.toString()
            if (string.length==6){
                verifiCode()
            }
            }

        })
    }
    fun verifiCode(){
        Toast.makeText(activity, "Ок", Toast.LENGTH_SHORT).show()
    }
}