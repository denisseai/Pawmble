package com.example.walkingbuddiesdogedition.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.activities.UserInfoActivity
import com.example.walkingbuddiesdogedition.util.User

class CardsAdapter(context: Context, resourcesId: Int, users: List<User>):ArrayAdapter<User>(context, resourcesId, users) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val user = getItem(position)
        val finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        val name = finalView.findViewById<TextView>(R.id.nameTV)
        val image = finalView.findViewById<ImageView>(R.id.photoIV)
        val userInfo = finalView.findViewById<LinearLayout>(R.id.userInfoLayout)

        name.text = "${user?.name}"
        Glide.with(context)
            .load(user?.imageUrl)
            .into(image)
        userInfo.setOnClickListener {
            if (user != null) {
                finalView.context.startActivity(UserInfoActivity.newIntent(finalView.context, user.uid))
            }
        }
        return finalView
    }
}