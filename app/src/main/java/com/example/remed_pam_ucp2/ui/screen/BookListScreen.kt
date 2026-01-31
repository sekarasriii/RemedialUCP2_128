package com.example.remed_pam_ucp2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.example.remed_pam_ucp2.data.room.entity.CategoryEntity
import com.example.remed_pam_ucp2.ui.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    viewModel: LibraryViewModel,
    onAddBook: () -> Unit,
    onManageCategories: () -> Unit,
    onShowLogs: () -> Unit
) {
    val books by viewModel.books.collectAsState()
    val categories by viewModel.categories.collectAsState()
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    val activeCategoryIds = remember(selectedCategoryId, categories) {
        if (selectedCategoryId == null) null
        else getDescendantIds(selectedCategoryId!!, categories) + selectedCategoryId!!
    }

    val filteredBooks = if (activeCategoryIds == null) books 
                        else books.filter { it.book.categoryId in activeCategoryIds }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { 
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("PERPUSTAKAAN", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = onShowLogs) {
                        Icon(Icons.Default.History, contentDescription = "Logs", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onManageCategories) {
                        Icon(Icons.Default.Category, contentDescription = "Kategori", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            ) 
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onAddBook,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Category Filter Row
            ScrollableTabRow(
                selectedTabIndex = if (selectedCategoryId == null) 0 else 1,
                containerColor = MaterialTheme.colorScheme.background,
                edgePadding = 16.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[if (selectedCategoryId == null) 0 else 1]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                Tab(
                    selected = selectedCategoryId == null, 
                    onClick = { selectedCategoryId = null },
                    text = { Text("SEMUA", style = MaterialTheme.typography.labelLarge) }
                )
                categories.distinctBy { it.name.lowercase() }.forEach { category ->
                    Tab(
                        selected = selectedCategoryId == category.id, 
                        onClick = { selectedCategoryId = category.id },
                        text = { Text(category.name.uppercase(), style = MaterialTheme.typography.labelLarge) }
                    )
                }
            }

            if (filteredBooks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Book, 
                            contentDescription = null, 
                            modifier = Modifier.size(64.dp), 
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Belum ada buku", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    items(filteredBooks) { bookWithAuthors ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = bookWithAuthors.book.title, 
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Person, 
                                        contentDescription = null, 
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = bookWithAuthors.authors.joinToString { it.name },
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getDescendantIds(parentId: Int, categories: List<CategoryEntity>): List<Int> {
    val children = categories.filter { it.parentId == parentId }
    return children.map { it.id } + children.flatMap { getDescendantIds(it.id, categories) }
}
