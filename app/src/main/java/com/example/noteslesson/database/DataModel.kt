package com.example.noteslesson.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noteslesson.recycler.Item

class DataModel : ViewModel() {
    val createdItem : MutableLiveData<Item> by lazy {
        MutableLiveData<Item>()
    }
}