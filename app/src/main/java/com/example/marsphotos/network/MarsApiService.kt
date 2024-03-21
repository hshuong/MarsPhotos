package com.example.marsphotos.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET



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
