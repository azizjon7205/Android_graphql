package com.example.android_graphql.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_graphql.UsersListQuery
import com.example.android_graphql.databinding.ItemViewBinding

class UsersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var onClick:((UsersListQuery.User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount() = dif.currentList.size

    inner class ViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind() {
            val user = dif.currentList[adapterPosition]
            with(binding) {
                tvName.text = user.name ?: "null"
                tvRocket.text = user.rocket ?: "null"
                tvTime.text = user.rocket ?: "null"
                tvTwitter.text = user.twitter ?: "null"

                root.setOnClickListener {
                    onClick!!.invoke(user)
                }
            }
        }
    }


    fun submitList(list: List<UsersListQuery.User>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<UsersListQuery.User>() {
            override fun areItemsTheSame(oldItem: UsersListQuery.User, newItem: UsersListQuery.User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UsersListQuery.User, newItem: UsersListQuery.User): Boolean =
                oldItem == newItem
        }
    }


}