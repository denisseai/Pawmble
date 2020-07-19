package com.example.walkingbuddiesdogedition.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.util.DATA_USERS
import com.example.walkingbuddiesdogedition.util.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val userId = intent.extras?.getString(PARAM_USER_ID, "")
        if (userId.isNullOrEmpty()) {
            finish()
        }

        val userDatabase = FirebaseDatabase.getInstance().reference.child(DATA_USERS)
        if (userId != null) {
            userDatabase.child(userId).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    userInfoName.text = user?.name
                    userInfoAge.text = user?.age
                    //userInfoSize.text = user?.size
                    if(user?.imageUrl != null) {
                        Glide.with(this@UserInfoActivity)
                            .load(user.imageUrl)
                            .into(userInfoIV)
                    }
                }

            })
        }
    }

    companion object {
        val PARAM_USER_ID = "User id"
        fun newIntent(context: Context, userId: String?): Intent {
            val intent = Intent(context, UserInfoActivity::class.java)
            intent.putExtra(PARAM_USER_ID, userId)
            return intent
        }
    }
}