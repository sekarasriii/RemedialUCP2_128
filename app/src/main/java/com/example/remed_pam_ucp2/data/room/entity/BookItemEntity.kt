package com.example.remed_pam_ucp2.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "book_items",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("bookId")]
)
data class BookItemEntity(
    @PrimaryKey val uniqueId: String,
    val bookId: Int,
    val status: String = "Available",
    val location: String? = null,
    val condition: String? = null,
    val deletedAt: Long? = null
)
