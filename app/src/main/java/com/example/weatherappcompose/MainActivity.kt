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
import com.example.weatherappcompose.screens.MainScreen
import com.example.weatherappcompose.screens.TabLayout

import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import org.json.JSONObject

const val API_KEY = "e34e4c19711deb09f38f50619aa775c1"


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
               Column {
                   MainScreen()
                   TabLayout()
               }
            }
        }
    }
}