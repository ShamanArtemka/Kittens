package com.example.first_app

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _selectedPhotoIndex =  mutableStateOf(R.drawable.kat4)
    val selectedPhotoIndex: State<Int> = _selectedPhotoIndex

    fun updateSelectedPhotoIndex(newIndex: Int) {
        _selectedPhotoIndex.value = newIndex
    }
}


