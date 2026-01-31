package com.example.remed_pam_ucp2.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.remed_pam_ucp2.data.room.entity.AuthorEntity
import com.example.remed_pam_ucp2.ui.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    viewModel: LibraryViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var publishYear by remember { mutableStateOf("") }
    var publisher by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var initialBarcode by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    val categories by viewModel.categories.collectAsState()
    val authors by viewModel.authors.collectAsState()
    val selectedAuthors = remember { mutableStateListOf<AuthorEntity>() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = { Text("TAMBAH BUKU") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            item {
                Text(
                    "METADATA BUKU",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = title, onValueChange = { title = it },
                    label = { Text("Judul Buku (Wajib)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = isbn, onValueChange = { isbn = it },
                        label = { Text("ISBN (Opsional)") },
                        modifier = Modifier.weight(1f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = publishYear,
                        onValueChange = { if (it.all { c -> c.isDigit() }) publishYear = it },
                        label = { Text("Tahun (Wajib)") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = publisher, onValueChange = { publisher = it },
                    label = { Text("Penerbit") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = language, onValueChange = { language = it },
                    label = { Text("Bahasa") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "FISIK BUKU (EKSEMPLAR)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = initialBarcode, onValueChange = { initialBarcode = it },
                    label = { Text("ID Eksemplar / Barcode (Wajib & Unik)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "PILIH KATEGORI (WAJIB)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        if (categories.isEmpty()) {
                            Text(
                                "Belum ada kategori. Sila tambah kategori dulu.",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        categories.forEach { category ->
                            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedCategoryId == category.id,
                                    onClick = { selectedCategoryId = category.id }
                                )
                                Text(category.name, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "PENGARANG (MINIMAL 1)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Card(
                    c
                }

            }
        }
    }

}
