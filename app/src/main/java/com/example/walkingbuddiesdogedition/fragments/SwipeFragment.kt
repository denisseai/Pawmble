package com.example.walkingbuddiesdogedition.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.activities.Callback
import com.example.walkingbuddiesdogedition.adapters.CardsAdapter
import com.example.walkingbuddiesdogedition.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import kotlinx.android.synthetic.main.fragment_swipe.*

class SwipeFragment : Fragment() {

    private lateinit var userId: String
    private lateinit var userDatabase: DatabaseReference
    private lateinit var chatDatabase: DatabaseReference

    private var cardsAdapter: ArrayAdapter<User>? = null
    private var rowItems = ArrayList<User>()
    private var preferredSize: String? = null
    private var userName: String? = null
    private var imageUrl: String? = null
    private var callback: Callback? = null

    fun setCallback(callback: Callback) {
        this.callback = callback
        userId = callback.onGetUserId()
        userDatabase = callback.getUserDatabase()
        chatDatabase = callback.getChatDatabase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDatabase.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                preferredSize = user?.preferredSize
                userName = user?.name
                imageUrl = user?.imageUrl
                populateItems()
            }
        })

        cardsAdapter = context?.let { CardsAdapter(it, R.layout.item, rowItems) }
        //cardsAdapter = CardsAdapter(context, R.layout.item, rowItems)

        frame.adapter = cardsAdapter
        frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                rowItems.removeAt(0)
                cardsAdapter?.notifyDataSetChanged()
            }

            override fun onLeftCardExit(p0: Any?) {
                val user = p0 as User
                userDatabase.child(user.uid.toString()).child(DATA_SWIPE_LEFT).child(userId).setValue(true)
            }

            override fun onRightCardExit(p0: Any?) {
                val selectedUser = p0 as User
                val selectedUserId = selectedUser.uid

                if (!selectedUserId.isNullOrEmpty()) {
                    userDatabase.child(userId).child(DATA_SWIPE_RIGHT)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                            }
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.hasChild(selectedUserId)) {
                                    Toast.makeText(context, "BARK! we have a match!", Toast.LENGTH_SHORT).show()

                                    val chatKey = chatDatabase.push().key

                                    if (chatKey != null) {
                                        userDatabase.child(userId).child(DATA_SWIPE_RIGHT).child(selectedUserId).removeValue()
                                        userDatabase.child(userId).child(DATA_MATCHES).child(selectedUserId).setValue(chatKey)
                                        userDatabase.child(selectedUserId).child(DATA_MATCHES).child(userId).setValue(chatKey)

                                        chatDatabase.child(chatKey).child(userId).child(DATA_NAME)
                                            .setValue(userName)
                                        chatDatabase.child(chatKey).child(userId)
                                            .child(DATA_IMAGE_URL).setValue(imageUrl)

                                        chatDatabase.child(chatKey).child(selectedUserId)
                                            .child(DATA_NAME).setValue(selectedUser.name)
                                        chatDatabase.child(chatKey).child(selectedUserId)
                                            .child(DATA_IMAGE_URL).setValue(selectedUser.imageUrl)
                                    }

                                } else {
                                    userDatabase.child(selectedUserId).child(DATA_SWIPE_RIGHT)
                                        .child(userId).setValue(true)
                                }
                            }
                        })
                }
            }

            override fun onAdapterAboutToEmpty(p0: Int) {
            }

            override fun onScroll(p0: Float) {
            }
        })

        likeButton.setOnClickListener {
            if (rowItems.isNotEmpty()) {
                frame.topCardListener.selectRight()
            }
        }
        dislikeButton.setOnClickListener {
            if (rowItems.isNotEmpty()) {
                frame.topCardListener.selectRight()
            }
        }
    }

    fun populateItems() {
        noUsersLayout.visibility = View.GONE
        progressLayout.visibility = View.VISIBLE
        val cardsQuery = userDatabase.orderByChild(DATA_SIZE).equalTo(preferredSize)
        cardsQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { child ->
                    val user = child.getValue(User::class.java)
                    if (user != null) {
                        var showUser = true
                        if (child.child(DATA_SWIPE_LEFT).hasChild(userId) ||
                            child.child(DATA_SWIPE_RIGHT).hasChild(userId) ||
                            child.child(DATA_MATCHES).hasChild(userId)
                        ) {
                            showUser = false
                        }
                        if (showUser) {
                            rowItems.add(user)
                            cardsAdapter?.notifyDataSetChanged()
                        }
                    }
                }
                progressLayout.visibility = View.GONE
                if (rowItems.isEmpty()) {
                    noUsersLayout.visibility = View.VISIBLE
                }
            }
        })
    }
}