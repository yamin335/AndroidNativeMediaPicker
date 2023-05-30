package mollah.yamin.androidnativemediapicker.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mollah.yamin.androidnativemediapicker.databinding.ListItemMediaBinding

class MediaRecyclerAdapter: RecyclerView.Adapter<MediaRecyclerAdapter.ViewHolder>() {

    private var dataList: List<Pair<Bitmap, String>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemMediaBinding = ListItemMediaBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    inner class ViewHolder (private val binding: ListItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = dataList[position]
            Glide.with(binding.root.context)
                .load(item.first)
                .into(binding.preview)

            if (item.second == "video") {
                binding.icPlay.visibility = View.VISIBLE
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: List<Pair<Bitmap, String>>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
}
