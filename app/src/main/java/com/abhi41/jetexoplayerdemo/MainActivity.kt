package com.abhi41.jetexoplayerdemo

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.abhi41.jetexoplayerdemo.ui.theme.JetExoPlayerDemoTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetExoPlayerDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   // VideoPlayer()
                    AdvanceExoplayer()
                }
            }
        }
    }
}
//https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4

@Composable
fun VideoPlayer() {

    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    var urlVideo =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    var urlMusic =
        "http://commondatastorage.googleapis.com/codeskulptor-assets/Epoq-Lepidoptera.ogg"

    val mediaItem = MediaItem.fromUri(Uri.parse(urlVideo))
    exoPlayer.setMediaItem(mediaItem)
    val playerView = StyledPlayerView(context)
    playerView.player = exoPlayer

    // playerView.useController = false  //this is for hide controller
    DisposableEffect(
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            factory = {
                playerView
            }
        )
    ) {
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true


        //to prevent memory leak
        onDispose {
            exoPlayer.release()
        }
    }


}
