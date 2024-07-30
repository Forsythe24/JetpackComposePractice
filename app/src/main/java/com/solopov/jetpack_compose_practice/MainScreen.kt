package com.solopov.jetpack_compose_practice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.solopov.jetpack_compose_practice.ui.theme.Black1
import com.solopov.jetpack_compose_practice.ui.theme.Black60
import com.solopov.jetpack_compose_practice.ui.theme.Gray2
import com.solopov.jetpack_compose_practice.ui.theme.Gray3
import com.solopov.jetpack_compose_practice.ui.theme.Gray4
import com.solopov.jetpack_compose_practice.ui.theme.Gray5
import com.solopov.jetpack_compose_practice.ui.theme.Gray6

val fontFamilyRobotoMedium = FontFamily(Font(R.font.roboto_medium))
val roundedCornerShape20 = RoundedCornerShape(20.dp)

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 28.dp, end = 28.dp, top = 36.dp)
    ) {
        GreetingSection()
        Spacer(modifier = Modifier.height(36.dp))
        SearchBar()
        Spacer(modifier = Modifier.height(40.dp))
        PlacesSection(navController = navController)
    }
}

@Composable
fun GreetingSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hi, Miron \uD83D\uDC4B",
                fontSize = 30.sp,
                fontFamily = fontFamilyRobotoMedium,
                color = Black1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Explore the world",
                fontSize = 20.sp,
                fontFamily = fontFamilyRobotoMedium,
                color = Gray2
            )
        }
        RoundImage(
            modifier = Modifier.size(52.dp),
            painter = painterResource(id = R.drawable.profile_image)
        )
    }
}

@Composable
fun RoundImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null
) {
    Image(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        painter = painter,
        contentDescription = contentDescription,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val textState = remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = Gray3, shape = roundedCornerShape20),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(0.6f),
            value = textState.value,
            onValueChange = {
                textState.value = it
            },
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = fontFamilyRobotoMedium
            ),
            placeholder = {
                Text(
                    text = "Search places",
                    fontSize = 16.sp,
                    fontFamily = fontFamilyRobotoMedium,
                    color = Gray2
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Gray2,
                cursorColor = Color.Black,
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
        Icon(
            modifier = Modifier
                .size(width = 2.dp, height = 32.dp),
            painter = painterResource(id = R.drawable.search_bar_divider),
            contentDescription = null,
            tint = Gray2,
        )
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = R.drawable.filter),
            contentDescription = "filter",
            tint = Gray2,
        )
    }
}

@Composable
fun PlacesSection(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Popular places",
            fontFamily = fontFamilyRobotoMedium,
            fontWeight = FontWeight.SemiBold,
            color = Black1,
            fontSize = 20.sp
        )
        Text(
            text = "View all",
            fontFamily = fontFamilyRobotoMedium,
            fontWeight = FontWeight.SemiBold,
            color = Gray2,
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(40.dp))
    CategoriesSection(categories = listOf("Most Viewed", "Nearby", "Latest"))
    Spacer(modifier = Modifier.height(44.dp))
    Places(
        places = listOf(
            Place(
                imageId = R.drawable.mount_fuji,
                name = "Mount Fuji",
                region = "Tokyo",
                country = "Japan",
                rating = 4.8f,
                fullName = "Mount Fuji",
                price = 200,
                travelTimeHours = 7f,
                currentTempCelsius = 23,
                description = stringResource(R.string.mt_fuji_description)
            ),
            Place(
                imageId = R.drawable.andes,
                name = "Andes",
                region = "South",
                country = "America",
                rating = 4.5f,
                fullName = "Andes Mountain",
                price = 230,
                travelTimeHours = 8f,
                currentTempCelsius = 16,
                description = stringResource(R.string.andes_desctiption)
            ),
        ),
        navController = navController
    )
}

@Composable
fun CategoriesSection(modifier: Modifier = Modifier, categories: List<String>) {
    var selectedChipIndex by remember {
        mutableIntStateOf(0)
    }
    LazyRow(
        modifier = modifier
    ) {
        items(categories.size) {
            CategoryChip(
                label = categories[it],
                isSelected = it == selectedChipIndex,
                onSelected = {
                    selectedChipIndex = if (selectedChipIndex == it) -1 else it
                }
            )
            Spacer(modifier = Modifier.width(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    FilterChip(
        modifier = modifier,
        selected = isSelected,
        onClick = onSelected,
        label = {
            Text(
                modifier = Modifier.padding(20.dp),
                text = label,
                fontFamily = fontFamilyRobotoMedium
            )
        },
        border = null,
        shape = roundedCornerShape20,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Gray4,
            labelColor = Gray5,
            selectedContainerColor = Black1,
            selectedLabelColor = Color.White,
        )
    )
}

@Composable
fun Places(modifier: Modifier = Modifier, places: List<Place>, navController: NavController) {
    LazyRow(modifier = modifier) {
        items(places.size) {
            PlaceCard(
                place = places[it],
                onClicked = {
                    navController.navigate("${Screen.DetailsScreen.route}/${Gson().toJson(places[it])}")
                }
            )
            Spacer(
                modifier = Modifier
                    .width(24.dp)
            )
        }
    }
}

@Composable
fun PlaceCard(modifier: Modifier = Modifier, place: Place, onClicked: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(0.7f)
            .padding(bottom = 40.dp)
            .clickable {
                onClicked()
            },
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
            horizontalArrangement = Arrangement.End
        ) {
            ShapeWithIcon(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = Black60, shape = CircleShape),
                icon = painterResource(id = R.drawable.heart),
                iconTint = Color.White,
                iconSize = 22.dp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            PlaceInfoCard(place = place)
        }
    }
}

@Composable
fun ShapeWithIcon(modifier: Modifier = Modifier, icon: Painter, iconTint: Color, iconSize: Dp, onClicked: () -> Unit = {}) {
    Box(
        modifier = modifier
            .clickable {
                onClicked()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize),
            painter = icon,
            contentDescription = null,
            tint = iconTint
        )
    }
}

@Composable
fun PlaceInfoCard(modifier: Modifier = Modifier, place: Place) {
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
                fontSize = 14.sp
            )
            Text(
                text = buildAnnotatedString {
                    val boldStyle = SpanStyle(
                        color = Color.White,
                        fontFamily = fontFamilyRobotoMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    pushStyle(boldStyle)
                    append(place.name + ", ")
                    pop()
                    append(place.region)
                },
                style = mainTextStyle
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier
                            .size(16.dp),
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null,
                        tint = Gray6
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = place.region + ", " + place.country,
                        style = mainTextStyle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        modifier = Modifier
                            .size(12.dp),
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null,
                        tint = Gray6,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = place.rating.toString(),
                        style = mainTextStyle,
                    )
                }
            }
        }
    }
}

