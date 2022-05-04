package com.example.pureheart.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pureheart.MainActivity
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentProfileBinding
import com.example.pureheart.ui.bio.ChangeBioFragment
import com.example.pureheart.ui.code.CodeFragment
import com.example.pureheart.ui.name.ChangeNameFragment
import com.example.pureheart.ui.name.ChangeUsernameFragment
import com.example.pureheart.utilits.*
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initFields()

        showToast("Profile")
        return root
    }

    private fun initFields() {
        binding.profileBio.text = USER.bio
        binding.userName.text = USER.fullname
        binding.profilePhone.text = USER.phone
        binding.settingsStatus.text = USER.state
        binding.profileLogin.text = USER.username
        binding.profileCode.text =  USER.code
        binding.userSt.text = USER.status

        binding.profileBtnLogin.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        binding.profileBtnBio.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        binding.profilePhoto.setOnClickListener { changePhotoUser() }
        binding.profileImage.donwloadAndSetImage(USER.photoUrl)

    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(250, 250)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start((activity as MainActivity),this)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
            R.id.settings_menu_change_code -> replaceFragment(CodeFragment())
        }
        return false
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK && data != null) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)

          putImageToStorage(uri, path){
              getUrlFromStorage(path){
                  putUrlToDatabase(it){
                    binding.profileImage.donwloadAndSetImage(it)
                          showToast("Данные обновлены")
                          USER.photoUrl = it
                  }
              }
          }
        }
    }

    private fun putUrlToDatabase(url: String, function: () -> Unit) {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .child(CHILD_PHOTO_URL).setValue(url)
            .addOnSuccessListener { function() }
            .addOnFailureListener{showToast(it.message.toString())}
    }

    private fun getUrlFromStorage(path: StorageReference, function: (url:String) -> Unit) {
        path.downloadUrl
            .addOnSuccessListener { function(it.toString()) }
            .addOnFailureListener{showToast(it.message.toString())}
    }

    private fun putImageToStorage(uri: Uri, path: StorageReference, function: () -> Unit) {
        path.putFile(uri)
            .addOnSuccessListener { function() }
            .addOnFailureListener{showToast(it.message.toString())}
    }

}

