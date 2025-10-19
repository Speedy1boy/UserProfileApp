package com.example.userprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.userprofileapp.ui.theme.UserProfileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserProfileAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    UserForm()
                }
            }
        }
    }
}

@Composable
fun UserForm() {
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableFloatStateOf(18f) }
    var gender by rememberSaveable { mutableStateOf("male") }
    var subscribed by rememberSaveable { mutableStateOf(false) }
    var showSummary by rememberSaveable { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = false
            },
            label = { Text(stringResource(R.string.enter_name)) },
            isError = nameError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (nameError) {
            Text(
                text = stringResource(R.string.error_name),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(stringResource(R.string.age) + ": ${age.toInt()}")
        Slider(
            value = age,
            onValueChange = { age = it },
            valueRange = 1f..100f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(stringResource(R.string.gender))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = gender == "male",
                onClick = { gender = "male" }
            )
            Text(stringResource(R.string.male))
            Spacer(modifier = Modifier.width(12.dp))
            RadioButton(
                selected = gender == "female",
                onClick = { gender = "female" }
            )
            Text(stringResource(R.string.female))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = subscribed,
                onCheckedChange = { subscribed = it }
            )
            Text(stringResource(R.string.subscribe))
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (name.isBlank()) {
                    nameError = true
                    showSummary = false
                } else {
                    showSummary = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.send))
        }

        if (showSummary) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(stringResource(R.string.summary_title), style = MaterialTheme.typography.titleMedium)
            Text("Имя: $name")
            Text("Возраст: ${age.toInt()}")
            Text("Пол: ${if (gender == "male") "Мужской" else "Женский"}")
            Text("Подписка: ${if (subscribed) "да" else "нет"}")
        }
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewUserForm() {
    UserProfileAppTheme {
        UserForm()
    }
}
