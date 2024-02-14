package com.dicoding.usersearch.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.usersearch.data.response.UserItem
import com.dicoding.usersearch.databinding.CardItemBinding
import com.dicoding.usersearch.ui.detail.DetailUserActivity

class UserAdapter : ListAdapter<UserItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItem) {
            binding.tvUsername.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.ivProfileCard)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(DetailUserActivity.USERNAME, user.login)
            val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)
        }
    }
}