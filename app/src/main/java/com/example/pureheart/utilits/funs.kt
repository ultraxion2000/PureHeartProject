package com.example.pureheart.utilits

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pureheart.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

fun showToast(message: String) {
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack:Boolean = true) {
    if(addStack){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.nav_host_fragment_content_main,
                fragment
            ).commit()
    }else{
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.nav_host_fragment_content_main,
                fragment
            ).commit()
    }

}

fun Fragment.replaceFragment(fragment: Fragment) {
    this.fragmentManager?.beginTransaction()
        ?.addToBackStack(null)
        ?.replace(
            R.id.nav_host_fragment_content_main,
            fragment
        )?.commit()
}

fun CircleImageView.downloadAndSetImage(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.love_heart_icon_178900)
        .into(this)
}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun getPlurals(count: Int) = APP_ACTIVITY.resources.getQuantityString(
    R.plurals.count_members,count,count
)