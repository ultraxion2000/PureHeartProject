package com.example.pureheart.ui.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.databinding.FragmentCreateGroupBinding
import com.example.pureheart.databinding.FragmentHelpBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.utilits.showToast
import kotlinx.android.synthetic.main.fragment_create_group.*

class CreateGroupFragment(private var listContacts:List<CommonModel>):Fragment() {

    private var _binding: FragmentCreateGroupBinding? = null

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()
        binding.createGroupBtn.setOnClickListener {
            showToast("Добавлен")
        }
        binding.createGroupInputName.requestFocus()
        return root
    }

    private fun initRecyclerView() {
        mRecyclerView = binding.createGroupRecycleView
        mAdapter = AddContactsAdapter()
        mRecyclerView.adapter = mAdapter
        listContacts.forEach { mAdapter.updateListItems(it) }

    }



    override fun onResume() {
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}