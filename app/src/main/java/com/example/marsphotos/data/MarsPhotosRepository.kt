package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.example.marsphotos.network.MarsPhoto

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

// Best practices require the app to have a repository for each type of
// data source your app uses.
// Type anh Mars lay tu network ve
// the app has one data source MarsPhoto tu network, so it has one repository
// the repository that retrieves data from the internet completes
// the data source's responsibilities.
// The data is exposed to the app through the repository class,
// which abstracts away the source of the data
class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
): MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> =  marsApiService.getPhotos()
}