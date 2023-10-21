package com.example.myassessment.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.PermissionRequest
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.myassessment.R
import com.example.myassessment.adapter.TaskListAdapter
import com.example.myassessment.model.TaskResponseItem
import com.example.myassessment.utils.NetworkResult
import com.example.myassessment.viewmodel.TaskViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskListActivity : AppCompatActivity() {

    private lateinit var taskListAdapter : TaskListAdapter;

    private lateinit var recyclerView_task: RecyclerView

    private lateinit var  progressDialog: ProgressDialog

    private lateinit var taskViewModel: TaskViewModel

    private var taskList:  ArrayList<TaskResponseItem> = ArrayList()

    private lateinit var  editText_TaskSearch: EditText

    private lateinit var  lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("please wait...")

        recyclerView_task = findViewById<RecyclerView>(R.id.recyclerView_task)

        lottieAnimationView= findViewById(R.id.animation_view)

        taskViewModel= ViewModelProvider(this)[TaskViewModel::class.java]

        taskListAdapter= TaskListAdapter(this,taskList)

        recyclerView_task.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this@TaskListActivity)
            adapter=taskListAdapter

        }





        editText_TaskSearch= findViewById(R.id.edit_taskSearch)

        editText_TaskSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {

                SearchTaskList(editable.toString())
            }
        })


    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {

            taskViewModel.postResponse.collect{it->
                when(it){
                    is NetworkResult.Loading -> {
                        progressDialog.show()

                    }

                    is NetworkResult.Error -> {

                        lottieAnimationView.visibility=View.VISIBLE
                        progressDialog.dismiss()
                    }

                    is  NetworkResult.Success -> {

                        lottieAnimationView.visibility=View.GONE
                        taskList.addAll(it.data!!)
                        taskListAdapter.setTaskList(taskList)
                        progressDialog.dismiss()

                        taskListAdapter.setOnItemClick(object :TaskListAdapter.onItemClickLisiner {
                            @SuppressLint("SuspiciousIndentation")
                            override fun OnClickLisiner(position: Int) {

                                val intent = Intent(this@TaskListActivity,TaskInforActivity::class.java)
                                  intent.putExtra("name", it.data.get(position).name)
                                  intent.putExtra("species", it.data.get(position).species)
                                  intent.putExtra("gender", it.data.get(position).gender)
                                  intent.putExtra("house", it.data.get(position).house)
                                  startActivity(intent)

                            }

                        })
                    }
                }

            }
        }

    }

    private fun SearchTaskList(textData: String) {
        try {
            val list =ArrayList<TaskResponseItem>()


            for (item:TaskResponseItem in taskList) {

                if (item.name.toString().lowercase().contains(textData.toString().lowercase()))
                {
                    list.add(item)
                }
            }

            taskListAdapter.filterdList(list)

        } catch (exception: Exception) {

            Toast.makeText(this, "${exception.toString()}", Toast.LENGTH_SHORT).show()
        }

    }

    fun DowanlodTaskBtn(view: View) {

        val intent = Intent(this, TaskDowanlodActivity::class.java)
        startActivity(intent)
    }

}