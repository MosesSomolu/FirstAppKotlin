package com.example.firstappkotlin

import androidx.lifecycle.ViewModel

// This is a ViewModel class. This class is used to manage the instance state of an app separately from it's lifecycle.
// In this case for the navigation drawer selection. So that whatever option/screen the user selected/was on
// before leaving the activity or invoking a configuration change will still be maintained when they return.

class itemsActivityViewModelClass : ViewModel() {
    var navDrawerDisplaySelectionName = "com.example.firstappkotlin.itemsActivityViewModelClass.navDrawerDisplaySelection"
    // This variable is to set the default state of a selection to the list of notes.
    var navDrawerDisplaySelection = R.id.nav_notes
}