package com.ilham.scrollablelistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilham.scrollablelistapp.adapter.MovieAdapter
import com.ilham.scrollablelistapp.databinding.FragmentXmlListBinding
import com.ilham.scrollablelistapp.model.Movie
import androidx.lifecycle.ViewModelProvider
import com.ilham.scrollablelistapp.viewmodel.MovieViewModel
import androidx.recyclerview.widget.GridLayoutManager

class XmlListFragment : Fragment() {

    private var _binding: FragmentXmlListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentXmlListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.movieList.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                showRecyclerList(list)
            }
        }
    }

    private fun showRecyclerList(list: ArrayList<Movie>) {
        binding.rvMoviesHorizontal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMoviesHorizontal.adapter = MovieAdapter(list, isHorizontal = true)

        val orientation = resources.configuration.orientation
        binding.rvMoviesVertical.layoutManager = if (orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
        binding.rvMoviesVertical.adapter = MovieAdapter(list, isHorizontal = false)
        binding.rvMoviesVertical.isNestedScrollingEnabled = false
    }
}