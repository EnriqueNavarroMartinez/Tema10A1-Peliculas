package com.example.tema10a1_peliculas

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tema10a1_peliculas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PeliculaAdapter

    private val apiKey = "a9d6bd05" // Coloca tu clave API aquí.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el RecyclerView con un adaptador vacío
        setupRecyclerView(emptyList())

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PeliculaApiService::class.java)

        // Lista de IMDb IDs que deseas buscar
        val imdbIds = listOf("tt3896198", "tt4154796", "tt1375666")

        // Llamar a la API para cada IMDb ID
        CoroutineScope(Dispatchers.IO).launch {
            val peliculas = imdbIds.mapNotNull { imdbId ->
                try {
                    val response = service.getPelicula(apiKey, imdbId).execute()
                    if (response.isSuccessful) {
                        val pelicula = response.body()
                        Log.d("MainActivity", "Película recibida: ${pelicula?.Title}")
                        pelicula
                    } else {
                        Log.e("MainActivity", "Error en la respuesta: ${response.errorBody()?.string()}")
                        null
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error: ${e.message}")
                    null
                }
            }

            withContext(Dispatchers.Main) {
                // Actualizar el adaptador con la lista de películas
                adapter.updateData(peliculas)
            }
        }
    }

    private fun setupRecyclerView(peliculas: List<Pelicula>) {
        adapter = PeliculaAdapter(peliculas)
        binding.recyclerViewPeliculas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
}