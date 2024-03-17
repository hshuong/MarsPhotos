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
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    //data class Success(val photos: String) : MarsUiState
    // In order to store the data, add a constructor parameter to the Success data class
    object Error : MarsUiState
    object Loading : MarsUiState
}
class MarsViewModel : ViewModel() {
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
                val listResult = MarsApi.retrofitService.getPhotos()
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
}
