package com.example.remed_pam_ucp2.data.migration

import com.example.remed_pam_ucp2.data.repository.LibraryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DataMigrationManager(private val repository: LibraryRepository) {
    
    suspend fun migrateFromOldSystem() = withContext(Dispatchers.IO) {
        delay(1000)
    }
}
