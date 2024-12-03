package com.loc.composebiometricauth.ui.screens
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loc.composebiometricauth.ui.theme.QuodGray
import com.loc.composebiometricauth.ui.theme.QuodPurple

@Composable
fun DocumentAnalysisScreen() {

    val context = LocalContext.current

    var frontPhotoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var backPhotoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcherFront = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            frontPhotoBitmap = bitmap
        } else {
            Toast.makeText(context, "Erro ao capturar foto da frente.", Toast.LENGTH_SHORT).show()
        }
    }

    val launcherBack = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            backPhotoBitmap = bitmap
        } else {
            Toast.makeText(context, "Erro ao capturar foto do verso.", Toast.LENGTH_SHORT).show()
        }
    }

    Surface( //TODO Retirar esse Surface depois, só serve para o preview
        modifier = Modifier.fillMaxSize(),
        color = QuodGray
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 70.dp,
                    horizontal = 10.dp
        ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Documentoscopia",
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(70.dp))

            if (frontPhotoBitmap != null) {
                androidx.compose.foundation.Image(
                    bitmap = frontPhotoBitmap!!.asImageBitmap(),
                    contentDescription = "Foto da Frente",
                    modifier = Modifier.size(200.dp)
                )
            }
            Button(onClick = { launcherFront.launch() },
                modifier = Modifier
                    //.fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 2.dp,
                        color = QuodPurple,
                        shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(QuodGray),
                shape = RoundedCornerShape(8.dp),

            ) {
                Text(text = "Capturar Frente",
                    color = QuodPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (backPhotoBitmap != null) {
                androidx.compose.foundation.Image(
                    bitmap = backPhotoBitmap!!.asImageBitmap(),
                    contentDescription = "Foto do Verso",
                    modifier = Modifier.size(200.dp)
                )
            }

            Button(onClick = { launcherBack.launch() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 2.dp,
                        color = QuodPurple,
                        shape = RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(QuodGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Capturar Verso",
                    color = QuodPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (frontPhotoBitmap != null && backPhotoBitmap != null) {
                    Toast.makeText(context, "Documento capturado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Por favor, capture frente e verso.", Toast.LENGTH_SHORT).show()
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(QuodPurple),
                shape = RoundedCornerShape(8.dp),

            ) {
                Text(text = "Validar Documento",
                    color = QuodGray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDocument (){
    DocumentAnalysisScreen()
}