package com.example.modul3_xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3_xml.databinding.ItemSongBinding

class SongAdapter(
    private val list: List<Song>,
    private val onDetailClick: (Song) -> Unit,
    private val onLinkClick: (String) -> Unit
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = list[position]
        holder.binding.apply {
            imgSong.setImageResource(song.imageResId)
            tvTitle.text = song.title

            tvAlbumName.text = song.albumName

            btnDetail.setOnClickListener {
                onDetailClick(song)
            }
            btnLink.setOnClickListener {
                onLinkClick(song.externalLink)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}