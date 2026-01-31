package com.example.remed_pam_ucp2.data.room.entity

import androidx.room.*

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val parentId: Int? = null,
    val description: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val deletedAt: Long? = null
)
