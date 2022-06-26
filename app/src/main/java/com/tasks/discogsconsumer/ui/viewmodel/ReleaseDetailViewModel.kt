package com.tasks.discogsconsumer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.domain.usecase.GetReleaseDetailUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReleaseDetailViewModel @Inject constructor(
    private val getReleaseDetailUseCase: GetReleaseDetailUseCase
    ): ViewModel() {
    val release = MutableLiveData<Release?>(null)


    fun getRelease(id: Int): LiveData<Release?> {
        if (release.value == null) {
            viewModelScope.launch {
                release.postValue(getReleaseDetailUseCase.getRelease(id))
            }
        }
        return release
    }

    fun cleanData() {
        release.postValue(null)
    }
}