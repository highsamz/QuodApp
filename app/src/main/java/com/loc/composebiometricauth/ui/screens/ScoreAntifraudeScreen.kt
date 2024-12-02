package com.loc.composebiometricauth.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun ScoreAntifraudeForm(onSubmit: (String) -> Unit) {
    var cpf by remember { mutableStateOf(TextFieldValue("")) }
    var isValidCpf by remember { mutableStateOf(true) }
    var score by remember { mutableStateOf<String?>(null) }

    fun formatCpf(cpfText: String): String {
        val onlyDigits = cpfText.replace("[^\\d]".toRegex(), "")
        return when {
            onlyDigits.length in 1..3 -> onlyDigits
            onlyDigits.length in 4..6 -> "${onlyDigits.substring(0, 3)}.${onlyDigits.substring(3)}"
            onlyDigits.length in 7..9 -> "${onlyDigits.substring(0, 3)}.${onlyDigits.substring(3, 6)}.${onlyDigits.substring(6)}"
            onlyDigits.length in 10..11 -> "${onlyDigits.substring(0, 3)}.${onlyDigits.substring(3, 6)}.${onlyDigits.substring(6, 9)}-${onlyDigits.substring(9)}"
            else -> onlyDigits
        }
    }

    val cpfVisualTransformation = VisualTransformation { text ->
        val originalText = text.text
        val formattedText = formatCpf(originalText)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = offset
                if (offset > 3) transformedOffset += 1
                if (offset > 6) transformedOffset += 1
                if (offset > 9) transformedOffset += 1
                return transformedOffset.coerceAtMost(formattedText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = offset
                if (offset > 3) originalOffset -= 1
                if (offset > 7) originalOffset -= 1
                if (offset > 11) originalOffset -= 1
                return originalOffset.coerceAtMost(originalText.length)
            }
        }

        TransformedText(
            text = buildAnnotatedString { append(formattedText) },
            offsetMapping = offsetMapping
        )
    }

    fun isCpfValid(cpfText: String): Boolean {
        val onlyDigits = cpfText.replace("[^\\d]".toRegex(), "")
        return onlyDigits.length == 11
    }

    LaunchedEffect(cpf.text) {
        isValidCpf = isCpfValid(cpf.text)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            isError = !isValidCpf,
            visualTransformation = cpfVisualTransformation,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isValidCpf) {
                    onSubmit(cpf.text)
                    score = "Score Calculado: ${Random.nextInt(100, 1000)}"
                }
            },
            enabled = isValidCpf
        ) {
            Text("Enviar")
        }

        if (!isValidCpf) {
            Text(
                text = "CPF inválido",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        score?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
