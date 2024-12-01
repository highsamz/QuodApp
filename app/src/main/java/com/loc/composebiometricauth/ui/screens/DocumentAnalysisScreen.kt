import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File

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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Documentoscopia: Validação de Documentos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        if (frontPhotoBitmap != null) {
            androidx.compose.foundation.Image(
                bitmap = frontPhotoBitmap!!.asImageBitmap(),
                contentDescription = "Foto da Frente",
                modifier = Modifier.size(200.dp)
            )
        }
        Button(onClick = { launcherFront.launch() }) {
            Text(text = "Capturar Frente")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (backPhotoBitmap != null) {
            androidx.compose.foundation.Image(
                bitmap = backPhotoBitmap!!.asImageBitmap(),
                contentDescription = "Foto do Verso",
                modifier = Modifier.size(200.dp)
            )
        }
        Button(onClick = { launcherBack.launch() }) {
            Text(text = "Capturar Verso")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (frontPhotoBitmap != null && backPhotoBitmap != null) {
                Toast.makeText(context, "Documento capturado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Por favor, capture frente e verso.", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Validar Documento")
        }
    }
}
