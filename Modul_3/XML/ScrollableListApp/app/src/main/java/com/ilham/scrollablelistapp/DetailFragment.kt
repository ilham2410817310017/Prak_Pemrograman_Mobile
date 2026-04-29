package com.ilham.scrollablelistapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ilham.scrollablelistapp.databinding.FragmentDetailBinding
import com.ilham.scrollablelistapp.model.Movie

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        
        val movie = arguments?.getSerializable("movie") as? Movie

        movie?.let {
            binding.apply {
                ivDetailPoster.setImageResource(it.imageRes)
                tvDetailTitle.text = it.title
                tvDetailYear.text = it.year
                tvDetailDescription.text = it.plot
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}