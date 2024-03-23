package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    // Tuy nhien, van can test lay du lieu bang network gia lap nen can dung
    // cach truyen repository tu ngoai vao, de thay doi linh hoat network that va gia lap
    // nen can dung 1 container de cung cap repository cho ViewModel (MarsViewModel)

    // A container is an object that contains the dependencies that the app requires.
    // AppContainer nghia la 1 Container chua cac thanh phan app phu thuoc vao
    // va o muc App Level => toan app su dung dependency trong Container nay.

    val marsPhotosRepository: MarsPhotosRepository

    // thuoc tinh nay la kho du lieu cua app.

    // marsPhotosRepository la dependency cua ViewModel can dung de luu va ket noi
    // network lay du lieu ve.
    // ViewModel phu thuoc vao marsPhotosRepository
    // a single data source, which is the network API call:
    // MarsApi.retrofitService.getPhotos() trong class NetworkMarsPhotosRepository()
    // trong interface MarsPhotosRepository
    // The data is exposed to the app through the repository class,
    // which abstracts away the source of the data
}

class DefaultAppContainer : AppContainer {
    // Vi ViewModel phu thuoc vao 1 repository tao loi goi network,
    // nen dung 1 container de dat cac thanh phan ma ViewModel phu thuoc vao do

    // Cac tham so cua thanh phan ViewModel phu thuoc vao la repository, dung
    // de ket noi den network api lay anh sao Hoa ve tu mang, duoc dua vao
    // container chua repository nay luon, vi repository can cac tham so nay
    // Mac dinh la repository lay data tu network that ve
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