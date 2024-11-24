package com.example.shopper.ui.feature.account.register

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
import com.example.shopper.ui.feature.account.login.LoginState
import com.example.shopper.ui.feature.account.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = koinViewModel()) {
    val loginState = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = loginState.value) {
            is RegisterState.Error -> {
                Text(text = state.message)
            }

            RegisterState.Idle -> {
                RegisterContent(onSingInClicked = { email, password, name ->
                    viewModel.register(email = email, password = password, name = name)
                }, onSignInClick = {
                    navController.popBackStack()
                })
            }

            RegisterState.Loading -> {
                CircularProgressIndicator()
                Text(text = stringResource(R.string.loading))
            }

            RegisterState.Success -> {
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
fun RegisterContent(
    onSingInClicked: (String, String, String) -> Unit,
    onSignInClick: () -> Unit
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val name = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.register), style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = {
                Text(text = "Name")
            }
        )
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = {
                Text(text = "Email")
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
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                onSingInClicked(email.value, password.value, name.value)
            }, modifier = Modifier.fillMaxWidth(),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty()
        ) {
            Text(text = "Login")
        }
        Text(text = "Don't have an account? Register", modifier = Modifier
            .padding(8.dp)
            .clickable {
                onSignInClick()
            })
    }
}

@Composable
@Preview(showBackground = true)
fun PrivateRegisterScreen(modifier: Modifier = Modifier) {
    RegisterContent(onSingInClicked = { email, password, name -> }, onSignInClick = {})
}