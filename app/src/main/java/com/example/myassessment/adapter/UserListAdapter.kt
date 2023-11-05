package com.example.myassessment.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.myassessment.databinding.TaskLayoutBinding
import com.example.myassessment.model.userlist.UserResponseItem


class UserListAdapter (private val context: Context,
                       private var userList: ArrayList<UserResponseItem>): RecyclerView.Adapter<UserListAdapter.MyviewHolder>() {


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

    fun setUserList(list: ArrayList<UserResponseItem>) {

        this.userList =list
        notifyDataSetChanged()
    }

    fun filterdList(filterList: ArrayList<UserResponseItem>) {

        userList = filterList

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {

        val itemPosition =userList.get(position)

        holder.binding.textTaskName.text ="currentPrice:"+itemPosition.currentPrice
        holder.binding.textTaskgender.text ="digits:"+itemPosition.digits
        holder.binding.textTaskhouse.text ="login:"+itemPosition.login
        holder.binding.textTaskspecies.text ="openTime:"+itemPosition.openTime

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