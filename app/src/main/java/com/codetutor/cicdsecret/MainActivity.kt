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
    val apiKey = BuildConfig.DEMO_API_KEY
    val isConfigured = apiKey.isNotBlank()

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
        if (isConfigured) {
            Text(
                text = "✅ Secret configured",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF2E7D32)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Key: ${maskKey(apiKey)}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            Text(
                text = "❌ NOT CONFIGURED",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Red
            )
        }
    }
}

/** Show first 3 and last 3 characters, mask the middle with "…" */
private fun maskKey(key: String): String {
    return if (key.length > 6) {
        "${key.take(3)}…${key.takeLast(3)}"
    } else {
        "***"
    }
}

@Preview(showBackground = true)
@Composable
fun SecretStatusScreenPreview() {
    CICDSecretTheme {
        SecretStatusScreen()
    }
}