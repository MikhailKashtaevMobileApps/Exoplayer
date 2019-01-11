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

    public static final String TAG = "__TAG__";

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

    public void initPlayer(){
        Log.d(TAG, "initPlayer: pos="+position);
        System.out.println(TAG+" initPlayer, pos="+position);
        player = ExoPlayerFactory.newSimpleInstance(
                context,
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        player.setPlayWhenReady(true);

        player.prepare( new ExtractorMediaSource.Factory(
                        new DefaultHttpDataSourceFactory(context.getString( R.string.app_name ))
                )
                        .createMediaSource(Uri.parse( context.getString( R.string.video_url )))
        );
        player.seekTo(0, position);
    }

    public SimpleExoPlayer getPlayer(){
        // If not then create new one
        return player;
    }

    public void releasePlayer(){
        Log.d(TAG, "releasePlayer: ");
        System.out.println(TAG+" releasePlayer");
        position = player.getCurrentPosition();
        player.release();
        player = null;
    }

}
