package com.solopov.jetpack_compose_practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.solopov.jetpack_compose_practice.ui.theme.Black60
import com.solopov.jetpack_compose_practice.ui.theme.Gray6
import com.solopov.jetpack_compose_practice.ui.theme.Gray7
import com.solopov.jetpack_compose_practice.ui.theme.Gray8

@Composable
fun DetailsScreen(modifier: Modifier = Modifier, navController: NavController, place: Place) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 28.dp, start = 28.dp, end = 28.dp, bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        PlaceDetailsCard(place = place, navController = navController)
        Spacer(modifier = Modifier.height(40.dp))
        OverviewSection(place = place)
        BookButton()
    }
}

@Composable
fun PlaceDetailsCard(modifier: Modifier = Modifier, place: Place, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
    ) {
        Image(
            modifier = Modifier
                .clip(roundedCornerShape20)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = place.imageId),
            contentDescription = null
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ShapeWithIcon(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = Black60, shape = CircleShape),
                icon = painterResource(id = R.drawable.ic_arrow_left),
                iconTint = Color.White,
                iconSize = 12.dp,
                onClicked = {
                    navController.popBackStack()
                }
            )
            ShapeWithIcon(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = Black60, shape = CircleShape),
                icon = painterResource(id = R.drawable.ic_save),
                iconTint = Color.White,
                iconSize = 24.dp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            PlaceDetailsInfoCard(place = place)
        }
    }
}

@Composable
fun PlaceDetailsInfoCard(modifier: Modifier = Modifier, place: Place) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Black60, shape = roundedCornerShape20)
            .padding(16.dp)
    ) {
        Column {
            val mainTextStyle = TextStyle(
                color = Gray6,
                fontFamily = fontFamilyRobotoMedium,
            )
            val boldTextStyle = TextStyle(
                color = Color.White,
                fontFamily = fontFamilyRobotoMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = place.fullName,
                    style = boldTextStyle,
                )
                Text(
                    text = "Price",
                    style = mainTextStyle,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null,
                        tint = Gray6
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = place.region + ", " + place.country,
                        style = mainTextStyle,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = buildAnnotatedString {
                        val darkTextStyle = SpanStyle(
                            color = Color(0xFF434343),
                            fontSize = 20.sp
                        )
                        val lightTextStyle = SpanStyle(
                            color = Gray6,
                            fontSize = 26.sp
                        )
                        pushStyle(darkTextStyle)
                        append("$")
                        pop()
                        pushStyle(lightTextStyle)
                        append(place.price.toString())
                    }
                )
            }
        }
    }
}

@Composable
fun OverviewSection(modifier: Modifier = Modifier, place: Place) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Overview",
            color = Color(0xFF1B1B1B),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamilyRobotoMedium
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = "Details",
            color = Color(0x621B1B1B),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamilyRobotoMedium
        )
    }
    Spacer(modifier = Modifier.height(32.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SquareWithIconAndText(iconId = R.drawable.ic_time, text = "${place.travelTimeHours} hours")
        SquareWithIconAndText(iconId = R.drawable.ic_cloud, text = "${place.currentTempCelsius} °C")
        SquareWithIconAndText(iconId = R.drawable.ic_star, text = place.rating.toString())
    }
    Spacer(modifier = Modifier.height(32.dp))
    val bottomFade =
        Brush.verticalGradient(0.3f to Gray8, 0.89f to Color(0x00A5A5A5))
    Text(
        text = place.description,
        fontSize = 18.sp,
        fontFamily = fontFamilyRobotoMedium,
        modifier = Modifier
            .fadingEdge(bottomFade)
            .fillMaxWidth()
            .height(80.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp),
        color = Gray8,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SquareWithIconAndText(modifier: Modifier = Modifier, iconId: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShapeWithIcon(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFEDEDED)
                )
                .size(34.dp),
            icon = painterResource(id = iconId),
            iconTint = Gray7,
            iconSize = 16.dp
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color(0xFF7E7E7E),
            fontFamily = fontFamilyRobotoMedium
        )
    }
}

@Composable
fun BookButton(modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = {},
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp),
        shape = roundedCornerShape20,
        containerColor = Color(0xFF1B1B1B),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Book Now",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = fontFamilyRobotoMedium
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_paper_plane),
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }
