package com.loc.composebiometricauth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.loc.composebiometricauth.ui.screens.CadastralAuthenticationScreen
import com.loc.composebiometricauth.ui.theme.ComposeBiometricAuthTheme
import com.loc.composebiometricauth.ui.screens.ScoreAntifraudeForm
import com.loc.composebiometricauth.ui.screens.SimValidationScreen
import com.loc.composebiometricauth.ui.screens.SplashScreen
import com.loc.composebiometricauth.ui.theme.BackColor
import com.loc.composebiometricauth.ui.theme.TxtColor

class MainActivity : FragmentActivity() {
    private var showSplashScreen by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val biometricAuthenticator = BiometricAuthenticator(this)

        setContent {
            ComposeBiometricAuthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackColor
                ) {
                    if (showSplashScreen){
                        SplashScreen (onTimeout = {
                            showSplashScreen = false
                        })
                    } else {
                        AppNavigator(biometricAuthenticator)
                    }
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
        composable("sim_swap") { SimValidationScreen() }
        composable("cadastral_authentication") { CadastralAuthenticationScreen() }
        composable("fraud_score") { ScoreAntifraudeForm {} }
    }
}

@Composable
fun MainScreen(navController: NavController, biometricAuthenticator: BiometricAuthenticator) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackColor
    ){
        var biometricMessage by remember { mutableStateOf("") }

        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 70.dp,
                    horizontal = 25.dp
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate("document_analysis") },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp))
                ,colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Text(text = "Análise de Documentos",
                        color = TxtColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("sim_swap") },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp))
                ,colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Text(
                        text = "SIM SWAP",
                        color = TxtColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("cadastral_authentication")  },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp))
                ,colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(text = "Autenticação Cadastral",
                        color = TxtColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center)

                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate("fraud_score") },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp))
                ,colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Text(text = "Score Antifraude",
                        color = TxtColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center)
                }

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
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
                    .height(55.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp),
                        spotColor = Color.Black
                    )
                    .clip(RoundedCornerShape(8.dp))
                ,colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                )
                {
                    Text(text = "Autenticar com Biometria",
                        color = TxtColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center)
                }

            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = biometricMessage)
        }
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

/*
@Preview (showBackground = true)
@Composable
fun PrevieMainScreen (){
    MainScreen()
}
*/

