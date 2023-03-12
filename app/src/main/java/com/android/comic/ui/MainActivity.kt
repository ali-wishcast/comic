package com.android.comic.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.android.comic.ui.comic.NavGraphs
import com.android.comic.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val engine = rememberNavHostEngine()
            val navController = engine.rememberNavController()
            AppTheme {
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root,
                    engine = engine,
                )
            }
        }
    }
}