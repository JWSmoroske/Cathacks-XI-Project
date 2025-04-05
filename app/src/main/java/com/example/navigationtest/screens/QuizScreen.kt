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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

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

/**
 * Main composable for the quiz screen
 * Handles quiz flow, question display, answer selection, and progress tracking
 */
@Composable
fun QuizScreen() {
    // State management variables
    var currentQuestionIndex by remember { mutableIntStateOf(0) } // Tracks current question index
    var userAnswer by remember { mutableStateOf("") }             // Stores the user's selected answer
    var showFeedback by remember { mutableStateOf(false) }        // Controls feedback visibility
    var quizCompleted by remember { mutableStateOf(false) }       // Flag for quiz completion state
    val answeredCorrectly = remember { mutableStateListOf<Boolean>() } // Tracks correctness of answers

    // Quiz setup
    val questions = remember { securityQuestions.shuffled() } // Randomize question order
    val progress = remember(currentQuestionIndex) {           // Calculate progress percentage
        (currentQuestionIndex + 1).toFloat() / questions.size
    }
    val score = answeredCorrectly.count { it }                // Calculate current score

    // Main layout column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!quizCompleted) {
            // ================== Progress Section ==================
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                // Score and question counter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Correct: $score/${questions.size}", color = MaterialTheme.colorScheme.primary)
                    Text(text = "${currentQuestionIndex + 1}/${questions.size}", color = Color.Gray)
                }

                // Linear progress bar
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth().height(8.dp).padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                // Progress dots indicator
                Row(modifier = Modifier.padding(vertical = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(questions.size) { index ->
                        val isAnswered = index < answeredCorrectly.size
                        val isCurrent = index == currentQuestionIndex
                        // Determine dot color based on answer state
                        val color = when {
                            isAnswered -> if (answeredCorrectly[index]) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                            isCurrent -> MaterialTheme.colorScheme.primary
                            else -> Color.LightGray
                        }

                        // Individual progress dot
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }

            // ================== Question & Answers Section ==================
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Quiz title
                Text(
                    text = "Security & Ethics Quiz",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Current question text
                Text(
                    text = questions[currentQuestionIndex].question,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Answer buttons
                Column(modifier = Modifier.padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val currentQuestion = questions[currentQuestionIndex]
                    val correctAnswer = currentQuestion.answers[currentQuestion.correctAnswerIndex]

                    currentQuestion.answers.forEach { answer ->
                        val isCorrectAnswer = answer == correctAnswer
                        val isSelected = answer == userAnswer
                        val showColors = showFeedback

                        // Answer button with conditional coloring
                        Button(
                            onClick = { if (!showFeedback) userAnswer = answer },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !showFeedback,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when {
                                    showColors && isCorrectAnswer -> Color(0xFF1B5E20)  // Dark green for correct
                                    showColors && isSelected -> Color(0xFFB71C1C)       // Dark red for incorrect
                                    else -> MaterialTheme.colorScheme.primary          // Default color
                                }
                            )
                        ) {
                            Text(
                                text = answer,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

                // Submit answer button
                Button(
                    onClick = {
                        val isCorrect = userAnswer == questions[currentQuestionIndex]
                            .answers[questions[currentQuestionIndex].correctAnswerIndex]
                        answeredCorrectly.add(isCorrect)
                        showFeedback = true
                    },
                    enabled = userAnswer.isNotBlank() && !showFeedback
                ) {
                    Text("Submit Answer")
                }

                // Feedback section
                if (showFeedback) {
                    val correctAnswer = questions[currentQuestionIndex]
                        .answers[questions[currentQuestionIndex].correctAnswerIndex]

                    Column(
                        modifier = Modifier.padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Result feedback
                        Text(
                            text = if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
                                "✅ Correct! Well done!"
                            } else {
                                "❌ Incorrect. The correct answer was: $correctAnswer"
                            },
                            color = if (userAnswer.equals(correctAnswer, ignoreCase = true))
                                Color(0xFF1B5E20) else Color(0xFFB71C1C),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Detailed explanation
                        Text(
                            text = "Explanation: ${questions[currentQuestionIndex].explanation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        // Navigation button (Next Question/Finish Quiz)
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
            // ================== Quiz Completion Screen ==================
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Completion message
                Text(
                    text = "Quiz Complete!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Final score display
                Text(
                    text = "Final Score: $score/${questions.size}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Restart quiz button
                Button(
                    onClick = {
                        currentQuestionIndex = 0
                        answeredCorrectly.clear()
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