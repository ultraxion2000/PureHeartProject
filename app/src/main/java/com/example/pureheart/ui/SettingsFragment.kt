package com.example.pureheart.ui

import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import com.example.pureheart.R

class SettingsFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       activity?.menuInflater?.inflate(R.menu.main, menu)
    }
}