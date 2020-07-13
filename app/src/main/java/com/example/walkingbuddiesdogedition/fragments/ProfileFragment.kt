package com.example.walkingbuddiesdogedition.fragments

import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.activities.Callback
import com.google.firebase.database.DatabaseReference

class ProfileFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var userDataBase: DatabaseReference
    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDataBase = callback.getUserDatabase().child(userId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}