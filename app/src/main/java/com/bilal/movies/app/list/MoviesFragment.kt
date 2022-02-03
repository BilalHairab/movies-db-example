package com.bilal.movies.app.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bilal.movies.R
import com.bilal.movies.app.detail.SharedSelectedMovieViewModel
import com.bilal.movies.domain.models.Movie

class MoviesFragment : Fragment(), ItemClicked {
    private val itemSelectionViewModel: SharedSelectedMovieViewModel by activityViewModels()
    private lateinit var getMoviesViewModel: MoviesListViewModel

    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var ratedRecyclerView: RecyclerView
    private lateinit var revenueRecyclerView: RecyclerView

    private lateinit var poplarMoviesAdapter: MoviesAdapter
    private lateinit var ratedMoviesAdapter: MoviesAdapter
    private lateinit var revenueMoviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMoviesViewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init RecyclerViews
        popularRecyclerView = view.findViewById(R.id.popularItems_rv)
        ratedRecyclerView = view.findViewById(R.id.ratedItems_rv)
        revenueRecyclerView = view.findViewById(R.id.revenueItems_rv)

        //Init RecyclerViews Adapters
        poplarMoviesAdapter = MoviesAdapter(this)
        ratedMoviesAdapter = MoviesAdapter(this)
        revenueMoviesAdapter = MoviesAdapter(this)

        initRecyclerView(popularRecyclerView, poplarMoviesAdapter) { addPopularMoviesPage() }
        initRecyclerView(ratedRecyclerView, ratedMoviesAdapter) { addTopRatedMoviesPage() }
        initRecyclerView(revenueRecyclerView, revenueMoviesAdapter) { addRevenueMoviesPage() }

        refreshItems()

        getMoviesViewModel.errorLiveData.observe(viewLifecycleOwner, { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_LONG).show()
        })

        getMoviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, { result ->
            handleAddingItems(result, poplarMoviesAdapter)
        })

        getMoviesViewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner, { result ->
            handleAddingItems(result, ratedMoviesAdapter)
        })
        getMoviesViewModel.revenueMoviesLiveData.observe(viewLifecycleOwner, { result ->
            handleAddingItems(result, revenueMoviesAdapter)
        })
    }

    private fun addPopularMoviesPage() {
        getMoviesViewModel.getPopularPage()
    }

    private fun addTopRatedMoviesPage() {
        getMoviesViewModel.getTopRatedPage()
    }

    private fun addRevenueMoviesPage() {
        getMoviesViewModel.getRevenuePage()
    }

    private fun handleAddingItems(newItems: ArrayList<Movie>, adapter: MoviesAdapter) {
        adapter.insertElements(newItems)
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        adapter: MoviesAdapter,
        refreshBlock: () -> Unit
    ) {
        context?.run {
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = layoutManager

            val dividerItemDecoration = ItemsSpacingDecoration(8, 8)
            recyclerView.addItemDecoration(dividerItemDecoration)
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            println("The RecyclerView is not scrolling")
                            val lastPosition = layoutManager.findLastVisibleItemPosition()
                            if (lastPosition >= layoutManager.itemCount - 3) {
                                refreshBlock.invoke()
                            }
                        }
                    }
                }
            })
        }
    }

    private fun refreshItems() {
        addPopularMoviesPage()
        addTopRatedMoviesPage()
        addRevenueMoviesPage()
    }

    override fun onItemClicked(view: View, movie: Movie) {
        itemSelectionViewModel.select(movie)
        Navigation.findNavController(view)
            .navigate(R.id.action_imagesFragment_to_imageDetailFragment)
    }
}