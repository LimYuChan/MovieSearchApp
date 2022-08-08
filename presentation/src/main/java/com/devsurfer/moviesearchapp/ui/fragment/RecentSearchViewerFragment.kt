package com.devsurfer.moviesearchapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.devsurfer.domain.state.ResourceState
import com.devsurfer.moviesearchapp.R
import com.devsurfer.moviesearchapp.adapter.KeywordAdapter
import com.devsurfer.moviesearchapp.databinding.FragmentRecentSearchViewerBinding
import com.devsurfer.moviesearchapp.databinding.FragmentSearchBinding
import com.devsurfer.moviesearchapp.viewModel.RecentSearchViewerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 최근 검색한 결과를 보여주는 화면입니다.
 * 최근 검색 아이템을 누르면 검색화면으로 keyword를 보내게 됩니다.
 */
@AndroidEntryPoint
class RecentSearchViewerFragment: Fragment() {

    private lateinit var binding: FragmentRecentSearchViewerBinding
    private val viewModel: RecentSearchViewerViewModel by viewModels()
    
    private val adapter = KeywordAdapter{
        val action = RecentSearchViewerFragmentDirections.actionRecentSearchViewerFragmentToSearchFragment(keyword = it)
        view?.let{ view ->
            Navigation.findNavController(view).navigate(action)
        }
    }

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
        binding.rvSearchResult.adapter = adapter
        binding.actionBar.setNavigationOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.currentKeywords.collectLatest {
                    when(it){
                        is ResourceState.Success->{
                            adapter.submitList(it.data)
                        }
                        is ResourceState.Error->{
                            Log.d(TAG, "onResume: ${it.failure.message}")
                            Toast.makeText(context, it.failure.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentKeywords()

        viewModel.isLoadViewVisible.observe(viewLifecycleOwner){
            binding.layoutProgress.isVisible = it
        }
    }
    companion object{
        private const val TAG = "RecentSearchViewerFragm"
    }
}