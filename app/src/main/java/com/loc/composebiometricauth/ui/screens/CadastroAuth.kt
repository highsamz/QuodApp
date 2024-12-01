package com.loc.composebiometricauth.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CadastralAuthenticationScreen() {

    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }


    var cpfError by remember { mutableStateOf(false) }
    var telefoneError by remember { mutableStateOf(false) }
    var nomeError by remember { mutableStateOf(false) }
    var enderecoError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Autenticação Cadastral",
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            value = nome,
            onValueChange = {
                nome = it
                nomeError = it.isEmpty()
            },
            label = { Text("Nome Completo") },
            isError = nomeError,
            modifier = Modifier.fillMaxWidth()
        )
        if (nomeError) {
            Text("O nome é obrigatório", color = MaterialTheme.colorScheme.error)
        }

        TextField(
            value = cpf,
            onValueChange = {
                cpf = it.replace(Regex("[^\\d]"), "")
                cpfError = cpf.length != 11
            },
            label = { Text("CPF") },
            isError = cpfError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        if (cpfError) {
            Text("CPF inválido", color = MaterialTheme.colorScheme.error)
        }

        TextField(
            value = endereco,
            onValueChange = {
                endereco = it
                enderecoError = it.isEmpty()
            },
            label = { Text("Endereço") },
            isError = enderecoError,
            modifier = Modifier.fillMaxWidth()
        )
        if (enderecoError) {
            Text("O endereço é obrigatório", color = MaterialTheme.colorScheme.error)
        }

        TextField(
            value = telefone,
            onValueChange = {
                telefone = it.replace(Regex("[^\\d]"), "")
                telefoneError = telefone.length != 11
            },
            label = { Text("Telefone Celular") },
            isError = telefoneError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        if (telefoneError) {
            Text("Telefone inválido", color = MaterialTheme.colorScheme.error)
        }
        Button(
            onClick = {
                if (!cpfError && !telefoneError && !nomeError && !enderecoError && null == false) {
                    Toast.makeText(context, "Dados validados com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Preencha corretamente todos os campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}
