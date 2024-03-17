package com.example.marsphotos.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Serialization is the process of converting data used by
// an application to a format that can be transferred over a network.

// As opposed to serialization, Deserialization is the process
// of reading data from an external source (like a server)
// and converting it into a runtime object.

// kotlinx.serialization parses this JSON data and converts it into Kotlin objects.
// To do this, kotlinx.serialization needs to have a Kotlin data class to store the parsed results
// MarsPhoto la doi tuong kotlin, la ket qua chuyen doi tu JSON sang doi tuong kotlin
// tu khoa @Serializable nghia la ket qua convert tu JSON sang object
// When kotlinx serialization parses the JSON,
// it matches the keys by name and fills the data objects with appropriate values.
@Serializable
data class MarsPhoto(
    val id: String,
    // the name of the variable in the data class is imgSrc.
    // The variable can be mapped to the JSON attribute img_src
    // using @SerialName(value = "img_src")
    @SerialName(value = "img_src")
    val imgSrc: String
)