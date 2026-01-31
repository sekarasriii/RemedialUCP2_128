package com.example.remed_pam_ucp2.data.room.dao

import androidx.room.*
import com.example.remed_pam_ucp2.data.room.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories WHERE deletedAt IS NULL")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity?

    @Query("SELECT * FROM categories WHERE parentId = :parentId AND deletedAt IS NULL")
    suspend fun getSubCategories(parentId: Int): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE name = :name AND parentId IS :parentId AND deletedAt IS NULL")
    suspend fun findCategoryByNameAndParent(name: String, parentId: Int?): CategoryEntity?

    @Query("UPDATE categories SET deletedAt = :timestamp WHERE id = :id")
    suspend fun softDelete(id: Int, timestamp: Long = System.currentTimeMillis())
}
