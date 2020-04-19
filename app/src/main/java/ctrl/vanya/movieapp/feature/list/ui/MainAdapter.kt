package ctrl.vanya.movieapp.feature.list.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ctrl.vanya.movieapp.R
import ctrl.vanya.movieapp.core.model.MovieMdl
import ctrl.vanya.movieapp.core.utils.GlideUtils
import ctrl.vanya.movieapp.core.utils.OnLoadMoreListener
import ctrl.vanya.movieapp.feature.detail.ui.DetailActivity

class MainAdapter (private val context: Context) : ListAdapter<MainAdapter.DataItem, RecyclerView.ViewHolder>(
    DiffCallback()
){

    private val loading = 1
    private val content = 2
    var isLoading = false
    private var isMoreDataAvailable = true
    private var loadMoreListener: OnLoadMoreListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            loading -> {
                return LoadHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.progress_dialog,
                        parent,
                        false
                    )
                )
            }
            content -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_list_movie,
                        parent,
                        false
                    )
                )
            }
            else -> throw ClassCastException("unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= itemCount - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = false
            loadMoreListener!!.onLoadMore()
        }

        if (holder is ViewHolder) {
            val item = getItem(position) as DataItem.MovieList
            holder.bind(item.item)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_IMDB, item.movieId)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.MovieList -> content
            is DataItem.Footer -> loading
        }
    }

    override fun submitList(list: List<DataItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    fun addDataAndSubmit(list: List<MovieMdl>) {
        val items: List<DataItem>? = if (list.size != 1) {
            when (itemCount) {
                list.size -> list.map {
                    DataItem.MovieList(
                        it
                    )
                }
                else -> list.map {
                    DataItem.MovieList(
                        it
                    )
                } + listOf(DataItem.Footer)
            }
        } else {
            list.map {
                DataItem.MovieList(
                    it
                )
            }
        }

        submitList(items)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgPhoto: ImageView = itemView.findViewById(R.id.iv_movie_list)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_movie_list_title)
        private val tvYear: TextView = itemView.findViewById(R.id.tv_movie_list_year)

        @SuppressLint("SetTextI18n")
        fun bind(
            item: MovieMdl
        ) {

            tvTitle.text = item.title
            tvYear.text = item.year
            GlideUtils.glideOverrideSize(itemView.context, item.poster!!, imgPhoto)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_IMDB, item.imdbID)
                itemView.context.startActivity(intent)
            }
        }
    }

    private class LoadHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    sealed class DataItem {
        data class MovieList(val item: MovieMdl) : DataItem() {
            override val movieId = item.imdbID!!
        }

        object Footer: DataItem() {
            override val movieId = (Int.MIN_VALUE).toString()
        }

        abstract val movieId: String
    }

    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(
            oldItem: DataItem, newItem: DataItem
        ): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: DataItem, newItem: DataItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun setMoreDataAvailable(moreDataAvailable: Boolean) {
        isMoreDataAvailable = moreDataAvailable
    }

    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }
}