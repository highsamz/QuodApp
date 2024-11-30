package com.loc.composebiometricauth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loc.composebiometricauth.ui.theme.ComposeBiometricAuthTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}

@Composable
fun AppNavigator(biometricAuthenticator: BiometricAuthenticator) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController, biometricAuthenticator) }
        composable("document_analysis") { DocumentAnalysisScreen() }
        composable("sim_swap") { SimSwapScreen() }
        composable("cadastral_authentication") { CadastralAuthenticationScreen() }
        composable("fraud_score") { FraudScoreScreen() }
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

@Composable
fun DocumentAnalysisScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Documentoscopia: Validação de Documentos",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun SimSwapScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "SIM SWAP: Validação de Troca de Chip",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun CadastralAuthenticationScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Autenticação Cadastral: Formulário",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun FraudScoreScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Score Antifraude: Formulário",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
