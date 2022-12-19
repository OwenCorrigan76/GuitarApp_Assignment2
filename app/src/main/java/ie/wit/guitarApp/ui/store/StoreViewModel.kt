package ie.wit.guitarApp.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoreViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Store Fragment"
    }
    val text: LiveData<String> = _text
}