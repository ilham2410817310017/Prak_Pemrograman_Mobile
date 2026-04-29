package com.ilham.scrollablelistapp.adapter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ilham.scrollablelistapp.R
import com.ilham.scrollablelistapp.databinding.ItemMovieBinding
import com.ilham.scrollablelistapp.databinding.ItemMovieHorizontalBinding
import com.ilham.scrollablelistapp.model.Movie

class MovieAdapter(
    private val listMovie: List<Movie>,
    private val isHorizontal: Boolean = false
) : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (isHorizontal) {
            ItemMovieHorizontalBinding.inflate(inflater, parent, false)
        } else {
            ItemMovieBinding.inflate(inflater, parent, false)
        }
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = listMovie[position]

        when (val binding = holder.binding) {
            is ItemMovieBinding -> {
                binding.ivMoviePoster.setImageResource(movie.imageRes)
                binding.tvTitle.text = movie.title
                binding.tvYear.text = movie.year
                binding.tvDescription.text = movie.plot

                setupActions(binding.btnImdb, binding.btnDetail, movie)
            }
            is ItemMovieHorizontalBinding -> {
                binding.imgMovie.setImageResource(movie.imageRes)
                binding.tvTitle.text = movie.title
                binding.tvDescription.text = movie.plot

                setupActions(binding.btnImdb, binding.btnDetail, movie)
            }
        }
    }

    private fun setupActions(btnImdb: android.widget.Button, btnDetail: android.widget.Button, movie: Movie) {
        btnImdb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.imdbUrl))
            it.context.startActivity(intent)
        }

        btnDetail.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("movie", movie)
            it.findNavController().navigate(
                R.id.action_xmlListFragment_to_detailFragment,
                bundle
            )
        }
    }
}