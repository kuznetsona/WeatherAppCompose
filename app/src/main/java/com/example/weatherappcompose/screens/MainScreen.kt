package com.example.weatherappcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappcompose.ui.theme.Background
import com.example.weatherappcompose.ui.theme.LightBackground
import com.example.weatherappcompose.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .background(LightBackground)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBackground),
        ) {
            Column(
                modifier = Modifier
                    .background(LightBackground)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightBackground),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = "search",
                            tint = Background
                        )

                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                            contentDescription = "refresh",
                            tint = Background
                        )

                    }


                }

                Text(
                    text = "London",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Background
                    )
                )

                Text(
                    text = "now",
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Background
                    )
                )

                AsyncImage(
                    model = "https://openweathermap.org/img/wn/02d@2x.png",
                    contentDescription = "02d",
                    modifier = Modifier
                        .size(110.dp)
                        .padding(top = 40.dp)
                )

                Text(
                    text = "23°",
                    style = TextStyle(
                        fontSize = 65.sp,
                        color = Background, fontWeight = FontWeight(600)
                    )
                )

                Text(
                    text = "Cloudy",
                    modifier = Modifier.padding(top = 18.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Background
                    )
                )

                Text(
                    text = "Wind",
                    modifier = Modifier.padding(top = 20.dp),
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Background
                    )
                )

                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_air_24),
                        contentDescription = "air",
                        tint = Background
                    )

                    Text(
                        text = "4 m/s",
                        modifier = Modifier.padding(top = 4.dp, start = 2.dp),
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = Background
                        )
                    )

                }


            }


        }


    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout() {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            },
            backgroundColor = LightBackground,
            contentColor = Background
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(text = text)
                    },
                    selectedContentColor = Background
                )
            }

        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->

        }

    }



}


