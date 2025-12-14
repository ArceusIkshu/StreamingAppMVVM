package com.example.streamvideoplayermvvmpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.streamvideoplayermvvmpractice.data.VideoData
import com.example.streamvideoplayermvvmpractice.data.mainvideoList
import com.example.streamvideoplayermvvmpractice.ui.theme.StreamVideoPlayerMVVMpracticeTheme
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.streamvideoplayermvvmpractice.viewModel.VideoPlayerViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StreamVideoPlayerMVVMpracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ){
                    StreamingVideoPlayer()
                }
            }
        }
    }
}

@Composable
fun StreamingVideoPlayer(){

    var isPlaying by remember{
        mutableStateOf(false)
    }
    var videoItemIndex by remember{
        mutableIntStateOf(0)
    }
    val viewModel: VideoPlayerViewModel = viewModel()
    viewModel.videoList = mainvideoList
    val context = LocalContext.current

    Column{

        StreamerPlayer(
            viewModel = viewModel,
            isPlaying = isPlaying,
            onPlayerClosed ={isVideoPlaying ->
                isPlaying = isVideoPlaying
            })

        LazyColumn(
            Modifier.padding(10.dp),
            content = {
                itemsIndexed(items = mainvideoList){ index,item ->
                    Row(
                        Modifier.fillMaxWidth()
                            .clickable{
                                if (videoItemIndex != index) isPlaying = false
                                viewModel.index = index
                                videoItemIndex = viewModel.index

                            },
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        AsyncImage(model = item.thumbnail, contentDescription = null)
                        Text(
                            text = "Video ${index+1}",
                            Modifier
                                .fillMaxSize()
                                .weight(1f)

                        )
                    }
                    Divider(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)

                    )
                }
            }
        )
        LaunchedEffect(key1 = videoItemIndex) {
            isPlaying = true
            viewModel.apply {
                releasePlayer()
                initializePlayer(context)
                playVideo()
            }
        }

    }
}


