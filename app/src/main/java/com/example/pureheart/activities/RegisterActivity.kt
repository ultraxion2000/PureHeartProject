package com.example.pureheart.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pureheart.databinding.ActivityRegisterBinding
import com.example.pureheart.ui.register.EnterPhoneNumberFragment
import com.example.pureheart.utilits.initFirebase
import com.example.pureheart.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()

    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction()

        replaceFragment(EnterPhoneNumberFragment())
    }

}


