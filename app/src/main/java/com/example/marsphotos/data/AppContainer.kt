package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
    // A container is an object that contains the dependencies that the app requires.
    // AppContainer nghia la 1 Container chua cac thanh phan app phu thuoc vao
    // va o muc App Level => toan app su dung dependency trong Container nay.

    // marsPhotosRepository la dependency cua ViewModel can dung de luu va ket noi
    // network lay du lieu ve. ViewModel phu thuoc vao marsPhotosRepository
    // a single data source, which is the network API call:
    // MarsApi.retrofitService.getPhotos() trong class NetworkMarsPhotosRepository()
    // trong interface MarsPhotosRepository
    // The data is exposed to the app through the repository class,
    // which abstracts away the source of the data
}

class DefaultAppContainer : AppContainer {
    private val baseUrl =
        "https://android-kotlin-fun-mars-server.appspot.com"

    private val retrofit = Retrofit.Builder() // Dung bo tao doi tuong Retrofit
        //.addConverterFactory(ScalarsConverterFactory.create()) // convert JSON to String

        // use the kotlinx.serialization converter to convert the JSON object to Kotlin objects
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl) // ket noi den web service nao
        .build() // Tao doi tuong Retrofit

    // define a public object called MarsApi to initialize
    // the Retrofit service.
    // You make this lazy initialization to make sure it is initialized
    // at its first usage.
    private val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
        // Initialize the retrofitService variable using
        // the retrofit.create() method with the MarsApiService interface.
    }
    // Each time your app calls MarsApi.retrofitService,
    // the caller accesses the same singleton Retrofit object
    // that implements MarsApiService, which is created on the first access.

    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}