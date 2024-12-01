package com.loc.composebiometricauth.ui.score

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType

fun calculateFraudScore(cpf: String): Int {
    val onlyDigits = cpf.replace("[^\\d]".toRegex(), "")

    if (onlyDigits.length != 11) return 0
    var sum = 0
    for (i in onlyDigits.indices) {
        sum += onlyDigits[i].toString().toInt() * (i + 1)
    }

    return sum % 1000
}


@Composable
fun ScoreAntifraudeForm(onSubmit: (String) -> Unit) {
    var cpf by remember { mutableStateOf(TextFieldValue("")) }
    var isValidCpf by remember { mutableStateOf(false) }
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
        val formattedCpf = formatCpf(text.text)
        val annotatedCpf = buildAnnotatedString {
            append(formattedCpf)
        }
        TransformedText(annotatedCpf, OffsetMapping.Identity)
    }

    fun isCpfValid(cpfText: String): Boolean {
        val onlyDigits = cpfText.replace("[^\\d]".toRegex(), "")
        if (onlyDigits.length != 11) return false

        var sum1 = 0
        var sum2 = 0
        var digit1: Int
        var digit2: Int

        for (i in 0..8) sum1 += (onlyDigits[i].toString().toInt() * (10 - i))
        digit1 = (sum1 * 10) % 11
        if (digit1 == 10 || digit1 == 11) digit1 = 0

        for (i in 0..8) sum2 += (onlyDigits[i].toString().toInt() * (11 - i))
        sum2 += digit1 * 2
        digit2 = (sum2 * 10) % 11
        if (digit2 == 10 || digit2 == 11) digit2 = 0

        return onlyDigits[9].toString().toInt() == digit1 && onlyDigits[10].toString().toInt() == digit2
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
                    onSubmit(cpf.text) // Chama a função de submit
                    score = "Score Calculado: ${calculateFraudScore(cpf.text)}"
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
