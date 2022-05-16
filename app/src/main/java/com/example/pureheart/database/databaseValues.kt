package com.example.pureheart.utilits

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.ContactsContract
import com.example.pureheart.models.CommonModel
import com.example.pureheart.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: User

const val NODE_USERS = "users"
const val NODE_USERNAMES = "usernames"
const val NODE_PHONES = "phones"
const val NODE_MESSAGES = "messages"
const val FOLDER_PROFILE_IMAGE = "profile_image"
const val NODE_PHONES_CONTACTS = "phones_contacts"

const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"
const val CHILD_BIO = "bio"
const val CHILD_PHOTO_URL = "photoUrl"
const val CHILD_STATE = "state"
const val CHILD_CODE = "code"
const val CHILD_STATUS = "status"
const val CHILD_TEXT = "text"
const val CHILD_TYPE = "type"
const val CHILD_FROM = "from"
const val CHILD_TIMESTAMP = "timeStamp"
const val CHILD_IMAGE_URL = "imageUrl"
const val TYPE_TEXT = "text"

const val FOLDER_MESSAGE_IMAGE = "message_image"

const val NODE_MAIN_LIST = "main_list"
const val NODE_GROUPS = "groups"
const val NODE_MEMBERS = "members"
const val FOLDER_GROUPS_IMAGE = "groups_image"
const val USER_CREATOR = "creator"
const val USER_ADMIN = "admin"
const val USER_MEMBER = "members"


