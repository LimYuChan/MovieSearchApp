package com.devsurfer.data.repository.movie.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devsurfer.data.mapper.movie.MovieMapper
import com.devsurfer.data.model.movie.MovieResponse
import com.devsurfer.data.service.SearchService
import com.devsurfer.domain.exception.NetworkException
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.utils.Constants
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class SearchMovieRemoteDataSource(
    private val service: SearchService,
    private val query: String
): PagingSource<Int, Movie>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: Constants.NETWORK_PAGING_START_INDEX
        val display = Constants.NETWORK_PAGING_SIZE
        return try{
            val response = service.searchMovie(query, position, display)
            val movies = response.items ?: emptyList()
            val nextKey = if(movies.isEmpty()){
                null
            }else{
                val nextKeyResult = (position + Constants.NETWORK_PAGING_SIZE).plus(1)
                if(nextKeyResult >= response.total){
                    null
                }else{
                    nextKeyResult
                }
            }
            LoadResult.Page(
                data = movies.map { MovieMapper.mapperToModel(it) },
                prevKey = null,
                nextKey = nextKey
            )
        }catch (exception: IOException){
            LoadResult.Error(NetworkException.NetworkNotConnected())
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }catch (exception: NetworkException){
            LoadResult.Error(exception)
        }catch (throwable: Throwable){
            LoadResult.Error(throwable)
        }
    }

    //버튼식 페이징이 아니기 때문에 리프레시는 항상 맨 처음 index를 반환
    override fun getRefreshKey(state: PagingState<Int, Movie>):Int = Constants.NETWORK_PAGING_START_INDEX
}