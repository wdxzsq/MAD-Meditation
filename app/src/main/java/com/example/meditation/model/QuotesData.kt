package com.example.meditation.model

data class QuotesDataItem(
    val success: Boolean,
    val data: List<ddataItem>
)

data class ddataItem(
    val id: Int,
    val title: String,
    val image: String,
    val description: String
)
