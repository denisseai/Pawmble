package com.example.walkingbuddiesdogedition.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walkingbuddiesdogedition.R
import com.example.walkingbuddiesdogedition.util.Chat

class ChatAdapter(private var chat: ArrayList<Chat>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    fun addElement(message: Chat) {
        chat.add(message)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChatViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
    )

    override fun getItemCount() = chat.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chat[position])
    }

    class ChatViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var layout = view.findViewById<View>(R.id.chatLayout)
        private var image = view.findViewById<ImageView>(R.id.chatPictureIV)
        private var name = view.findViewById<TextView>(R.id.chatNameTV)

        fun bind(chat: Chat) {
            name.text = chat.name
            if (image != null) {
                Glide.with(view)
                    .load(chat.imageUrl)
                    .into(image)
            }
            layout.setOnClickListener {}
        }
    }
}