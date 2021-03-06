package com.bilal.movies.app.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bilal.movies.data.toBitmap
import com.bilal.movies.databinding.FragmentMovieDetailBinding
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.usecases.LoadImageParams
import com.bilal.movies.domain.usecases.LoadImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent

class MovieDetailFragment : Fragment() {
    private val itemSelectionViewModel: SharedSelectedMovieViewModel by activityViewModels()
    private val loadImageUseCase: LoadImageUseCase by KoinJavaComponent.inject(
        LoadImageUseCase::class.java
    )

    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        binding.movieViewModel = itemSelectionViewModel
        requireActivity().title = itemSelectionViewModel.selectedMovieLiveData.value?.title
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loadImage(binding.itemIv, itemSelectionViewModel.selectedMovieLiveData.value?.backdrop_path)
    }

    fun loadImage(view: ImageView, imageID: String?) {
        if (imageID == null) {
            return
        }
        GlobalScope.launch(Dispatchers.IO) {
            val imageTaskResult = loadImageUseCase.execute(LoadImageParams(imageID))
            withContext(Dispatchers.Main) {
                imageTaskResult.let {
                    if (it is DataHolder.Success) {
                        if (it.data.isNotEmpty()) {
                            view.setImageBitmap(it.data.toBitmap())
                        }
                    }
                }
            }
        }
    }
}