package com.android.comic.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.android.comic.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.rememberNavHostEngine

class MainActivity : AppCompatActivity(){
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

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen() {

}