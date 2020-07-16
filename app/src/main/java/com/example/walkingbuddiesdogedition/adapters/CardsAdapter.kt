package com.example.walkingbuddiesdogedition.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.util.User

class CardsAdapter(context: Context, resourcesId: Int, users: List<User>):ArrayAdapter<User>(context, resourcesId, users) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var user = getItem(position)
        var finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        var name = finalView.findViewById<TextView>(R.id.nameTV)
        var image = finalView.findViewById<ImageView>(R.id.photoIV)

        name.text = "${user?.name}"
        Glide.with(context)
            .load(user?.imageUrl)
            .into(image)
        return finalView
    }
}