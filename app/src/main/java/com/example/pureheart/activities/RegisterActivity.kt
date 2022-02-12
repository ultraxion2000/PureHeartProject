package com.example.pureheart.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pureheart.R
import com.example.pureheart.databinding.ActivityRegisterBinding
import com.example.pureheart.ui.register.EnterPhoneNumberFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportFragmentManager.beginTransaction().replace(R.id.place_holder, EnterPhoneNumberFragment())
            .commit()

    }


}


