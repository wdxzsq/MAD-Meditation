package com.example.meditation.model

data class FeelingsDataItem(
    val success: Boolean,
    val data: List<dataItem>
)

data class dataItem(
    var id: Int,
    val title: String,
    val position: Int,
    val image: String,
)
