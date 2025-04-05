package com.example.navigationtest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class QuizQuestion(
    val question: String,
    val answers: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String = "Security"
)

val securityQuestions = listOf(
    QuizQuestion(
        "What's the recommended minimum length for a secure password?",
        listOf("8 characters", "12 characters", "6 characters", "16 characters"),
        3,
        "16 characters provides significantly better security against brute-force attacks.",
        "Authentication"
    ),
    QuizQuestion(
        "What is the best practice for storing user passwords?",
        listOf("Plain text", "Encrypted", "Hashed with salt", "In a spreadsheet"),
        2,
        "Hashing with salt protects passwords even if the database is compromised.",
        "Data Protection"
    ),
    QuizQuestion(
        "Which is NOT a good security practice?",
        listOf("Regular updates", "Shared admin accounts", "MFA", "Role-based access"),
        1,
        "Shared accounts make auditing impossible. Always use individual credentials.",
        "Access Control"
    ),
    QuizQuestion(
        "What should you do before deploying sensitive data to production?",
        listOf("Email it to the team", "Store in version control", "Encrypt it", "Use default credentials"),
        2,
        "Encryption ensures data protection even if unauthorized access occurs.",
        "Data Handling"
    ),
    QuizQuestion(
        "What's the primary purpose of two-factor authentication (2FA)?",
        listOf("Faster login", "Reduce password complexity", "Prevent phishing", "Add verification layer"),
        3,
        "2FA adds an extra verification step to confirm user identity.",
        "Authentication"
    ),
    QuizQuestion(
        "Which is an ethical way to handle user data under GDPR?",
        listOf("Collect everything", "Store indefinitely", "Minimize data collection", "Sell anonymized data"),
        2,
        "Data minimization collects only what's necessary for the specified purpose.",
        "Ethics"
    ),
    QuizQuestion(
        "You receive a phishing email. What's the best response?",
        listOf("Click links to check", "Forward to IT", "Delete immediately", "Report as spam"),
        3,
        "Reporting helps your organization improve spam filters and protect others.",
        "Threat Response"
    ),
    QuizQuestion(
        "What's the ethical concern about public facial recognition databases?",
        listOf("Storage costs", "Consent issues", "Accuracy rates", "Color accuracy"),
        1,
        "Collecting biometric data without explicit consent violates privacy rights.",
        "Ethics"
    ),
    QuizQuestion(
        "Which encryption is considered most secure for wireless networks?",
        listOf("WEP", "WPA", "WPA2", "WPA3"),
        3,
        "WPA3 uses the latest security protocols and individualized encryption.",
        "Network Security"
    ),
    QuizQuestion(
        "What should a privacy policy clearly communicate?",
        listOf("Marketing goals", "Data usage practices", "Office locations", "Employee salaries"),
        1,
        "Transparency about data usage builds trust and meets legal requirements.",
        "Ethics"
    )
)

@Composable
fun QuizScreen() {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }
    var showFeedback by remember { mutableStateOf(false) }
    var quizCompleted by remember { mutableStateOf(false) }

    val questions = remember { securityQuestions.shuffled() }
    val progress = remember(currentQuestionIndex) {
        (currentQuestionIndex + 1).toFloat() / questions.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!quizCompleted) {
            // Progress Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                // Score and Progress Text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Correct: $score/${questions.size}",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${currentQuestionIndex + 1}/${questions.size}",
                        color = Color.Gray
                    )
                }

                // Linear Progress Bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                            .padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.primary,
                )

                // Dots Indicator
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(questions.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(
                                    if (index <= currentQuestionIndex)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.LightGray
                                )
                        )
                    }
                }
            }

            // Question and Answers
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Security & Ethics Quiz",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = questions[currentQuestionIndex].question,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    questions[currentQuestionIndex].answers.forEach { answer ->
                        Button(
                            onClick = { userAnswer = answer },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = answer,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        val correctAnswer = questions[currentQuestionIndex]
                            .answers[questions[currentQuestionIndex].correctAnswerIndex]
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
                    val correctAnswer = questions[currentQuestionIndex]
                        .answers[questions[currentQuestionIndex].correctAnswerIndex]

                    Column(
                        modifier = Modifier.padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
                                "✅ Correct! Well done!"
                            } else {
                                "❌ Incorrect. The correct answer was: $correctAnswer"
                            },
                            color = if (userAnswer.equals(correctAnswer, ignoreCase = true)) Color.Green else Color.Red,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Explanation: ${questions[currentQuestionIndex].explanation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

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
                            Text(if (currentQuestionIndex < questions.size - 1) "Next Question" else "Finish Quiz")
                        }
                    }
                }
            }
        } else {
            // Quiz Complete Screen
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quiz Complete!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Final Score: $score/${questions.size}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(
                    onClick = {
                        currentQuestionIndex = 0
                        score = 0
                        userAnswer = ""
                        showFeedback = false
                        quizCompleted = false
                    }
                ) {
                    Text("Restart Quiz")
                }
            }
        }
    }
}