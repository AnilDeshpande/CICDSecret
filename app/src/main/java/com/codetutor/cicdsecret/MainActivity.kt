package com.codetutor.cicdsecret

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codetutor.cicdsecret.ui.theme.CICDSecretTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CICDSecretTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SecretStatusScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SecretStatusScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Application ID",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Text(
            text = BuildConfig.APPLICATION_ID,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Secret Status",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        Text(
            text = "❌ NOT CONFIGURED",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Red
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecretStatusScreenPreview() {
    CICDSecretTheme {
        SecretStatusScreen()
    }
}