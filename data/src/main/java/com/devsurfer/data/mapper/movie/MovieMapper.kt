package com.devsurfer.data.mapper.movie

import com.devsurfer.data.model.movie.MovieResponse
import com.devsurfer.domain.model.movie.Movie

object MovieMapper {
    fun mapperToModel(response: MovieResponse): Movie =
        Movie(
            title = response.title,
            link = response.link,
            image = response.image,
            subtitle = response.subtitle,
            pubDate = response.pubDate,
            director = response.director,
            actor = response.actor,
            userRating = response.userRating
        )
}