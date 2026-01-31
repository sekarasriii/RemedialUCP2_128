package com.example.remed_pam_ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.remed_pam_ucp2.data.room.entity.*
import com.example.remed_pam_ucp2.data.room.dao.BookWithAuthors
import com.example.remed_pam_ucp2.data.room.database.LibraryDatabase
import com.example.remed_pam_ucp2.data.repository.LibraryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LibraryViewModel(private val repository: LibraryRepository) : ViewModel() {

    val categories: StateFlow<List<CategoryEntity>> = repository.allCategories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val books: StateFlow<List<BookWithAuthors>> = repository.allBooks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val authors: StateFlow<List<AuthorEntity>> = repository.allAuthors
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val auditLogs: StateFlow<List<AuditLogEntity>> = repository.allLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addCategory(name: String, parentId: Int? = null, description: String? = null) {
        viewModelScope.launch {
            repository.insertCategory(CategoryEntity(name = name, parentId = parentId, description = description))
        }
    }

    fun addBook(
        title: String,
        categoryId: Int,
        isbn: String?,
        publishYear: Int,
        publisher: String?,
        language: String?,
        selectedAuthors: List<AuthorEntity>,
        initialBarcode: String
    ) {
        viewModelScope.launch {
            repository.insertBook(
                title, categoryId, isbn, publishYear, 
                publisher, language, selectedAuthors, initialBarcode
            )
        }
    }

    fun addAuthor(name: String, email: String? = null) {
        viewModelScope.launch {
            repository.insertAuthor(AuthorEntity(name = name, email = email))
        }
    }

}
