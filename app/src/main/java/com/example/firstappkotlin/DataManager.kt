package com.example.firstappkotlin

//The object class that is used to populate the Data models.

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

//    fun addNote(course: CourseInfo, noteTitle: String, noteText: String): Int{
//        val note = NoteInfo(course, noteTitle, noteText)
//        notes.add(note)
//            return notes.lastIndex
//    }         Created this function for testing

//    fun findNote(course: CourseInfo, noteTitle: String, noteText: String) : NoteInfo?{
//        for (note in notes)
//            if (course == note.course && noteTitle == note.title && noteText == note.text)
//                return note
//        return null
//    }

    private fun initializeCourses(){
        var course = CourseInfo("android_intents","Android Programming with Intents")
        courses.set(course.courseId, course)
        //I am creating a function to initialize the HashMap by setting the parameters into it.
        //The var course is set to create an instance of the CourseInfo class using it's parameters.
        //Creating an instance of this class enables me to populate it with courseId and title data.
        //I can now use this data to populate the HashMap by setting the courseId(String) and course(CourseInfo)
        // as parameters for the HashMap.

        course = CourseInfo("sample_course", "Sample Course")
        courses.set(course.courseId, course)
        //HashMap   //<String     //CourseInfo>

        course = CourseInfo("android_async", "Android Async Programming and Services")
        courses.set(course.courseId, course)

        course = CourseInfo("java_lang", "Java Fundamentals: The Java Language")
        courses.set(course.courseId, course)

        course = CourseInfo("java_core", "Java Fundamentals: The core platform")
        courses.set(course.courseId, course)
    }
    //This function was simply created to automatically populate the collection of courses using the Hashmap

    private fun initializeNotes(){

        var  note = NoteInfo(CourseInfo("android_intents","Android Programming with Intents"), "Android Programming with Intents","Text on Intents")
        notes.add(NoteInfo(CourseInfo("android_intents","Android Programming with Intents"), "Android Programming with Intents","Text on Intents"))

        note = NoteInfo(CourseInfo("android_async","Android Programming with Intents"), "Android Async Programming and Services","Text on Intents")
        notes.add(NoteInfo(CourseInfo("android_async","Android Programming with Intents"), "Android Async Programming and Services","Text on Intents"))

        note = NoteInfo(CourseInfo("java_lang","Java Fundamentals: The core Language"), "Java Fundamentals: The core Language","Text on Java")
        notes.add(NoteInfo(CourseInfo("java_lang","Java Fundamentals: The core Language"), "Java Fundamentals: The core Language","Text on Java"))

        note = NoteInfo(CourseInfo("java_core","Java Fundamentals: The core platform"), "Java Fundamentals: The core platform","Text on Java platform")
        notes.add(NoteInfo(CourseInfo("java_core","Java Fundamentals: The core platform"), "Java Fundamentals: The core platform","Text on Java platform"))

    }
}