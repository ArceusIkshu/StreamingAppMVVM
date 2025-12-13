package com.example.streamvideoplayermvvmpractice.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.streamvideoplayermvvmpractice.data.VideoData

class VideoPlayerViewModel: ViewModel() {
    private var exoPlayer: ExoPlayer? = null
    var index: Int = 0
    var videoList: List<VideoData> = listOf()

    fun initializePlayer(context: Context){
        exoPlayer = ExoPlayer.Builder(context).build()

    }

    fun releasePlayer(){
        exoPlayer?.playWhenReady = false
        exoPlayer?.release()
        exoPlayer = null
    }

    fun playVideo(){
        exoPlayer?.let{player->
            player.apply{
                stop()
                clearMediaItems()
                setMediaItem(MediaItem.fromUri(Uri.parse(videoList[index].videoUrl)))
                playWhenReady = true
                prepare()
                play()
            }

        }
    }

}