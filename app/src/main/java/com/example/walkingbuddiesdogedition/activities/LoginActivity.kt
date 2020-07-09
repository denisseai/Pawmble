package com.example.walkingbuddiesdogedition.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.walkingbuddiesdogedition.MainActivity
import com.example.walkingbuddiesdogedition.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun onLogin(v:View){
        startActivity(MainActivity.newIntent(this))
    }
    companion object {
        fun newIntent(context: Context?) = Intent(context, LoginActivity::class.java)
    }
}