    package com.ilham.scrollablelistapp

    import android.content.Intent
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.Lifecycle
    import androidx.lifecycle.lifecycleScope
    import androidx.lifecycle.repeatOnLifecycle
    import androidx.recyclerview.widget.GridLayoutManager
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.ilham.scrollablelistapp.adapter.MovieAdapter
    import com.ilham.scrollablelistapp.databinding.FragmentXmlListBinding
    import com.ilham.scrollablelistapp.model.Movie
    import com.ilham.scrollablelistapp.viewmodel.MovieViewModel
    import com.ilham.scrollablelistapp.viewmodel.MovieViewModelFactory
    import kotlinx.coroutines.launch
    import timber.log.Timber
    import androidx.navigation.fragment.findNavController

    class XmlListFragment : Fragment() {

        private var _binding: FragmentXmlListBinding? = null
        private val binding get() = _binding!!

        private val factory = MovieViewModelFactory("Muhammad Ilham")
        private val viewModel: MovieViewModel by viewModels { factory }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            _binding = FragmentXmlListBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.movieList.collect { list ->
                        if (list.isNotEmpty()) {
                            showRecyclerList(ArrayList(list))
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.navigateToDetail.collect { movie ->
                        movie?.let {
                            Timber.tag("Navigation").d("Berpindah ke halaman Detail untuk film: ${it.title}")
                            val bundle = Bundle().apply {
                                putSerializable("movie", it)
                            }
                            findNavController().navigate(R.id.action_xmlListFragment_to_detailFragment, bundle)
                            viewModel.onNavigated()
                        }
                    }
                }
            }
        }

        private fun showRecyclerList(list: ArrayList<Movie>) {
            binding.rvMoviesHorizontal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvMoviesHorizontal.adapter = MovieAdapter(list, true) { movie ->
                viewModel.onMovieClicked(movie)
            }

            val orientation = resources.configuration.orientation
            binding.rvMoviesVertical.layoutManager = if (orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            binding.rvMoviesVertical.adapter = MovieAdapter(list, false) { movie ->
                viewModel.onMovieClicked(movie)
            }
            binding.rvMoviesVertical.isNestedScrollingEnabled = false
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }