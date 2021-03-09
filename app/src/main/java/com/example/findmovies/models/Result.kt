package com.example.findmovies.models

data class Result(
        val page: Int,
        val results: List<MovieModel>,
        val total_pages: Int,
        val total_results: Int
)