package com.example.remed_pam_ucp2.data.repository

import com.example.remed_pam_ucp2.data.room.dao.*
import com.example.remed_pam_ucp2.data.room.entity.*
import kotlinx.coroutines.flow.Flow

class LibraryRepository(
    private val categoryDao: CategoryDao,
    private val bookDao: BookDao,
    private val authorDao: AuthorDao,
    private val auditDao: AuditDao
) {
    val allCategories: Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    suspend fun insertCategory(category: CategoryEntity) {
        if (category.id != 0 && category.id == category.parentId) return
        if (category.parentId != null && isDescendant(category.parentId, category.id)) return

        val existing = categoryDao.findCategoryByNameAndParent(category.name, category.parentId)
        if (existing != null) return

        categoryDao.insertCategory(category)
        recordAudit("categories", "Insert/Update", null, "Name: ${category.name}, Parent: ${category.parentId}")
    }

    private suspend fun isDescendant(potentialDescendant: Int, categoryId: Int): Boolean {
        if (potentialDescendant == categoryId) return true
        val children = categoryDao.getSubCategories(categoryId)
        for (child in children) {
            if (isDescendant(potentialDescendant, child.id)) return true
        }
        return false
    }

    suspend fun deleteCategory(categoryId: Int, deleteBooks: Boolean) {
        try {
            val borrowedCount = bookDao.countBorrowedBooksInCategory(categoryId)
            if (borrowedCount > 0) {
                recordAudit("categories", "Gagal Hapus", categoryId.toString(), "Ada buku dipinjam")
                throw IllegalStateException("Masih ada buku dipinjam!")
            }

            bookDao.setBooksToNoCategory(categoryId)
            categoryDao.softDelete(categoryId)
            recordAudit("categories", "Hapus", categoryId.toString(), "Berhasil")
        } catch (e: Exception) {
            throw e
        }
    }

    val allBooks: Flow<List<BookWithAuthors>> = bookDao.getAllBooksWithAuthors()

    suspend fun insertBook(
        title: String,
        categoryId: Int,
        isbn: String?,
        publishYear: Int,
        publisher: String?,
        language: String?,
        authors: List<AuthorEntity>,
        initialCopyBarcode: String
    ) {
        // TODO: Cek ISBN & Barcode Unik (Poin 3 & 4) - Masih manual cek manual di otak dulu

        val book = BookEntity(
            title = title,
            categoryId = categoryId,
            isbn = isbn,
            publishYear = publishYear,
            publisher = publisher,
            language = language
        )
        val bookId = bookDao.insertBook(book).toInt()
        
        val crossRefs = authors.map { BookAuthorCrossRef(bookId, it.id) }
        bookDao.insertBookAuthors(crossRefs)
        
        bookDao.insertBookItem(BookItemEntity(uniqueId = initialCopyBarcode, bookId = bookId, status = "Available"))
        
        recordAudit("books", "Tambah", null, "Judul: $title, ISBN: $isbn")
    }

    val allAuthors: Flow<List<AuthorEntity>> = authorDao.getAllAuthors()
    suspend fun insertAuthor(author: AuthorEntity) {
        authorDao.insertAuthor(author)
        recordAudit("authors", "Tambah", null, "Nama: ${author.name}")
    }

    val allLogs: Flow<List<AuditLogEntity>> = auditDao.getAllLogs()

    private suspend fun recordAudit(table: String, action: String, before: String?, after: String?) {
        auditDao.insertAudit(AuditLogEntity(tableName = table, action = action, dataBefore = before, dataAfter = after))
    }

    fun getAllCategoriesFlow() = categoryDao.getAllCategories()
}
