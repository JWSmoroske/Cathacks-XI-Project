package com.example.navigationtest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PracticesScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Cybersecurity Best Practices",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        BestPracticeCard(
            title = "1. Use Strong Passwords",
            description = "Create passwords that are at least 12 characters long and combine uppercase letters, lowercase letters, numbers, and symbols. Using a password manager can help you generate and securely store these passwords.",
            example = "Example: Use a generated password like \"G7#t9kL!zP3e\"."
        )
        BestPracticeCard(
            title = "2. Enable Multi-Factor Authentication (MFA)",
            description = "Add an extra layer of security by requiring a second form of verification. This reduces the risk of unauthorized access even if your password is compromised.",
            example = "Example: Pair your password with an authenticator app such as Google Authenticator."
        )
        BestPracticeCard(
            title = "3. Keep Software and Devices Updated",
            description = "Regular updates patch vulnerabilities and improve security. Ensuring your systems are up to date is one of the easiest ways to protect against threats.",
            example = "Example: Enable automatic updates on your smartphone and computer."
        )
        BestPracticeCard(
            title = "4. Be Cautious with Email Attachments and Links",
            description = "Phishing attacks often use deceptive emails to trick you into clicking on malicious links or opening harmful attachments. Always verify the source before interacting.",
            example = "Example: Hover over links to preview the URL and double-check the sender's details."
        )
        BestPracticeCard(
            title = "5. Regularly Back Up Important Data",
            description = "Backing up data ensures you can recover information in case of hardware failure, ransomware, or accidental deletion. Keep backups in a separate, secure location.",
            example = "Example: Use a cloud backup service or an external drive for routine backups."
        )
        BestPracticeCard(
            title = "6. Use Antivirus and Anti-Malware Solutions",
            description = "Security software can detect and remove threats before they cause harm. Regular scans help maintain your system's security.",
            example = "Example: Install trusted antivirus software and schedule weekly scans."
        )
        BestPracticeCard(
            title = "7. Educate Yourself and Others on Cybersecurity",
            description = "Staying informed about current threats and best practices is essential. Regular education helps you and your team recognize and respond to potential risks.",
            example = "Example: Attend online cybersecurity courses or webinars to keep up with the latest trends."
        )
    }
}

@Composable
fun BestPracticeCard(title: String, description: String, example: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = example,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}