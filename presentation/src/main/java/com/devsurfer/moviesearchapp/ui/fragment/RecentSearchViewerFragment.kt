package com.devsurfer.moviesearchapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.devsurfer.moviesearchapp.R
import com.devsurfer.moviesearchapp.databinding.FragmentRecentSearchViewerBinding
import com.devsurfer.moviesearchapp.databinding.FragmentSearchBinding

class RecentSearchViewerFragment: Fragment() {
    private lateinit var binding: FragmentRecentSearchViewerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recent_search_viewer, container, false)
        binding = FragmentRecentSearchViewerBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}