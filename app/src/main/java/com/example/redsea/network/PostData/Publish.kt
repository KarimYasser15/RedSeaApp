package com.example.redsea.network.PostData

data class Publish(
    var name: String,
    var from: String,
    var to: String,
    var well_data: MutableList<WellData>
)