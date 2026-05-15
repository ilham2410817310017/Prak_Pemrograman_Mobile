package com.example.scrollablelistcompose.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
// SESUAI GAMBAR: Screen ada di sub-folder screens
import com.example.scrollablelistcompose.presentation.screens.ComposeListScreen
// SESUAI GAMBAR: ViewModel ada di folder viewmodel
import com.example.scrollablelistcompose.viewmodel.MovieViewModel
import com.example.scrollablelistcompose.viewmodel.MovieViewModelFactory

class ComposeListFragment : Fragment() {

    // Poin b: Inisialisasi ViewModel menggunakan Factory (Analisis PPT Modul 4)
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory("Muhammad Ilham")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    // Memanggil layar utama Compose dari folder screens
                    ComposeListScreen(viewModel = viewModel)
                }
            }
        }
    }
}