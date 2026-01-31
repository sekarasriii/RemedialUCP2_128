package com.example.remed_pam_ucp2.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remed_pam_ucp2.data.room.dao.*
import com.example.remed_pam_ucp2.data.room.entity.*

@Database(
    entities = [
        CategoryEntity::class,
        AuthorEntity::class,
        BookEntity::class,
        BookAuthorCrossRef::class,
        BookItemEntity::class,
        AuditLogEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun bookDao(): BookDao
    abstract fun authorDao(): AuthorDao
    abstract fun auditDao(): AuditDao

    companion object {
        @Volatile
        private var INSTANCE: LibraryDatabase? = null

        fun getDatabase(context: Context): LibraryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryDatabase::class.java,
                    "library_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
