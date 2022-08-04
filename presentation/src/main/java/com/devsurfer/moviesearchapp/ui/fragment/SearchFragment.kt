package com.devsurfer.moviesearchapp.ui.fragment

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.devsurfer.domain.state.ResourceState
import com.devsurfer.moviesearchapp.R
import com.devsurfer.moviesearchapp.adapter.MovieAdapter
import com.devsurfer.moviesearchapp.databinding.FragmentSearchBinding
import com.devsurfer.moviesearchapp.viewModel.SearchMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchMovieViewModel by viewModels()

    private val adapter = MovieAdapter{

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            etSearchKeyword.addTextChangedListener(onTextChanged = {str,_,_,_ ->
                buttonSearch.isEnabled = !str.isNullOrBlank()
            })
            buttonSearch.setOnClickListener {
                viewModel.searchMovie(etSearchKeyword.text.toString(), 1, 100)
            }
            rvSearchResult.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding){
            buttonSearch.isEnabled = etSearchKeyword.text.toString().isNotBlank()
            viewModel.isLoadViewVisible.observe(viewLifecycleOwner){
                layoutProgress.visibility = if(it) View.VISIBLE else View.GONE
            }

            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.searchResultState.collectLatest {
                    Log.d(TAG, "onResume: $it")
                    when(it){
                        is ResourceState.Success->{
                            adapter.submitList(it.data)
                        }
                        is ResourceState.Error->{
                            Toast.makeText(context, it.failure.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object{
        private const val TAG = "SearchFragment"
    }
}