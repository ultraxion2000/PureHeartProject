package com.example.pureheart.ui

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.pureheart.MainActivity
import com.example.pureheart.R
import com.example.pureheart.activities.RegisterActivity
import com.example.pureheart.utilits.AUTH
import com.example.pureheart.utilits.replaceActivity
import com.google.android.material.navigation.NavigationView

class SettingsFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       activity?.menuInflater?.inflate(R.menu.main, menu)
    }

}
