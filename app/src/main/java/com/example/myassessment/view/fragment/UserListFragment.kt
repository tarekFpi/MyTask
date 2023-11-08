package com.example.myassessment.view.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.myassessment.R
import com.example.myassessment.adapter.UserListAdapter
import com.example.myassessment.model.userlist.UserResponseItem
import com.example.myassessment.utils.CheckInternetConnection
import com.example.myassessment.network.Resource
import com.example.myassessment.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var taskListAdapter : UserListAdapter

    private lateinit var recyclerView_task: RecyclerView

    private lateinit var  progressDialog: ProgressDialog

    private lateinit var userListViewModel: UserListViewModel

    private var userlist:  ArrayList<UserResponseItem> = ArrayList()

    private lateinit var  editText_TaskSearch: EditText

    private lateinit var  lottieAnimationView: LottieAnimationView

    @Inject
    lateinit var checkInternetConnection: CheckInternetConnection

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait...")
        progressDialog.show()

        recyclerView_task =view.findViewById<RecyclerView>(R.id.recyclerView_task)

         lottieAnimationView=view.findViewById(R.id.animation_view)

         userListViewModel= ViewModelProvider(this)[UserListViewModel::class.java]

         taskListAdapter= UserListAdapter(requireContext(),userlist)

         recyclerView_task.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(requireContext())
            adapter=taskListAdapter

         }

        userListViewModel.userlistLiveData.observe(requireActivity(), Observer { it->

            when(it){
                is Resource.Loading -> {
                    progressDialog.show()

                }

                is Resource.Error -> {

                    lottieAnimationView.visibility=View.VISIBLE
                    progressDialog.dismiss()

                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }

                is  Resource.Success -> {

                    lottieAnimationView.visibility=View.GONE
                    userlist.addAll(it.data!!)
                    taskListAdapter.setUserList(userlist)
                    progressDialog.dismiss()

                }
            }

        })


        editText_TaskSearch=view.findViewById(R.id.edit_taskSearch)

        editText_TaskSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {

               SearchTaskList(editable.toString())
            }
        })




        return view
    }




    private fun SearchTaskList(textData: String) {
        try {
            val list =ArrayList<UserResponseItem>()


            for (item:UserResponseItem in userlist) {

                if (item.login.toString().lowercase().contains(textData.toString().lowercase()))
                {
                    list.add(item)
                }
            }

            taskListAdapter.filterdList(list)

        } catch (exception: Exception) {

            Toast.makeText(requireContext(), "${exception.toString()}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()

        InternetConnection()
    }

    private  fun InternetConnection(){

        if (checkInternetConnection.isInternetAvailable(requireContext())) else
            Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()

    }

}