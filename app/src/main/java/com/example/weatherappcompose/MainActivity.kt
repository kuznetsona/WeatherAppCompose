package com.example.weatherappcompose

import android.app.DownloadManager.Request
import android.content.Context
import android.os.Bundle
import android.telephony.IccOpenLogicalChannelResponse
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherappcompose.data.WeatherModel
import com.example.weatherappcompose.screens.MainScreen
import com.example.weatherappcompose.screens.TabLayout
import com.example.weatherappcompose.ui.theme.LightBackground

import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

const val API_KEY = "e34e4c19711deb09f38f50619aa775c1"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }

                val currentDay = remember {
                    mutableStateOf(
                        WeatherModel(
                            "", "", "0", "",
                            "", "", "0", "0", ""
                        )
                    )
                }



                getData("London", this, daysList, currentDay)

                Column(
                    modifier = Modifier
                        .background(LightBackground)
                ) {
                    MainScreen(currentDay)
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }
}


private fun getData(
    city: String, context: Context,
    daysList: MutableState<List<WeatherModel>>,
    currentDay: MutableState<WeatherModel>
) {
    val url = "https://api.openweathermap.org/data/2.5/forecast?q=" +
            "$city&units=metric&appid=$API_KEY"

    val queue = Volley.newRequestQueue(context)

    val sRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url, { response ->
            val list = getWeatherByDays(response)
            currentDay.value = list[0]
            daysList.value = list

        },
        {
            Log.d("MyLog", "VolleyError: $it")
        }
    )

    queue.add(sRequest)
}

private fun getWeatherByDays(response: String): List<WeatherModel> {
    if (response.isEmpty()) return listOf()
    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("city").getString("name")
    val days = mainObject.getJSONArray("list")
    for (i in 0 until days.length() step 8) {
        val item = days.getJSONObject(i)
        var str = "["
        for (j in i until i + 7) {
            str += days[j].toString() + ","
        }
        str += days[i + 7].toString() + "]"
        list.add(
            WeatherModel(
                city,
                item.getString("dt_txt").toString(),
                "",
                item.getJSONArray("weather")
                    .getJSONObject(0).getString("main").toString(),
                item.getJSONObject("wind")
                    .getString("speed").toFloat().toInt().toString(),
                item.getJSONArray("weather")
                    .getJSONObject(0).getString("icon"),
                item.getJSONObject("main")
                    .getString("temp_max").toFloat().toInt().toString(),
                item.getJSONObject("main")
                    .getString("temp_min").toFloat().toInt().toString(),
                str

            )
        )


        Log.d("Weather Hours", list.get(0).hours.toString())


    }


    list[0] = list[0].copy(
        temp = mainObject.getJSONArray("list")
            .getJSONObject(0)
            .getJSONObject("main")
            .getString("temp").toFloat().toInt().toString()
    )

    return list


}



