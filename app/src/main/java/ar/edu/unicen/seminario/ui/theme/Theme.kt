import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color.Black,
    secondary = Color.DarkGray,
    onPrimary = Color.Blue,
    background = Color.Black,
    onError = Color.Red

)
private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = Color.LightGray,
    onPrimary = Color(0xFF99A1DD),
    background = Color.Black,
    onError = Color.Red
)
@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        typography = AppTypography,
        colorScheme = colors,
        content = content
    )
}