package com.example.weatherappcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.weatherappcompose.data.WeatherModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


@Composable
fun MainScreen(currentDay: MutableState<WeatherModel>) {
    Column(
        modifier = Modifier
            .background(LightBackground)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBackground),
            elevation = 0.dp
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
                    text = currentDay.value.city,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Background
                    )
                )

                Text(
                    text = currentDay.value.time.split(' ')[0],
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Background
                    )
                )

                AsyncImage(
                    model = "https://openweathermap.org/img/wn/" +
                            currentDay.value.icon +
                            "@2x.png",
                    contentDescription = "02d",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(top = 30.dp)
                )

                Text(
                    text = currentDay.value.maxTemp + "° / " + currentDay.value.minTemp + "°",
                    style = TextStyle(
                        fontSize = 65.sp,
                        color = Background, fontWeight = FontWeight(600)
                    )
                )

                Text(
                    text = currentDay.value.condition,
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
                        text = currentDay.value.wind + " m/s",
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
fun TabLayout(daysList: MutableState<List<WeatherModel>>,
currentDay: MutableState<WeatherModel>) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)

    ) {
        TabRow(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp, top = 20.dp),
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
            val list = when(index){
                0 -> getWeatherByHours(currentDay.value.hours)
                1 -> daysList.value
                else -> daysList.value
            }

            MainList(list, currentDay)

        }

    }


}

private fun getWeatherByHours(hours: String): List<WeatherModel> {
    if (hours.isEmpty()) return listOf()

    val list = ArrayList<WeatherModel>()
    val hoursArray = JSONArray(hours)

    for (i in 0 until 8){
        val item = hoursArray[i] as JSONObject
        list.add(
            WeatherModel(
                "",
                item.getString("dt_txt").toString(),
                "",
                "",
                "",
                item.getJSONArray("weather").getJSONObject(0).getString("icon"),
                item.getJSONObject("main").getString("temp_max").toFloat().toInt().toString(),
                item.getJSONObject("main").getString("temp_min").toFloat().toInt().toString(),
                ""

            )
        )
    }
    return list
}





