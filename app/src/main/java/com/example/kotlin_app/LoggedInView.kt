package com.example.kotlin_app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoggedInView: ViewModel() {
    var username = mutableStateOf("")



    fun login( email: String, password: String ){
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { username.value = email }
    }

    fun logout() {
        Firebase.auth.signOut()
        username.value = ""
    }

}