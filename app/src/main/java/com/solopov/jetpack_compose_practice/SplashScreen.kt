import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.solopov.jetpack_compose_practice.R
import com.solopov.jetpack_compose_practice.ui.theme.Blue1
import com.solopov.jetpack_compose_practice.ui.theme.Blue2
import com.solopov.jetpack_compose_practice.ui.theme.Gray1
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.0f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navController.navigate("main_screen") {
            popUpTo("splash_screen") {
                inclusive = true
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Blue1, Blue2)
                )
            )
            .scale(scale.value),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 88.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Travel",
                    fontFamily = FontFamily(Font(R.font.lobster_regular)),
                    fontSize = 44.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(15.dp))
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Find Your Dream Destination With Us",
                modifier = Modifier.fillMaxWidth(),
                color = Gray1,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.roboto_medium))
            )
        }
    }
}
