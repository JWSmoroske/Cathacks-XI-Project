package com.example.navigationtest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navigationtest.LLMViewModel

// Data class for quiz questions including an explanation for the answer
data class QuizQuestion(
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

// Question bank with explanations for why the correct answer is best practice
val securityQuestions = listOf(
    QuizQuestion(
        "What's the recommended minimum length for a secure password?",
        listOf("8 characters", "12 characters", "6 characters", "16 characters"),
        3,
        "A password with 16 characters increases complexity and is much harder to crack using brute-force methods."
    ),
    QuizQuestion(
        "What is the best practice for storing user passwords?",
        listOf("Plain text", "Encrypted", "Hashed with salt", "In a spreadsheet"),
        2,
        "Storing passwords as a hash with a unique salt for each password greatly reduces the risk of exposure even if the data is compromised."
    ),
    QuizQuestion(
        "Which is NOT a good security practice?",
        listOf("Regular updates", "Shared admin accounts", "MFA", "Role-based access"),
        1,
        "Shared admin accounts decrease accountability and can lead to security breaches. Each user should have a unique login."
    ),
    QuizQuestion(
        "What should you do before deploying sensitive data to production?",
        listOf("Email it to the team", "Store in version control", "Encrypt it", "Use default credentials"),
        2,
        "Encrypting sensitive data ensures that even if unauthorized access occurs, the data remains protected."
    )
)

@Composable
fun QuizScreen(llmViewModel: LLMViewModel = viewModel()) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }
    var showFeedback by remember { mutableStateOf(false) }
    var quizCompleted by remember { mutableStateOf(false) }

    val questions = remember { securityQuestions.shuffled() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!quizCompleted) {
            Text(
                text = "Security Best Practices Quiz",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = questions[currentQuestionIndex].question,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display the list of potential answers
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                questions[currentQuestionIndex].answers.forEach { answer ->
                    Button(
                        onClick = { userAnswer = answer },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = answer,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val correctAnswer = questions[currentQuestionIndex].answers[
                        questions[currentQuestionIndex].correctAnswerIndex
                    ]
                    showFeedback = true
                    if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
                        score++
                    }
                },
                enabled = userAnswer.isNotBlank() && !showFeedback
            ) {
                Text("Submit Answer")
            }

            if (showFeedback) {
                val correctAnswer = questions[currentQuestionIndex].answers[
                    questions[currentQuestionIndex].correctAnswerIndex
                ]
                if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
                    Text(
                        text = "✅ Correct! Well done!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Green,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Text(
                        text = "❌ Incorrect. The correct answer was: $correctAnswer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "Tip: ${questions[currentQuestionIndex].explanation}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Button(
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            userAnswer = ""
                            showFeedback = false
                        } else {
                            quizCompleted = true
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Next Question")
                }
            }
        } else {
            Text(
                text = "Quiz Complete!\nScore: $score/${questions.size}",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    currentQuestionIndex = 0
                    score = 0
                    userAnswer = ""
                    showFeedback = false
                    quizCompleted = false
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Restart Quiz")
            }
        }
    }
}
