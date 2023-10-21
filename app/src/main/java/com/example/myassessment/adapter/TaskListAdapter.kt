package com.example.myassessment.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.myassessment.R
import com.example.myassessment.databinding.TaskLayoutBinding
import com.example.myassessment.model.TaskResponseItem


class TaskListAdapter (private val context: Context,
 private var taskList: ArrayList<TaskResponseItem>): RecyclerView.Adapter<TaskListAdapter.MyviewHolder>() {


    private var listposition = -1

    private var clickLisiner: onItemClickLisiner? = null


    inner class MyviewHolder(val binding: TaskLayoutBinding) :View.OnClickListener, RecyclerView.ViewHolder(binding.root) {


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            val position: Int = adapterPosition
            clickLisiner?.OnClickLisiner(position)
        }
    }

    interface onItemClickLisiner {
        fun OnClickLisiner(position: Int)
    }

    fun setOnItemClick(clickLisiner: onItemClickLisiner?) {
        this.clickLisiner = clickLisiner!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val binding = TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyviewHolder(binding)
    }

    fun setTaskList(list: ArrayList<TaskResponseItem>) {

        this.taskList =list
        notifyDataSetChanged()
    }

    fun filterdList(filterList: ArrayList<TaskResponseItem>) {

        taskList = filterList

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = taskList.size


    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {

        val itemPosition =taskList.get(position)

        holder.binding.textTaskName.text ="Name:"+itemPosition.name
        holder.binding.textTaskgender.text ="Gender:"+itemPosition.gender
        holder.binding.textTaskhouse.text ="House:"+itemPosition.house
        holder.binding.textTaskspecies.text ="Species:"+itemPosition.species

        setAnimiton(holder.itemView, position)
    }


    fun setAnimiton(viewAnimition: View, position: Int) {
        if (position > listposition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            animation.duration = 1000
            viewAnimition.startAnimation(animation)
            listposition = position
        }
    }
}