package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer

class MarsPhotosApplication : Application() {
    // class MarsPhotosApplication nay co 1 thuoc tinh container tro den DefaultAppContainer,
    // container do co thuoc tinh marsPhotosRepository la repository la data
    // container la bien de truy cap den lay DefaultContainer chua cac thanh phan
    // ma Application nay phu thuoc vao, la marsPhotosRepository (kieu NetworkMarsPhotosRepository
    // trong interface MarsPhotosRepository)
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // duoc khoi tao trong ham nay, ko khoi tao o constructor cua class
        container = DefaultAppContainer()
    }
    // Nho update the Android manifest so the app uses the application class: MarsPhotosApplication nay
    //     <application
    //        android:name=".MarsPhotosApplication"
}