package com.thanushaw.restapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thanushaw.restapi.R
import com.thanushaw.restapi.api.Post
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RecyclerViewAdapter(private val mList: List<Post>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // change to different subjects from rx.subjects to get different behavior
    // BehaviorSubject for example allows to receive last event on subscribe
    // PublishSubject sends events only after subscribing on the other hand which is desirable for clicks
    private val clickSubject = PublishSubject.create<String>()
    val clickEvent : Observable<String> = clickSubject
    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textViewPostId: TextView = itemView.findViewById(R.id.post_id)
        val textViewPostTitle: TextView = itemView.findViewById(R.id.post_title)
        init {
            itemView.setOnClickListener {
                clickSubject.onNext(textViewPostId.text as String)
            }
        }
    }


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.textViewPostId.text = item.id.toString()+"."

        // sets the text to the textview from our itemHolder class
        holder.textViewPostTitle.text = item.title

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
}