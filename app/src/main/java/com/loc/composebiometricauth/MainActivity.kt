package com.loc.composebiometricauth

import DocumentAnalysisScreen
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loc.composebiometricauth.ui.screens.CadastralAuthenticationScreen
import com.loc.composebiometricauth.ui.screens.ScoreAntifraudeForm
import com.loc.composebiometricauth.ui.screens.SimValidationScreen
import com.loc.composebiometricauth.ui.theme.ComposeBiometricAuthTheme

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkCameraPermission()

        val biometricAuthenticator = BiometricAuthenticator(this)

        setContent {
            ComposeBiometricAuthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigator(biometricAuthenticator)
                }
            }
        }
    }

    private fun checkCameraPermission() {
        val permission = Manifest.permission.CAMERA
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), CAMERA_PERMISSION_CODE)
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }
}

@Composable
fun AppNavigator(biometricAuthenticator: BiometricAuthenticator) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController, biometricAuthenticator) }
        composable("document_analysis") { DocumentAnalysisScreen() }
        composable("sim_swap") { SimValidationScreen() }
        composable("cadastral_authentication") { CadastralAuthenticationScreen() }
        composable("fraud_score") { ScoreAntifraudeForm {} }
    }
}

@Composable
fun MainScreen(navController: NavController, biometricAuthenticator: BiometricAuthenticator) {
    var biometricMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("document_analysis") }) {
            Text(text = "Análise de Documentos")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate("sim_swap") }) {
            Text(text = "SIM SWAP")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate("cadastral_authentication") }) {
            Text(text = "Autenticação Cadastral")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigate("fraud_score") }) {
            Text(text = "Score Antifraude")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                biometricAuthenticator.promptBiometricAuth(
                    title = "Login Biométrico",
                    subTitle = "Use sua impressão digital para autenticar",
                    negativeButtonText = "Cancelar",
                    fragmentActivity = navController.context as FragmentActivity,
                    onSuccess = {
                        biometricMessage = "Autenticação bem-sucedida!"
                    },
                    onError = { _, errorString ->
                        biometricMessage = "Erro: $errorString"
                    },
                    onFailed = {
                        biometricMessage = "Autenticação falhou."
                    }
                )
            }
        ) {
            Text(text = "Autenticar com Biometria")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = biometricMessage)
    }
}