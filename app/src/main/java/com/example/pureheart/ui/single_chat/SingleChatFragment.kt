package com.example.pureheart.ui.single_chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pureheart.R
import com.example.pureheart.activities.MainActivity
import com.example.pureheart.databinding.FragmentSingleChatBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.models.User
import com.example.pureheart.ui.main_list.MainListFragment
import com.example.pureheart.utilits.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.app_bar_main.*


class SingleChatFragment(private val contact: CommonModel) : Fragment() {

    private var _binding: FragmentSingleChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    private lateinit var mReceivingUser: User
    private lateinit var mRefUser: DatabaseReference

    private lateinit var mRefMessages: DatabaseReference
    private lateinit var mAdapter: SingleChatAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mMessagesListener: ChildEventListener
    private var mCountMessages = 10
    private var mIsScrolling = false
    private var mSmoothScrollToPosition = true
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentSingleChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showToast("Chat")


        initFields()
        initToolbar()
        initRecycleView()


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_back, menu)
        (activity as MainActivity).menuInflater.inflate(R.menu.remove, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_confirm_back -> changeB()
            R.id.menu_clear_chat -> clearChat(contact.id){
                showToast("Чат очищен")
                fragmentManager?.popBackStack()

            }
            R.id.menu_delete_chat -> deleteChat(contact.id){
                showToast("Чат удален")
                fragmentManager?.popBackStack()

            }
        }
        return true
    }



    private fun changeB() {
           APP_ACTIVITY.supportFragmentManager.popBackStack()
    }

    private fun initFields() {
        mSwipeRefreshLayout = binding.chatSwipeRefresh
        mLayoutManager = LinearLayoutManager(this.context)
        binding.chatInputMessage.addTextChangedListener(AppTextWatcher {
            val string = binding.chatInputMessage.text.toString()
            if (string.isEmpty()) {
                binding.chatImageView.visibility = View.GONE
                binding.btnPhoto.visibility = View.VISIBLE
            } else {
                binding.chatImageView.visibility = View.VISIBLE
                binding.btnPhoto.visibility = View.GONE
            }
        })
        binding.btnPhoto.setOnClickListener { attachFile() }

    }


    private fun attachFile() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .start((activity as MainActivity), this)
    }

    private fun initToolbar() {
        mListenerInfoToolbar = AppValueEventListener {
            mReceivingUser = it.getUserModel()
            initInfo()
        }
        mRefUser = REF_DATABASE_ROOT.child(NODE_USERS).child(contact.id)
        mRefUser.addValueEventListener(mListenerInfoToolbar)
        binding.chatImageView.setOnClickListener {
            mSmoothScrollToPosition = true
            val message = binding.chatInputMessage.text.toString()
            if (message.isEmpty()) {
                showToast("Введите сообщение")
            } else sendMessage(message, contact.id, TYPE_TEXT) {

                saveToMainList(contact.id, TYPE_CHAT)

                binding.chatInputMessage.setText("")
            }
        }
    }



    private fun initRecycleView() {
        mRecyclerView = binding.chatRecycleView
        mAdapter = SingleChatAdapter()
        mRefMessages = REF_DATABASE_ROOT
            .child(NODE_MESSAGES)
            .child(CURRENT_UID)
            .child(contact.id)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false

        mRecyclerView.layoutManager = mLayoutManager

        mMessagesListener = AppChildEventListener {

            val massage = it.getCommonModel()

            if (mSmoothScrollToPosition) {
                mAdapter.addItemToBottom(massage)
                {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            } else {
                mAdapter.addItemToTop(massage) {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            }
        }


        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    mIsScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mIsScrolling && dy < 0 && mLayoutManager.findFirstVisibleItemPosition() <= 3) {
                    updateData()
                }
            }
        })

        mSwipeRefreshLayout.setOnRefreshListener { updateData() }
    }

    private fun updateData() {
        mSmoothScrollToPosition = false
        mIsScrolling = false
        mCountMessages += 10
        mRefMessages.removeEventListener(mMessagesListener)
        mRefMessages.limitToLast(mCountMessages).addChildEventListener(mMessagesListener)

    }


    private fun initInfo() {

        if (mReceivingUser.fullname.isEmpty()) {
            binding.userNameChat.text = contact.fullname
        } else binding.userNameChat.text = mReceivingUser.fullname

        binding.toolbarChatImage.downloadAndSetImage(mReceivingUser.photoUrl)
        binding.settingsStatusChat.text = mReceivingUser.state

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK && data != null
        ) {

            val uri = CropImage.getActivityResult(data).uri
            val messageKey = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
                .child(contact.id).push().key.toString()

            val path = REF_STORAGE_ROOT
                .child(FOLDER_MESSAGE_IMAGE)
                .child(messageKey)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    sendMessageAsImage(contact.id,it,messageKey)
                    mSmoothScrollToPosition = true
                }
            }
        }
    }




    private fun getUrlFromStorage(path: StorageReference, function: (url: String) -> Unit) {
        path.downloadUrl
            .addOnSuccessListener { function(it.toString()) }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    private fun putImageToStorage(uri: Uri, path: StorageReference, function: () -> Unit) {
        path.putFile(uri)
            .addOnSuccessListener { function() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }


    override fun onPause() {
        super.onPause()
        mRefUser.removeEventListener(mListenerInfoToolbar)
        mRefMessages.removeEventListener(mMessagesListener)
    }


}