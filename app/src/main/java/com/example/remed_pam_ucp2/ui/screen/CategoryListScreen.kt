package com.example.remed_pam_ucp2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.remed_pam_ucp2.data.room.entity.CategoryEntity
import com.example.remed_pam_ucp2.ui.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    viewModel: LibraryViewModel,
    onAddCategory: () -> Unit,
    onBack: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { 
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("MANAJEMEN KATEGORI") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            ) 
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onAddCategory,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp)
        ) {
            val rootCategories = categories.filter { it.parentId == null }
            items(rootCategories) { category ->
                CategoryItemRecursive(category, categories, depth = 0, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CategoryItemRecursive(
    category: CategoryEntity,
    allCategories: List<CategoryEntity>,
    depth: Int,
    viewModel: LibraryViewModel
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    
    Column(modifier = Modifier.padding(start = (depth * 20).dp, top = 4.dp, bottom = 4.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 1f - (depth * 0.1f))),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp), 
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Icon(
                        if (depth == 0) Icons.Default.Folder else Icons.Default.SubdirectoryArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = category.name, style = MaterialTheme.typography.titleMedium)
                }
                var showDeleteConfirm by remember { mutableStateOf(false) }
                IconButton(onClick = { showDeleteConfirm = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
                }

                if (showDeleteConfirm) {
                    AlertDialog(
                        onDismissRequest = { showDeleteConfirm = false },
                        title = { Text("Hapus?") },
                        text = { Text("Yakin hapus kategori ${category.name}?") },
                        confirmButton = {
                            TextButton(onClick = {
                                android.widget.Toast.makeText(context, "Error: Fitur hapus belum siap!", android.widget.Toast.LENGTH_SHORT).show()
                                showDeleteConfirm = false
                            }) { Text("YA") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteConfirm = false }) { Text("TIDAK") }
                        }
                    )
                }
            }
        }
    }
}
