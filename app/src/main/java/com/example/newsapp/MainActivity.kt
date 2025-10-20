package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.news.main.utils.AppTextStyles
import com.example.news.main.NewsMainViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.main.ui.NewsMainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var textStyles: AppTextStyles

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val viewModel: NewsMainViewModel = viewModel()

                    NewsMainScreen(
                        viewModel = viewModel,
                        textStyles = textStyles,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

