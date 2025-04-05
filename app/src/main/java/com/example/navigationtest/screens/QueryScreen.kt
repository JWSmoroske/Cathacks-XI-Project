package com.example.navigationtest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navigationtest.QueryViewModel
import com.example.navigationtest.R
import com.example.navigationtest.UiState
import parseBasicMarkdown

@Composable
fun QueryScreen(
    queryViewModel: QueryViewModel = viewModel()
) {
    val uiState by queryViewModel.uiState.collectAsState()
    val promptContext = stringResource(R.string.prompt_context)

    var prompt by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val isLoading = uiState is UiState.Loading


    when (uiState) {
        is UiState.Loading -> {
            // Optionally clear or show partial message
        }
        is UiState.Success -> {
            result = (uiState as UiState.Success).outputText
        }
        is UiState.Error -> {
            result = (uiState as UiState.Error).errorMessage
        }
        else -> { /* no-op */ }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (prompt.isEmpty()) {
                Text(
                    text = stringResource(R.string.placeholder_prompt),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp, top = 12.dp)
                )
            }

            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                //label = { Text(stringResource(R.string.label_prompt)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val completePrompt = promptContext + prompt
                queryViewModel.sendPrompt(completePrompt)
            },
            enabled = prompt.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.action_go))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show progress indicator if loading
        if (isLoading) {
            CircularProgressIndicator()
        }

        // Show the result using your custom parser
        if (result.isNotEmpty() && !isLoading) {
            val textColor = if (uiState is UiState.Error) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                // Parse triple/double/single-asterisk markup
                val annotatedResult = parseBasicMarkdown(result)

                Text(
                    text = annotatedResult,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun QueryScreenPreview() {
    QueryScreen()
}
