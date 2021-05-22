package com.jundapp.githubpro.core.ui

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundapp.githubpro.R
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.databinding.AdapterUserListBinding

class UserListAdapter(private val context: Application) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    var data: List<User> = arrayListOf()

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    class ListViewHolder(val binding: AdapterUserListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterUserListBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val thisData = data[position]

        holder.binding.tvName.text = thisData.username
        holder.binding.tvUName.text = thisData.username

        Glide.with(context)
            .load(thisData.avatarUrl)
            .placeholder(R.drawable.ic_avatar)
            .error(R.drawable.ic_avatar)
            .into(holder.binding.ivAvatar)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data[holder.absoluteAdapterPosition]) }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}