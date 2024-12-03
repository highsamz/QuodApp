package com.loc.composebiometricauth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loc.composebiometricauth.R
import kotlinx.coroutines.delay



@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val colinFont = FontFamily(Font(R.font.colin_extralight))

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                bottom = 120.dp
            )
        ){
            Text(text = "QuOD",
                fontSize = 70.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = colinFont,
            )

            Text(text = "Security Check",
                fontSize = 23.sp,
                color = Color.Black,
                fontWeight = FontWeight.Light
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }
}

