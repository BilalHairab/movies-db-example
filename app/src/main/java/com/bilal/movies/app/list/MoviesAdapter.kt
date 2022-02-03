package com.bilal.movies.app.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bilal.movies.R
import com.bilal.movies.data.toBitmap
import com.bilal.movies.domain.base.DataHolder
import com.bilal.movies.domain.models.Movie
import com.bilal.movies.domain.usecases.LoadImageParams
import com.bilal.movies.domain.usecases.LoadImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent

/**
 * Created by Bilal Hairab on 03/02/2022.
 */
@SuppressLint("CheckResult")
class MoviesAdapter(private val onItemClicked: ItemClicked?) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder?>() {

    private val loadImageUseCase: LoadImageUseCase by KoinJavaComponent.inject(
        LoadImageUseCase::class.java
    )

    private var mList: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = mList[position]
        holder.itemNameTV.text = movieItem.title
        holder.itemView.setOnClickListener {
            onItemClicked?.onItemClicked(it, movieItem)
        }
        GlobalScope.launch(Dispatchers.IO) {
            movieItem.backdrop_path?.run {
                val imageTaskResult = loadImageUseCase.execute(LoadImageParams(this))
                withContext(Dispatchers.Main) {
                    imageTaskResult.let {
                        if (it is DataHolder.Success) {
                            if (it.data.isNotEmpty()) {
                                holder.movieImageView.setImageBitmap(it.data.toBitmap())
                            }
                        }
                    }

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun clear() {
        val count = itemCount
        mList.clear()
        notifyItemRangeRemoved(0, count)
    }

    fun insertElements(items: ArrayList<Movie>) {
        items.forEach {
            if (mList.contains(it).not()) {
                mList.add(it)
            }
        }
        notifyItemInserted(mList.size - 1)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImageView: ImageView = itemView.findViewById(R.id.item_iv)
        val itemNameTV: TextView = itemView.findViewById(R.id.name_tv)
    }

}