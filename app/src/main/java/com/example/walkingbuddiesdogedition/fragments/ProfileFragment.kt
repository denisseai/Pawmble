package com.example.walkingbuddiesdogedition.fragments

import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.activities.Callback
import com.example.walkingbuddiesdogedition.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressLayout.setOnTouchListener {view, event -> true}

        populateInfo()

        photoIV.setOnClickListener {callback?.startActivityForPhoto()}

        applyButton.setOnClickListener {onApply() }
        signoutButton.setOnClickListener {callback?.onSignout()}

    }
    fun populateInfo() {
        progressLayout.visibility = View.VISIBLE
        userDataBase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                progressLayout.visibility = View.GONE
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(isAdded) {
                    val user = snapshot.getValue(User::class.java)
                    nameET.setText(user?.name, TextView.BufferType.EDITABLE)
                    emailET.setText(user?.email, TextView.BufferType.EDITABLE)
                    ageET.setText(user?.age, TextView.BufferType.EDITABLE)

                    if (user?.gender == GENDER_MALE) {
                        radioMale1.isChecked = true
                    } else if (user?.gender == GENDER_FEMALE) {
                        radioFemale1.isChecked = true
                    } else {

                    }

                    if (user?.size == SIZE_SMALL) {
                        radioSize1.isChecked = true
                    } else if (user?.size == SIZE_MEDIUM) {
                        radioSize2.isChecked = true
                    } else if (user?.size == SIZE_LARGE) {
                        radioSize3.isChecked = true
                    } else {

                    }

                    if (user?.preferredSize == SIZE_SMALL) {
                        radioPreferredSize1.isChecked = true
                    } else if (user?.preferredSize == SIZE_MEDIUM) {
                        radioPreferredSize2.isChecked = true
                    } else if (user?.preferredSize == SIZE_LARGE) {
                        radioPreferredSize3.isChecked = true
                    } else {

                    }
                    if(!user?.imageUrl.isNullOrEmpty()) {
                        populateImage(user?.imageUrl!!)
                    }
                    progressLayout.visibility = View.GONE
                }
            }
        })
    }

    fun onApply() {
        if(nameET.text.toString().isNullOrEmpty() ||
                emailET.text.toString().isNullOrEmpty() ||
                radioGrp1.checkedRadioButtonId == -1 ||
                radioGrp2.checkedRadioButtonId == -1 ||
                radioGrp3.checkedRadioButtonId == -1) {
            Toast.makeText(context, getString(R.string.error_profile_incomplete), Toast.LENGTH_SHORT).show()
        } else {
            val name = nameET.text.toString()
            val email = emailET.text.toString()
            val age = ageET.text.toString()
            val gender =
                if (radioMale1.isChecked) GENDER_MALE
                else GENDER_FEMALE
            val size =
                if (radioSize1.isChecked) SIZE_SMALL
                else if (radioSize2.isChecked) SIZE_MEDIUM
                else SIZE_LARGE
            val preferredSize =
                if (radioPreferredSize1.isChecked) SIZE_SMALL
                else if (radioPreferredSize2.isChecked) SIZE_MEDIUM
                else SIZE_LARGE
            userDataBase.child(DATA_NAME).setValue(name)
            userDataBase.child(DATA_EMAIL).setValue(email)
            userDataBase.child(DATA_AGE).setValue(age)
            userDataBase.child(DATA_GENDER).setValue(gender)
            userDataBase.child(DATA_SIZE).setValue(size)
            userDataBase.child(DATA_SIZE_PREFERENCE).setValue(preferredSize)

            callback?.profileComplete()
        }
    }

    fun updateImageUri(uri: String) {
        userDataBase.child(DATA_IMAGE_URL).setValue(uri)
        populateImage(uri)
    }
    fun populateImage(uri: String) {
        Glide.with(this)
            .load(uri)
            .into(photoIV)
    }
}