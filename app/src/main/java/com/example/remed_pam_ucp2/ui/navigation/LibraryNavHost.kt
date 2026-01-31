package com.example.remed_pam_ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.remed_pam_ucp2.ui.screen.*
import com.example.remed_pam_ucp2.ui.viewmodel.LibraryViewModel

sealed class Screen(val route: String) {
    object BookList : Screen("book_list")
    object CategoryList : Screen("category_list")
    object AddBook : Screen("add_book")
    object AddCategory : Screen("add_category")
    object AuditLogs : Screen("audit_logs")
}

@Composable
fun LibraryNavHost(
    navController: NavHostController,
    viewModel: LibraryViewModel
) {
    NavHost(navController = navController, startDestination = Screen.BookList.route) {
        composable(Screen.BookList.route) {
            BookListScreen(
                viewModel = viewModel, 
                onAddBook = { navController.navigate(Screen.AddBook.route) },
                onManageCategories = { navController.navigate(Screen.CategoryList.route) },
                onShowLogs = { navController.navigate(Screen.AuditLogs.route) }
            )
        }
        composable(Screen.CategoryList.route) {
            CategoryListScreen(
                viewModel = viewModel, 
                onAddCategory = { navController.navigate(Screen.AddCategory.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AddBook.route) {
            AddBookScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.AddCategory.route) {
            AddCategoryScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.AuditLogs.route) {
            AuditLogsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}
