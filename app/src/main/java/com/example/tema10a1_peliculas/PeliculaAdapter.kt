package com.example.tema10a1_peliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tema10a1_peliculas.databinding.ItemPeliculaBinding
import com.squareup.picasso.Picasso

class PeliculaAdapter(private var peliculas: List<Pelicula>) :
    RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder>() {

    inner class PeliculaViewHolder(val binding: ItemPeliculaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val binding = ItemPeliculaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PeliculaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val pelicula = peliculas[position]
        holder.binding.apply {
            textViewTitulo.text = pelicula.Title
            textViewAno.text = "Año: ${pelicula.Year}"
            textViewDirector.text = "Director: ${pelicula.Director}"
            Picasso.get().load(pelicula.Poster).into(imageViewPoster)
        }
    }

    override fun getItemCount(): Int = peliculas.size

    fun updateData(newPeliculas: List<Pelicula>) {
        peliculas = newPeliculas
        notifyDataSetChanged()
    }
}