package com.example.walkingbuddiesdogedition.activities

import com.google.firebase.database.DatabaseReference

interface Callback {
    fun onSignout()
    fun onGetUserId(): String
    fun getUserDatabase(): DatabaseReference
}