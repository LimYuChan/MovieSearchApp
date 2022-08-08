package com.devsurfer.moviesearchapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.devsurfer.moviesearchapp.R
import com.devsurfer.moviesearchapp.adapter.MovieAdapter
import com.devsurfer.moviesearchapp.databinding.FragmentSearchBinding
import com.devsurfer.moviesearchapp.viewModel.SearchMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 영화를 검색하는 화면입니다.
 * 화면 생성시 받은 argutment가 있다면 해당 argument로 검색을 실행합니다.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchMovieViewModel by viewModels()
    private val args: SearchFragmentArgs by navArgs()

    private val adapter = MovieAdapter{
        if(activity != null && isAdded){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.link)))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.keyword?.let {
            viewModel.setLastKeyword(it)
            viewModel.searchMovie(it)
        }
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
                val keyword = etSearchKeyword.text.toString()
                viewModel.setLastKeyword(keyword)
                viewModel.searchMovie(keyword)
            }
            buttonRecentSearch.setOnClickListener {
                val action = SearchFragmentDirections.actionSearchFragmentToRecentSearchViewerFragment()
                Navigation.findNavController(view).navigate(action)
            }
            rvSearchResult.adapter = adapter

            buttonRetry.setOnClickListener {
                adapter.retry()
            }

            //화면이 재생성된다면 마지막에 검색했던 키워드로 재검색
            viewModel.lastKeyword.observe(viewLifecycleOwner){
                if(it.isNotBlank()){
                    viewModel.searchMovie(it)
                }
            }

            //Paging Adapter 상태 별 UI 컨트롤 (로딩, 오류)
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest{
                    val isListEmpty = it.refresh is LoadState.NotLoading && adapter.itemCount == 0
                    textEmptyListPlaceHolder.isVisible = isListEmpty
                    rvSearchResult.isVisible = !isListEmpty
                    layoutProgress.isVisible = it.source.refresh is LoadState.Loading
                    layoutRetry.isVisible = it.source.refresh is LoadState.Error

                    val errorState = it.source.append as? LoadState.Error
                        ?: it.source.prepend as? LoadState.Error
                        ?: it.append as? LoadState.Error
                        ?: it.prepend as? LoadState.Error
                        ?: it.refresh as? LoadState.Error
                    errorState?.let { error->
                        Toast.makeText(
                            context,
                            error.error.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.pagingDataFlow.collectLatest(adapter::submitData)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding){
            buttonSearch.isEnabled = etSearchKeyword.text.toString().isNotBlank()
        }
    }

    companion object{
        private const val TAG = "SearchFragment"
    }
}