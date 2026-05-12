package com.example.modul3_xml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.modul3_xml.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SongViewModel
    private lateinit var adapter: SongAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = SongViewModelFactory("Data Master Music CAS")
        viewModel = ViewModelProvider(this, factory)[SongViewModel::class.java]

        adapter = SongAdapter(emptyList(),
            onDetailClick = { song -> viewModel.onDetailClicked(song) },
            onLinkClick = { url -> viewModel.onIntentClicked(url)}
        )

        binding.rvSongs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSongs.adapter = adapter

        binding.rvHighlight.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHighlight.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvHighlight)

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_language)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.songList.collect { songs ->
                        adapter.updateData(songs)
                    }
                }

                launch {
                    viewModel.navigationEvent.collect { song ->
                        song?.let {
                            val bundle = Bundle().apply { putSerializable("song", it) }
                            findNavController().navigate(R.id.action_home_to_detail, bundle)
                            viewModel.onNavigationHandled()
                        }
                    }
                }

                launch {
                    viewModel.intentEvent.collect { url ->
                        url?.let {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            startActivity(intent)
                            viewModel.onIntentHandled()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}