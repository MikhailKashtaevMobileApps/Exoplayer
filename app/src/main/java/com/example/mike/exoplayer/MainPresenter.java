package com.example.mike.exoplayer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainPresenter {

    private static MainPresenter instance;
    private Context context;
    private SimpleExoPlayer player;
    private long position;

    public static MainPresenter getInstance(){
        if ( instance == null ){
            instance = new MainPresenter();
        }
        return instance;
    }

    public void setView(Context context){
        this.context = context;
    }

    public SimpleExoPlayer getPlayer(){
        // If player exists already, return player
        if ( this.player != null ){
            return player;
        }

        // If not then create new one
        player = ExoPlayerFactory.newSimpleInstance(
                context,
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        player.setPlayWhenReady(false);
        player.seekTo(0, position);

        player.prepare( new ExtractorMediaSource.Factory(
                        new DefaultHttpDataSourceFactory(context.getString( R.string.app_name ))
                )
                        .createMediaSource(Uri.parse( context.getString( R.string.video_url )))
        );
        return player;
    }

}
