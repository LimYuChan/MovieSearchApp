package com.devsurfer.data.model.searchKeyword

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class SearchKeywordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val search_keyword: String
)
