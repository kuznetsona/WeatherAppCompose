package com.example.weatherappcompose

import android.app.DownloadManager.Request
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

const val API_KEY = "e34e4c19711deb09f38f50619aa775c1"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CityName("Tomsk", this)
                }
            }
        }
    }
}

@Composable
fun CityName(name: String, context: Context) {
    val state = remember{
        mutableStateOf("Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Text(text = "Temp in $name = ${state.value}")
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter){
            Button(onClick = {
                getResult(name, state, context)

            }, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()

                ) {
                Text(text = "Refresh")
            }
        }
    }
    
    
}


private fun getResult(city: String, state: MutableState<String>,
                      context: Context){
    val url = "https://api.openweathermap.org/data/2.5/forecast?" +
            "q=$city&units=metric&appid=$API_KEY"
    val queque = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url, {
            response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONArray("list").getJSONObject(0)
                .getJSONObject("main")
                .getString("temp").toString()

            //state.value = response
        },
        {
            error ->
        }

    )
    queque.add(stringRequest)


}