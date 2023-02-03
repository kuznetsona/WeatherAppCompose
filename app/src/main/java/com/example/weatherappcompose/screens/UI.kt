package com.example.weatherappcompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.ui.theme.Background
import com.example.weatherappcompose.ui.theme.LightBackground
import org.w3c.dom.Text

@Composable
fun ListItem(item: WeatherModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = LightBackground,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 13.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.time, color = Background)

            AsyncImage(
                model = "https://openweathermap.org/img/wn/"+
                        item.icon + "@2x.png",
                contentDescription = item.icon
            )

            Text(
                text = item.temp.ifEmpty {
                   item.maxTemp + "°/" + item.minTemp + "°"
                },
                color = Background,
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.padding(
                    end = 8.dp
                )
            )

        }


    }

}

@Composable
fun MainList(list: List<WeatherModel>, currentDays: MutableState<WeatherModel>){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            list
        ) { _, item ->
            ListItem(item)
        }


    }



}