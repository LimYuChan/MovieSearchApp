package com.devsurfer.data.repository.movie.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devsurfer.data.mapper.movie.toModel
import com.devsurfer.data.service.SearchService
import com.devsurfer.domain.exception.NetworkException
import com.devsurfer.domain.model.movie.Movie
import com.devsurfer.domain.utils.Constants
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

/**
 * Paging3 라이브러리 사용을 위한 Data source 입니다.
 * PagingSource - Int = Paging index
 * PagingSource - Movie = 페이징 될 객체
 * Paging Adapter에서 데이터 로드가 더 필요하다면 호출하게 됩니다.
 * 버튼식 페이징이 아니라 스크롤식 페이징이기 때문에 Refresh key 는 항상 처음 인덱스인 1을 반환합니다.
 *
 */

class SearchMovieRemoteDataSource(
    private val service: SearchService, private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: Constants.NETWORK_PAGING_START_INDEX
        val display = Constants.NETWORK_PAGING_SIZE
        return try {
            val response = service.searchMovie(query, position, display)
            val movies = response.items ?: emptyList()
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                //해당 작업을 해주지 않으면 paging position 이 계속 늘어나도 같은 객체를 가져오는 문제가 있어서 Total과 비교하여 더이상 데이터가 없으면 작동하지 않도록 동작했습니다.
                val nextKeyResult = (position + Constants.NETWORK_PAGING_SIZE).plus(1)
                if (nextKeyResult >= response.total) {
                    null
                } else {
                    nextKeyResult
                }
            }
            //제목 중간에 나오는 HTML 태그 정리
            val data = movies.map {
                it.copy(
                    title = it.title.replace("<b>", "").replace("</b>", "")
                ).toModel()
            }
            LoadResult.Page(
                data = data, prevKey = null, nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(NetworkException.NetworkNotConnected())
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: NetworkException) {
            LoadResult.Error(exception)
        } catch (throwable: Throwable) {
            LoadResult.Error(throwable)
        }
    }

    //버튼식 페이징이 아니기 때문에 리프레시는 항상 맨 처음 index를 반환
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int = Constants.NETWORK_PAGING_START_INDEX
}