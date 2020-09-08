package com.example.firstappkotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_items.*


// The Activity that displays the notes a user selects.
// Data in the spinner is derived from the CourseInfo data model
// and populated from the DataManager object class using the initializeCourses() function.
// Based on the selected note in the spinner, the TextViews displays the Notes Title
// and Notes Text related to the course selected from the spinner.

class NoteActivity : AppCompatActivity() {
    private val tag = this::class.simpleName
    private var notePosition = Position_Not_Set



    //This variable was created so that in case the notePosition that we want to receive from the
    // NoteListActivity does not have any position assigned to it, the EditNote spinner and textViews
    // will not have any information displayed in them.

// This notePosition is what will receive the intent extra in the onCreate method.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar2)
        //show Back Button
        //assert(supportActionBar != null)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        populateSpinner()
 
        notePosition = intent.getIntExtra(NOTE_POSITION, Position_Not_Set)
        //Here notePosition is set to receive the intent extra of NOTE_POSITION which is the String assigned to
        // the position of the note put in the intent extra when the user selects a note.
        if (notePosition != Position_Not_Set)
        // This statement defines what happens when there is a position value.
        // If notePosition is not equal to -1(Position_Not_Set) then displayNote in the EditNote Activity
            displayNote()
        // This method was created to display the notes in the EditNote Activity if there is a position
        // value present in the intent extra sent.
        else {
            DataManager.notes.add(NoteInfo(CourseInfo("",""), "",""))
            notePosition = DataManager.notes.lastIndex}

        Log.d(tag,"onCreate")
    }


    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        //This code gets the note from the DataManger that corresponds to the position value in the
        // intent extra sent then displays it into the Spinner and EditText fields in the EditNote Activity using the code below
        myTextNoteTitle.setText(note.title)
        myTextNotes.setText(note.text)

        //This code is then used to ensure that the position of the course being displayed is
        // corresponding to the position of the course within the spinner
        val coursePosition = DataManager.courses.values.indexOf(note.course)
        mySpinnerCourses.setSelection(coursePosition)
    }


    fun populateSpinner() {

        val adapterCourses = ArrayAdapter<CourseInfo>(
            this, android.R.layout.simple_spinner_dropdown_item,
            DataManager.courses.values.toList()
        )

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinnerCourses.adapter = adapterCourses
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Inflates the menu. This adds items to the actions bar if it is present
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Handle action bar item clicks here.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            R.id.action_back -> {
                moveBack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    private fun moveBack(){
        --notePosition
        displayNote()
        invalidateOptionsMenu()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            if(menuItem != null){
                //menuItem.icon = getDrawable(R.drawable.ic_block_white)
                menuItem.isEnabled = false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }



    private fun saveNote(){
        val note = DataManager.notes[notePosition]
        note.title = myTextNoteTitle.text.toString()
        note.text = myTextNotes.text.toString()
        note.course = mySpinnerCourses.selectedItem as CourseInfo
    }

    fun saveNoteFunction(view : View){
        var actionButton = findViewById<Button>(R.id.mySaveButton)
        var intent = Intent(this, ItemsActivity::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
        finish()
    }

    //initiate BackButton
//    override fun onSupportNavigateUp(): Boolean {
//        disableBackButton()
//        Log.i("1BackButtonPressed","BackButtonPressed")
//        finish()
//        return true
//    }


//    private fun disableBackButton(){
//        val note = DataManager.notes[notePosition]
//        if(notePosition == null){
//            supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            Log.i("2BackButtonPressed","Back Button Disabled")
//        }
//    }

}

