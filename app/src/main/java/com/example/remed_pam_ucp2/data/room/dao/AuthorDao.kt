package com.example.remed_pam_ucp2.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.remed_pam_ucp2.data.room.entity.AuthorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: AuthorEntity)

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): Flow<List<AuthorEntity>>
}
