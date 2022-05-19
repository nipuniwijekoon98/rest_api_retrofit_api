package com.thanushaw.restapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thanushaw.restapi.R
import com.thanushaw.restapi.api.Comment
import com.thanushaw.restapi.api.Post
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RecyclerViewAdapter2(private val mList: List<Comment>) : RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>() {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewCommentId: TextView = itemView.findViewById(R.id.comment_id)
        val textViewPostId: TextView = itemView.findViewById(R.id.post_id)
        val textViewName: TextView = itemView.findViewById(R.id.name)
        val textViewEmail: TextView = itemView.findViewById(R.id.email)
        val textViewBody: TextView = itemView.findViewById(R.id.body)

    }


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design_comment, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.textViewCommentId.text = item.id.toString()
        holder.textViewPostId.text = item.postId.toString()
        holder.textViewName.text = item.name.toString()
        holder.textViewEmail.text = item.email.toString()
        holder.textViewBody.text = item.body.toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
}