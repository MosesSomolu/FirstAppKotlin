package com.example.firstappkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

// This is the Recycler adapter designed for populating the recycler view. The Layout of the Recycler view was designed using a
// single Layout file. That layout file is what this adapter is going to use to populate each of the views created with that
// layout depending on the size of the data.
// This Adapter extends the base class RecyclerView.Adapter and overrides 3 methods.
// onCreateViewHolder, getItemCount and onBindViewHolder

class NoteRecyclerAdapter( private val context: Context, private val notes: List<NoteInfo>) : RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

// Creates the view holder using the layout file and gets it ready to be populated with data.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_note_list, parent, false)
        return ViewHolder(itemView)
    }
// Identifies the size of the data withtin the data source.
    override fun getItemCount()  = notes.size

// Binds the data taken from the data source into the created layout by identifying
// each view within the layout and populating it with the appropriate information.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.textCourse.text = note.course?.title
        holder.noteText.text = note.text
        holder.notePosition = position
    }

// This is an inner class created after the RecyclerViewAdapter is created. This ViewHolder
// as the name implies is what is used to identify the views in the layout file, and launch an
// activity.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textCourse: TextView = itemView.findViewById(R.id.coursesTitle)
        val noteText: TextView = itemView.findViewById(R.id.noteText)
        var notePosition = 0
        var bundle = Bundle()

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(NOTE_POSITION, notePosition)
                startActivity(context, intent, bundle)
            }
        }
    }
}