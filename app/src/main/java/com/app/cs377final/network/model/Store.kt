package com.app.cs377final.network.model

data class Store(
    val storeID: String,
    val storeName: String,
    val images: StoreImages
)

data class StoreImages(
    val banner: String,
    val logo: String,
    val icon: String
)