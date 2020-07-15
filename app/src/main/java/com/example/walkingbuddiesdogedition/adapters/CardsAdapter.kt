package com.example.walkingbuddiesdogedition.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.util.User

class CardsAdapter(context: Context, resourcesId: Int, users: List<User>):ArrayAdapter<User>(context, resourcesId, users) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var user = getItem(position)
        var finalView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)
    }
}