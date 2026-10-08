package com.`is`.rescuepet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.`is`.rescuepet.ui.navigation.RescuePetNavGraph
import com.`is`.rescuepet.ui.theme.RescuePetTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RescuePetTheme {
                RescuePetNavGraph()
            }
        }
    }
}