package com.example.pureheart.ui.main_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.database.getCommonModel
import com.example.pureheart.databinding.FragmentMainListBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.ui.single_chat.SingleChatFragment
import com.example.pureheart.utilits.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_main_list.*
import kotlinx.android.synthetic.main.main_list_item.view.*


class MainListFragment : Fragment() {

    private var _binding: FragmentMainListBinding? = null

    private val binding get() = _binding!!

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentMainListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showToast("Chats")
        initRecyclerView()

        return root
    }

    private fun initRecyclerView() {
        mRecyclerView = binding.mainListRecycleView
        mAdapter = MainListAdapter()

        //1 запрос
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot ->
            mListItems = dataSnapshot.children.map{it.getCommonModel()}
            mListItems.forEach{model ->

                when(model.type){
                    TYPE_CHAT -> showChat(model)
                    TYPE_GROUP -> showGroup(model)
                }


            }
        })

            mRecyclerView.adapter = mAdapter
    }

    private fun showGroup(model: CommonModel) {
        //2 запрос
        REF_DATABASE_ROOT.child(NODE_GROUPS).child(model.id)
            .addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot1 ->
            val newModel = dataSnapshot1.getCommonModel()
            //3 запрос
                REF_DATABASE_ROOT.child(NODE_GROUPS).child(model.id).child(NODE_MESSAGES)
                    .limitToLast(1)
                .addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot2 ->
                    val tempList = dataSnapshot2.children.map {it.getCommonModel()}

                    if(tempList.isEmpty()){
                        newModel.lastMessage = "Чат пустой"
                    }else{
                        newModel.lastMessage = tempList[0].text
                    }
                    newModel.type = TYPE_GROUP
                    mAdapter.updateListItems(newModel)

                })
        })
    }

    private fun showChat(model: CommonModel) {
        //2 запрос
        mRefUsers.child(model.id).addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot1 ->
            val newModel = dataSnapshot1.getCommonModel()
            //3 запрос
            mRefMessages.child(model.id).limitToLast(1)
                .addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot2 ->
                    val tempList = dataSnapshot2.children.map {it.getCommonModel()}

                    if(tempList.isEmpty()){
                        newModel.lastMessage = "Чат пустой"
                    }else{
                        newModel.lastMessage = tempList[0].text
                    }

                    if(newModel.fullname.isEmpty()){
                        newModel.fullname = newModel.phone
                    }

                    newModel.type = TYPE_CHAT
                    mAdapter.updateListItems(newModel)

                })
        })
    }


    override fun onResume(){
        super.onResume()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
