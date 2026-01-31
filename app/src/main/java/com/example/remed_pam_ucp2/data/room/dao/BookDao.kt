package com.example.remed_pam_ucp2.data.room.dao

import androidx.room.*
import com.example.remed_pam_ucp2.data.room.entity.*
import kotlinx.coroutines.flow.Flow

data class BookWithAuthors(
    @Embedded val book: BookEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = BookAuthorCrossRef::class,
            parentColumn = "bookId",
            entityColumn = "authorId"
        )
    )
    val authors: List<AuthorEntity>
)

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookAuthors(crossRefs: List<BookAuthorCrossRef>)

    @Transaction
    @Query("SELECT * FROM books WHERE deletedAt IS NULL")
    fun getAllBooksWithAuthors(): Flow<List<BookWithAuthors>>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: Int): BookEntity?

    @Query("UPDATE books SET deletedAt = :timestamp WHERE id = :id")
    suspend fun softDelete(id: Int, timestamp: Long = System.currentTimeMillis())

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookItem(item: BookItemEntity)

    @Query("SELECT * FROM book_items WHERE bookId = :bookId AND deletedAt IS NULL")
    fun getItemsForBook(bookId: Int): Flow<List<BookItemEntity>>

    @Query("SELECT * FROM book_items WHERE uniqueId = :uniqueId")
    suspend fun findItemByBarcode(uniqueId: String): BookItemEntity?

    @Query("SELECT * FROM books WHERE isbn = :isbn AND deletedAt IS NULL")
    suspend fun findBookByIsbn(isbn: String): BookEntity?

    @Query("UPDATE books SET deletedAt = :timestamp WHERE categoryId = :categoryId")
    suspend fun softDeleteBooksByCategory(categoryId: Int, timestamp: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM book_items WHERE bookId IN (SELECT id FROM books WHERE categoryId = :categoryId) AND status = 'Borrowed'")
    suspend fun countBorrowedBooksInCategory(categoryId: Int): Int

    @Query("UPDATE books SET categoryId = NULL WHERE categoryId = :categoryId")
    suspend fun setBooksToNoCategory(categoryId: Int)
}
