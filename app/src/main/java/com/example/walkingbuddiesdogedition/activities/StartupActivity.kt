package com.example.walkingbuddiesdogedition.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.R.layout.activity_startup

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_startup)
    }

    fun onLogin(v: View){
        startActivity(LoginActivity.newIntent(this))
    }

    fun onSignup(v: View){
        startActivity(SignupActivity.newIntent(this))
    }
    companion object {
        fun newIntent(context: Context?) = Intent(context, StartupActivity::class.java)
    }
}