package com.example.remed_pam_ucp2.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
