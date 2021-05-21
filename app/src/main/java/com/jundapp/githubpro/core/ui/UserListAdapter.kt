package com.jundapp.githubpro.core.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jundapp.githubpro.R
import com.jundapp.githubpro.core.domain.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(private val context: Activity) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    var data: List<User> = arrayListOf()

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
    // TODO : change to view binding
    class ListViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView) {

        val ivAvatar: CircleImageView = rowView.findViewById(R.id.ivAvatar)

        val tvName: TextView = rowView.findViewById(R.id.tvName)
        val tvUName: TextView = rowView.findViewById(R.id.tvUName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.adapter_user_list, parent, false)
        return ListViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val thisData = data[position]

        holder.tvName.text = thisData.username
        holder.tvUName.text = thisData.username

        Glide.with(context)
            .load(thisData.avatar_url)
            .placeholder(R.drawable.ic_avatar)
            .error(R.drawable.ic_avatar)
            .into(holder.ivAvatar)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data[holder.absoluteAdapterPosition]) }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}