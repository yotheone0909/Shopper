package com.example.shopper.ui.feature.account.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shopper.R
import com.example.shopper.navigation.HomeScreen
import com.example.shopper.navigation.RegisterScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {
    val loginState = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = loginState.value) {
            is LoginState.Error -> {
                Text(text = state.message)
            }

            LoginState.Idle -> {
                LoginContent(onSingInClicked = { email, password ->
                    viewModel.login(email, password)
                }, onRegisterClick = {
                    navController.navigate(RegisterScreen)
                })
            }

            LoginState.Loading -> {
                CircularProgressIndicator()
                Text(text = stringResource(R.string.loading))
            }

            LoginState.Success -> {
                LaunchedEffect(loginState.value) {
                    navController.navigate(HomeScreen) {
                        popUpTo(HomeScreen) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun LoginContent(onSingInClicked: (String, String) -> Unit, onRegisterClick: () -> Unit) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = {
                Text(text = stringResource(R.string.email))
            }
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            label = {
                Text(text = stringResource(R.string.email))
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                onSingInClicked(email.value, password.value)
            }, modifier = Modifier.fillMaxWidth(),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty()
        ) {
            Text(text = stringResource(R.string.login))
        }
        Text(text = stringResource(R.string.already_have_an_account), modifier = Modifier
            .padding(8.dp)
            .clickable {
                onRegisterClick()
            })
    }
}

@Composable
@Preview(showBackground = true)
fun PrivateLoginScreen(modifier: Modifier = Modifier) {
    LoginContent(onSingInClicked = { email, password -> }, onRegisterClick = {})
}