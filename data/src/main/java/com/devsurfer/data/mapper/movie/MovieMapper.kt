package com.devsurfer.data.mapper.movie

import com.devsurfer.data.model.movie.MovieResponse
import com.devsurfer.domain.model.movie.Movie

//Movie 객체는 data -> domain으로만 작동하기 때문에 toModel mapper만 작성했습니다.

fun MovieResponse.toModel(): Movie =
    Movie(
        title = this.title,
        link = this.link,
        image = this.image,
        subtitle = this.subtitle,
        pubDate = this.pubDate,
        director = this.director,
        actor = this.actor,
        userRating = this.userRating
    )