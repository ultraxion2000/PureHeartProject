package com.example.pureheart.ui.groups

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pureheart.R
import com.example.pureheart.activities.MainActivity
import com.example.pureheart.database.createGroupToDatabase
import com.example.pureheart.databinding.FragmentCreateGroupBinding
import com.example.pureheart.databinding.FragmentHelpBinding
import com.example.pureheart.models.CommonModel
import com.example.pureheart.ui.HomeFragment
import com.example.pureheart.ui.main_list.MainListFragment

import com.example.pureheart.utilits.getPlurals
import com.example.pureheart.utilits.replaceFragment
import com.example.pureheart.utilits.showToast
import kotlinx.android.synthetic.main.fragment_create_group.*

class CreateGroupFragment(private var listContacts:List<CommonModel>):Fragment() {

    private var _binding: FragmentCreateGroupBinding? = null

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private var mUri = Uri.EMPTY


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)

        initRecyclerView()

        binding.createGroupBtn.setOnClickListener {
           val nameGroup = binding.createGroupInputName.text.toString()
            if(nameGroup.isEmpty()){
                showToast("Введите название")
            }else{
                createGroupToDatabase(nameGroup,mUri,listContacts){
                    fragmentManager?.popBackStack()
                }
            }
        }
        binding.createGroupInputName.requestFocus()
        binding.createGroupCounts.text = getPlurals(listContacts.size)
        return root
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_back, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.settings_confirm_back -> changeB()
        }
        return true
    }

    private fun changeB() {
        fragmentManager?.popBackStack()
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