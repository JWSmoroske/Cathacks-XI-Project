import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

/**
 * Parses asterisk-based Markdown:
 *  - ***Heading*** => Header style
 *  - **bold** => Bold
 *  - *italic* => Italic
 */
fun parseBasicMarkdown(input: String): AnnotatedString {
    val pattern = Regex(
        pattern = listOf(
            "(\\*\\*\\*([^*]+)\\*\\*\\*)", // ***Heading***
            "(\\*\\*([^*]+)\\*\\*)",       // **bold**
            "(\\*([^*]+)\\*)",             // *italic*
            "([^*]+)"                      // everything else
        ).joinToString("|")
    )

    return buildAnnotatedString {
        val matches = pattern.findAll(input)
        matches.forEach { matchResult ->
            val headingGroup = matchResult.groups[2]  // ***Heading***
            val boldGroup = matchResult.groups[4]     // **bold**
            val italicGroup = matchResult.groups[6]   // *italic*
            val normalGroup = matchResult.groups[7]   // plain text

            when {
                headingGroup != null -> {
                    // Add spacing above the heading
                    append("\n")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    ) {
                        append(headingGroup.value)
                    }
                    // Optional: no extra newline after header
                    //append("\n")
                }
                boldGroup != null -> {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldGroup.value)
                    }
                }
                italicGroup != null -> {
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(italicGroup.value)
                    }
                }
                normalGroup != null -> {
                    append(normalGroup.value)
                }
            }
        }
    }
}
