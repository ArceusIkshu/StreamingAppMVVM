package com.example.streamvideoplayermvvmpractice

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.streamvideoplayermvvmpractice.viewModel.VideoPlayerViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun StreamerPlayer(
    viewModel: VideoPlayerViewModel,
    onPlayerClosed:(isVideoPlaying: Boolean) -> Unit,
    isPlaying: Boolean
){
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        if (isPlaying) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { cont ->
                    viewModel.playerViewBuilder(cont)
                })
            IconButton(
                onClick = {
                    onPlayerClosed(false)
                    viewModel.releasePlayer()
                }, modifier = Modifier.align(
                    Alignment.TopEnd
                )
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = ""
            )
        }
    }

}