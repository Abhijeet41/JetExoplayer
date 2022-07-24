package com.abhi41.jetexoplayerdemo


import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.abhi41.jetexoplayerdemo.common.LockScreenOrientation
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource

@Composable
fun AdvanceExoplayer() {
    val configuration = LocalConfiguration.current
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    var videoUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    val httpDataSourceFactory: HttpDataSource.Factory =
        DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(false)

    val dataSourceFactory: DataSource.Factory = DataSource.Factory {
        val dataSource = httpDataSourceFactory.createDataSource()
        dataSource.setRequestProperty("cookie", "cookieValue")
        dataSource.setRequestProperty("Range", "1-10000")
        dataSource
    }

    val mContext = LocalContext.current
    // Initializing ExoPLayer
    val mExoPlayer = remember {
        ExoPlayer.Builder(mContext)
            .setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory)).build().apply {
                val mediaItem = MediaItem.Builder()
                    .setUri(Uri.parse(videoUrl))
                    .build()
                setMediaItem(mediaItem)
                playWhenReady = true
                prepare()
            }
    }

    DisposableEffect(
        //Implementing ExoPlayer
        AndroidView(factory = {
            StyledPlayerView(mContext).apply {
                player = mExoPlayer
            }
        })
    ) {
        onDispose {
            mExoPlayer.release()
        }
    }
}
