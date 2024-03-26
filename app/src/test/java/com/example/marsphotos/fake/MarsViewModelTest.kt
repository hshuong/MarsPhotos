package com.example.marsphotos.fake

import com.example.marsphotos.rules.TestDispatcherRule
import com.example.marsphotos.ui.screens.MarsUiState
import com.example.marsphotos.ui.screens.MarsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MarsViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    // test for the getMarsPhotos() function from the MarsViewModel
    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() =
        runTest{
            // create an instance of the MarsViewModel and pass it an instance of
            // the fake repository
            val marsViewModel = MarsViewModel(
                // fake class that inherits from the MarsPhotosRepository interface and
                // overrides the getMarsPhotos() function to return fake data.
                marsPhotosRepository = FakeNetworkMarsPhotosRepository()
            )
            // Assert that the marsUiState of your ViewModel instance matches
            // the result of a successful call to MarsPhotosRepository.getMarsPhotos()
            assertEquals(
                MarsUiState.Success("Success: ${FakeDataSource.photosList.size} Mars " +
                        "photos retrieved"),
                marsViewModel.marsUiState
            )
        }
}