package com.example.redsea.network.Response.UserWells

data class UserWellsItem(
    val created_at: String,
    val from: String,
    val id: Int,
    val name: String,
    val published: String,
    val rig: Any,
    val to: String,
    val updated_at: String,
    val user_id: String,
    val well: Any
)