package com.example.marsphotos.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val retrofit = Retrofit.Builder() // Dung bo tao doi tuong Retrofit
    //.addConverterFactory(ScalarsConverterFactory.create()) // convert JSON to String

    // use the kotlinx.serialization converter to convert the JSON object to Kotlin objects
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL) // ket noi den web service nao
    .build() // Tao doi tuong Retrofit

// Interface defines how Retrofit talks to the web server using HTTP requests.
interface MarsApiService { //how Retrofit talks to the web server using HTTP requests.
    //Use the @GET annotation to tell Retrofit that this is a GET request
    // and specify an endpoint for that web service method.
    // In this case, the endpoint is photos.
    // The following URL gets a list of Mars photos:
    //https://android-kotlin-fun-mars-server.appspot.com/photos
    @GET("photos")
    // a suspend function to make it asynchronous and not block the calling thread.
    suspend fun getPhotos(): List<MarsPhoto>//String // to get the response string from the web service.
    // When the getPhotos() method is invoked, Retrofit appends the endpoint photos
    // to the base URL—which you defined in the Retrofit builder—used to
    // start the request.
}

//Outside the MarsApiService interface declaration, define a public object
// called MarsApi to initialize the Retrofit service.
// This object is the public singleton object that the rest of
// the app can access.
object MarsApi {
    // define a public object called MarsApi to initialize
    // the Retrofit service.
    // You make this lazy initialization to make sure it is initialized
    // at its first usage.
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
        // Initialize the retrofitService variable using
        // the retrofit.create() method with the MarsApiService interface.
    }
    // Each time your app calls MarsApi.retrofitService,
    // the caller accesses the same singleton Retrofit object
    // that implements MarsApiService, which is created on the first access.
}
