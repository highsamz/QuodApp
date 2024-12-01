package com.loc.composebiometricauth.ui.screens

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DocumentAnalysisViewModel : ViewModel() {
    var frontImage by mutableStateOf<Bitmap?>(null)
    var backImage by mutableStateOf<Bitmap?>(null)
    var validationMessage by mutableStateOf("")

    fun setImage(front: Boolean, bitmap: Bitmap) {
        if (front) {
            frontImage = bitmap
        } else {
            backImage = bitmap
        }
    }

    fun validateDocument() {
        if (frontImage != null && backImage != null) {
            validationMessage = "Documento capturado com sucesso!"
        } else {
            validationMessage = "Por favor, capture as duas imagens do documento."
        }
    }
}
