package com.example.remed_pam_ucp2.data.room.entity

import androidx.room.*

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("categoryId")]
)
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val categoryId: Int? = null,
    val isbn: String? = null,
    val publishYear: Int,
    val publisher: String? = null,
    val language: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)
