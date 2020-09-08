package com.example.firstappkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.content_items.*


//This class is created when a Navigation view activity is added to the app features.
//This is where the work of ensuring the NavBar options and features are working and
// are given specific actions.
class ItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val noteLayoutManager by lazy { LinearLayoutManager(this)  }
    private val noteRecyclerAdapter by lazy { NoteRecyclerAdapter(this, DataManager.notes) }

    private val courseLayoutManager by lazy { GridLayoutManager(this, 2)}
    private val courseRecyclerAdapter by lazy { CourseRecyclerAdapter(this, DataManager.courses.values.toList())}

    //code to access ViewModel class using the ViewModelProvider
    private val viewModel by lazy { ViewModelProvider(this).get(itemsActivityViewModelClass::class.java) }


    //OnCreate Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        uselessFunction()

        handleDisplaySelection(viewModel.navDrawerDisplaySelection)

        //so if the savedInstance state bundle is null, then the default activity is displayed.
        //However, if it is not null then whatever value is saved in the bundle, which is in viewModel.navDrawerDisplaySelectionName,
        // should be displayed when the activity is created.
        if (savedInstanceState != null)
            viewModel.navDrawerDisplaySelection = savedInstanceState.getInt(viewModel.navDrawerDisplaySelectionName)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            startActivity(Intent(this, NoteActivity::class.java))
        }



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

    }

    //This func has been overridden to create a more Durable state save so that when the user leaves the app and returns
    //they will come back to the activity they left. This is invoked in the onCreate method.

    fun uselessFunction(){
        //Just created this to check if Git will recognize I made changes to the file
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
            outState.putInt(viewModel.navDrawerDisplaySelectionName, viewModel.navDrawerDisplaySelection)
    }

    private fun displayNotes() {
        listItems.layoutManager = LinearLayoutManager(this)
        listItems.adapter = NoteRecyclerAdapter(this, DataManager.notes)

        nav_view.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        listItems.layoutManager = GridLayoutManager(this,2)
        listItems.adapter = CourseRecyclerAdapter(this, DataManager.courses.values.toList())

        nav_view.menu.findItem(R.id.nav_courses).isChecked = true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                //Using ViewModel. When the user makes a selection, this code is run to check  if
                //the selection made is nav notes or nav courses. If it is either one of them
                //then the handleDisplaySelection func is called to launch the corresponding activity.
                //The menu ID of the corresponding activity selected is then set as the value of navDrawerDisplaySelection in ViewModel
                // So that when the user leaves the activity or invokes a configuration change, they return to the activity they left.
                // So in onCreate Activity, the value of navDrawerDisplaySelection is what will be used when the activity is recreated.
                R.id.nav_notes,
                R.id.nav_courses -> {
                    handleDisplaySelection(item.itemId)
                    viewModel.navDrawerDisplaySelection = item.itemId
                }
                R.id.nav_share -> {

                }
                R.id.nav_send -> {

                }
            }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    // Func to handle note selection to launch the activity corresponding to the users selection.
    fun handleDisplaySelection( ItemId: Int){
        when (ItemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
        }
    }
}
