package com.example.remed_pam_ucp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.remed_pam_ucp2.data.room.database.LibraryDatabase
import com.example.remed_pam_ucp2.data.repository.LibraryRepository
import com.example.remed_pam_ucp2.ui.navigation.LibraryNavHost
import com.example.remed_pam_ucp2.ui.theme.LibraryTheme
import com.example.remed_pam_ucp2.ui.viewmodel.LibraryViewModel
import com.example.remed_pam_ucp2.ui.viewmodel.LibraryViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val db = LibraryDatabase.getDatabase(this)
        val repository = LibraryRepository(
            db.categoryDao(),
            db.bookDao(),
            db.authorDao(),
            db.auditDao()
        )
        val factory = LibraryViewModelFactory(repository)
        val viewModel: LibraryViewModel by viewModels { factory }

        setContent {
            LibraryTheme {
                val navController = rememberNavController()
                LibraryNavHost(navController = navController, viewModel = viewModel)
            }
        }
    }
}