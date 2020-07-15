package com.example.walkingbuddiesdogedition.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.activities.Callback
import com.example.walkingbuddiesdogedition.util.User
import com.google.firebase.database.DatabaseReference

class SwipeFragment : Fragment() {

    private var callback: Callback? = null
    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference
    private var cardsAdapter: ArrayAdapter<User>? = null
    private var rowItems = ArrayList<User>()

    fun setCallback(callback: Callback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardsAdapter =
    }
}