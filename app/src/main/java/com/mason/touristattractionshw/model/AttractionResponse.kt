package com.mason.touristattractionshw.model

data class AttractionResponse(
    var total: Int,
    var data: List<Attraction>
)