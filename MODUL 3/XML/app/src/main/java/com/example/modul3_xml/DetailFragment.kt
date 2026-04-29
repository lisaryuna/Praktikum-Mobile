package com.example.modul3_xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.modul3_xml.databinding.FragmentDetailBinding
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val song = arguments?.getSerializable("song") as? Song
        if (song != null) {
            binding.imgDetailCover.setImageResource(song.imageResId)
            binding.tvDetailTitle.text = song.title
            binding.tvDetailAlbum.text = "Album: ${song.albumName}"
            binding.tvDetailYear.text = "Release Year: ${song.year}"
            binding.tvDetailDesc.text = song.description
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}