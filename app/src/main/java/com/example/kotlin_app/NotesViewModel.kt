package com.example.kotlin_app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotesViewModel: ViewModel() {
    var notes = mutableStateOf( listOf<String>())
    var titles = mutableStateOf( listOf<String>())
    var dates = mutableStateOf( listOf<String>())

    init {
        Firebase.firestore
            .collection("notes")
            .addSnapshotListener { value, error ->
                if(error != null) {

                } else if(value != null && !value.isEmpty) {
                    val fetchNotes = mutableListOf<String>()
                    val fetchTitles = mutableListOf<String>()
                    val fetchDates = mutableListOf<String>()
                    for(n in value.documents){
                        fetchNotes.add(n.get("note").toString())
                        fetchTitles.add(n.get("title").toString())
                        fetchDates.add(n.get("date").toString())
                    }
                    notes.value = fetchNotes
                    titles.value = fetchTitles
                    dates.value = fetchDates
                    }
            }
    }
}