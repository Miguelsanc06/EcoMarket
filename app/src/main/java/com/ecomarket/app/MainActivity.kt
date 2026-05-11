package com.ecomarket.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ecomarket.app.navigation.AppNavigation
import com.ecomarket.app.ui.theme.EcoMarketTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            EcoMarketTheme {

                AppNavigation()

            }
        }
    }
}