package com.example.deuHack.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor():ViewModel(){
    private val mutableBottomNavigationBarState = MutableLiveData(true)
    val bottomBarState:LiveData<Boolean> get() = mutableBottomNavigationBarState

    fun setBottomNavigationState(bool:Boolean) {
        mutableBottomNavigationBarState.value = bool
    }
}