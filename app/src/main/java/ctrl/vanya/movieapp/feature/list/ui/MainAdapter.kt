package ctrl.vanya.movieapp.feature.list.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ctrl.vanya.movieapp.R
import ctrl.vanya.movieapp.core.model.MovieMdl
import ctrl.vanya.movieapp.core.utils.GlideUtils
import ctrl.vanya.movieapp.core.utils.OnLoadMoreListener
import ctrl.vanya.movieapp.feature.detail.ui.DetailActivity
import java.util.ArrayList

class MainAdapter(private val activity: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMovie = ArrayList<MovieMdl>()

    private val typeProgress = 0
    private val typeLinear = 1

    private var isLoading = false
    private var isMoreDataAvailable = true

    private var loadMoreListener: OnLoadMoreListener? = null

    fun setData(items: List<MovieMdl>) {
        if (listMovie.isNotEmpty()) {
            listMovie.add(MovieMdl("load"))
            this.notifyItemInserted(listMovie.size - 1)
            if (items.isNotEmpty()) {
                listMovie.removeAt(listMovie.size - 1)
                listMovie.addAll(items)
            } else {
                this.setMoreDataAvailable(false)
            }
        } else {
            listMovie.addAll(items)
        }
        notifyDataSetChanged()
        isLoading = false
    }

    fun clearList() {
        listMovie.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(activity)
        return if (viewType == typeLinear) {
            MovieViewHolder(inflater.inflate(R.layout.item_list_movie, parent, false))
        } else {
            LoadHolder(inflater.inflate(R.layout.progress_dialog, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= itemCount - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true
            loadMoreListener!!.onLoadMore()
        }

        if (getItemViewType(position) == typeLinear) {
            (holder as MovieViewHolder)
            holder.bind(listMovie[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listMovie[position].loading == "load") {
            typeProgress
        } else {
            typeLinear
        }
    }

    override fun getItemCount() = listMovie.size

    class MovieViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val imgPhoto: ImageView = itemView.findViewById(R.id.iv_movie_list)
        private val tvName: TextView = itemView.findViewById(R.id.tv_movie_list_title)
        private val tvYear: TextView = itemView.findViewById(R.id.tv_movie_list_year)


        fun bind(movie: MovieMdl) {
            if (movie.title != null) {
                tvName.text = movie.title
                tvYear.text = movie.year
                GlideUtils.glideOverrideSize(itemView.context, movie.poster!!, imgPhoto)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_IMDB, movie.imdbID)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    private class LoadHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    fun setMoreDataAvailable(moreDataAvailable: Boolean) {
        isMoreDataAvailable = moreDataAvailable
    }

    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }
}