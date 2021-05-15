package com.schuster.threading

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.schuster.threading.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "AstrosRepository"
   }

    val context: Context = this
    private lateinit var binding: ActivityMainBinding

    private val progressBar: ProgressBar by lazy {
        binding.progressbarLoadIndicator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()

    }

    private fun initButton() {
        //TODO: 018 - fazer o handle do clique do botão
        binding.buttonLoadData.setOnClickListener {
            binding.textviewData.text = ""
            launchData()
        }
    }

    //TODO: 013 - Criar função para exibir os dados carregados
    private fun showData(astros: List<AstrosPeople>?) {
        binding.textviewData.text = ""
        astros?.forEach { people ->
            binding.textviewData.append("${people.name} - ${people.craft} \n\n\n")
        }
    }

    //TODO: 014 - Criar função para exibir a ProgressBar
    private fun showLoadingIndicator(){
        progressBar.visibility = View.VISIBLE
    }

    //TODO: 015 - Criar função para esconder a ProgressBar
    private fun hideLoadingIndicator() {
        progressBar.visibility = View.GONE
    }

    //TODO: 017 - Criar função para buscar os dados na APi com lifecycleScope
    private fun launchData() {

        lifecycleScope.launch {
            showLoadingIndicator()

            try {
                val repository = AstrosRepository()
                val result = repository.loadAstros()
                showData(result?.people)
                hideLoadingIndicator()

            } catch (exception: Exception) {
                Log.d(TAG, exception.message.toString())
                Toast.makeText(context, "Falha ao buscar dados, tente novamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
