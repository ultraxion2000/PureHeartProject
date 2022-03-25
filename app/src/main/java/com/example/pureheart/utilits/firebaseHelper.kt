package com.example.pureheart.utilits

import com.example.pureheart.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


lateinit var AUTH:FirebaseAuth
lateinit var UID:String
lateinit var REF_DATABASE_ROOT:DatabaseReference
lateinit var USER: User

const val NODE_USERS = "users"
const val NODE_USERNAMES =  "USERNAMES"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"

fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}