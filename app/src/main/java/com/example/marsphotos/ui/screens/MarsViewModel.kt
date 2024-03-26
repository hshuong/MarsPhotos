/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication
import com.example.marsphotos.data.MarsPhotosRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    //data class Success(val photos: String) : MarsUiState
    // In order to store the data, add a constructor parameter to the Success data class
    object Error : MarsUiState
    object Loading : MarsUiState
}
class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {

    // The ViewModel communicates with the data layer,
    // and the rest of the app is transparent to this implementation.

    // The MarsViewModel is responsible for making the network call to
    // get the Mars photos data. In the ViewModel, you use MutableState
    // to update the app UI when the data changes

    // Quy trinh:

    // 1) O Manifest khai bao app se dung MarsPhotosApplication de chay
    // Ham onCreate cua class MarsPhotosApplication co khoi tao thuoc tinh container

    // 2) Container co thuoc tinh marsPhotosRepository kieu NetworkMarsPhotosRepository
    // Co cac tham so de tao ra doi tuong retrofit o day. Nhung doi tuong retrofit
    // thuc hien chuc nang gi thi dinh nghia o MarsApiService o 5 o duoi

    // 3) thuoc tinh marsPhotosRepository dung retrofit de lay anh photo tu network
    // dua vao 1 List<MarsPhoto>

    // 4) NetworkMarsPhotosRepository co thuoc tinh marsApiService kieu MarsApiService

    // 5) MarsApiService dung @GET cua retrofit la data ve
    // Neu them cac chuc nang khac cua retrofit thi them vao day: POST, PUT, DELETE
    // retrofit vua convert cac string json sang doi tuong cua Kotlin (MarsPhoto data class)
    // Trong dinh nghia doi tuong retrofit co tham so:
    // .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    // 5.2) Them buoc tai image va hien thi:
    // The image has to be downloaded, internally stored(cached),
    // and decoded from its compressed format to an image that Android can use

    // 6) ViewModel can den repository de lay du lieu. Repository duoc
    // truyen vao ham constructor cua ViewModel duoi dang 1 tham so ten la marsPhotosRepository

    // 7) Tham so cua constructor cua MarsViewModel la marsPhotosRepository duoc tao tu ben ngoai
    // MarsViewModel theo Dependency Injection. De code linh hoat de test

    // 8) Vi constructor ViewModel ko duoc co tham so ma MarsViewModel can tham so nen
    // phai dung companion object dat ngay trong class MarsViewModel

    // 9) companion object dung co che Factory de tao ra tham so repository cho constructor
    // MarsViewModel.

    // 10) companion object can lay Container (container chua thuoc tinh marsPhotosRepository
    // ben trong, do chuc nang cua container la chua cac dependency cua app)
    // tu Application duoc dinh nghia rieng la MarsPhotosApplication.
    // companion object dung container cua MarsPhotosApplication de tao ra
    // marsPhotosRepository, truyen marsPhotosRepository vao ham dung cua MarsViewModel
    // de MarsViewModel lay duoc data tu repository

    // ViewModel phu thuoc vao marsPhotosRepository de lay data
    // Can truyen dependency marsPhotosRepository vao constructor cua MarsViewModel
    // marsPhotosRepository la dependency cung cap boi DefaultAppContainer
    // (AppContainer) A container is an object that contains the dependencies that the app requires.

    // Because the Android framework does not allow a ViewModel to be passed values
    // in the constructor when created, we implement a ViewModelProvider.Factory object,
    // which lets us get around this limitation.

    // The MarsViewModel.Factory object uses the application container to retrieve
    // the marsPhotosRepository, and then passes this repository to the ViewModel
    // when the ViewModel object is created
    // => Xem o dinh nghia companion object o cuoi class nay


    // marsPhotosRepository ket noi network va lay du lieu ve.
    // MarsApi.retrofitService.getPhotos() trong class NetworkMarsPhotosRepository()
    // trong interface MarsPhotosRepository

    /** The mutable State that stores the status of the most recent request */
    // ban dau marsUiState co kieu String cho don gian, hien thi response string ve hoac string loi
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set
    // da thay doi kieu cua marsUiState tu String sang MarsUiState
    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    // A viewModelScope is the built-in coroutine scope defined
    // for each ViewModel in your app. Any coroutine launched
    // in this scope is automatically canceled if the ViewModel is cleared.

    // You can use viewModelScope to launch the coroutine and make
    // the web service request in the background.
    // Since the viewModelScope belongs to the ViewModel,
    // the request continues even if the app goes through
    // a configuration change.
    private fun getMarsPhotos() {
        viewModelScope.launch {
//            try {
//                val listResult = MarsApi.retrofitService.getPhotos()
//                // vi viec lay data tu network co the bi tre nen phai dung
//                // coroutine de khong lam block ham Main
//                // Use viewModelScope to launch the coroutine and make
//                // the web service request in the background.
//                // goc la String, doi sang MarsUiState
//                marsUiState = MarsUiState.Success(listResult)
//            } catch (e: IOException) {
//                marsUiState = MarsUiState.Error
//            }
            // You can lift the marsUiState assignment out of the try-catch block.
            marsUiState = try {
                // val marsPhotosRepository = NetworkMarsPhotosRepository()
                // o version data layer moi nay, viewModel ko goi network truc tiep, ma dung Repository

                // Instead of the ViewModel directly making the network request for the data,
                // the repository provides the data. The ViewModel no longer directly
                // references the MarsApi code.

                // Before
                // val listResult = MarsApi.retrofitService.getPhotos()

                // Now
                // marsPhotosRepository la dependency cung cap boi DefaultAppContainer
                // (AppContainer)
                val listResult = marsPhotosRepository.getMarsPhotos()

                // Tuy nhien, van can test lay du lieu bang network gia lap nen can dung
                // cach truyen repository tu ngoai vao, de thay doi linh hoat network that va gia lap
                // o duoi, version old la viewModel goi truc tiep network

                // Tao instance cua sealed interface MarsUiState va su dung no: gan marsUiState cho
                // no. Cau truc tao instance MarsUiState.Success(thamso) or
                // MarsUiState.Error or MarsUiStat.Loading (2 cai sau ko co tham so nhu Success)
                //MarsUiState.Success(listResult)
                MarsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
                // The final expression is the value that will be returned after a lambda is executed
            } catch (e: IOException) {
                MarsUiState.Error
            }
        } // launch
    }

    // A companion object helps us by having a single instance of an object
    // that is used by everyone without needing to create a new instance
    // of an expensive object. This is an implementation detail,
    // and separating it lets us make changes without impacting other parts
    // of the app's code.

    // Dung mot companion object de thanh phan nao khac trong app cung truy cap
    // duoc no ma khong phai tao them cac doi tuong moi

    // companion object giong nhu mot bien static cua java, ham main goi no
    // bang ten class.ten doi tuong companion. Cac ham bat ky trong app goi den
    // no ma khong can tao ra no

    companion object {
        // Because the Android framework does not allow a ViewModel to be passed values
        // in the constructor when created, we implement a ViewModelProvider.Factory object,
        // which lets us get around this limitation.

        // The MarsViewModel.Factory object uses the application container to retrieve
        // the marsPhotosRepository, and then passes this repository to the ViewModel
        // when the ViewModel object is created.

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            // The MarsViewModel.Factory object uses the application container
            // to retrieve the marsPhotosRepository
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                // APPLICATION_KEY dung de TIM doi tuong MarsPhotosApplication cua app
                // vi MarsPhotosApplication chua thuoc tinh container, container lai
                // dung de lay ve repository, repository la thanh phan ViewModel phu thuoc
                // vao. Repository se duoc pass vao constructor ViewModel
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}
