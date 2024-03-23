package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import com.example.marsphotos.network.MarsPhoto

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}
// Repository la kho chua du lieu, app lay du lieu chi co 1 cach la vao kho du lieu de lay ve
// kho co nhieu nguon cung cap du lieu dua vao kho nay.
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
    // co the dung cac api khac nhau khong chi la retrofit de lay data tu network
): MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> =  marsApiService.getPhotos()
}