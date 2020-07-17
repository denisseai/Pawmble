package com.example.walkingbuddiesdogedition.util

data class User(
    val uid: String? = "",
    val name: String? = "",
    val email: String? = "",
    val age: String? = "",
    val size: String? = "",
    val gender: String? = "",
    val preferredSize: String? = "",
    val imageUrl: String? = ""

)

data class Chat(
    val userId: String? = "",
    val chatId: String? = "",
    val otherUserId: String? = "",
    val name: String? = "",
    val imageUrl: String? = ""
)