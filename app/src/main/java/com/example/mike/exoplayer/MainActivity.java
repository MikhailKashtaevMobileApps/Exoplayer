package com.example.mike.exoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private PlayerView pView;
    public static final String TAG = "__TAG__";
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pView = findViewById( R.id.pView );

        mainPresenter = MainPresenter.getInstance();
        mainPresenter.setView(this);
    }

    private void initializePlayer(){
        mainPresenter.initPlayer();
        pView.setPlayer(mainPresenter.getPlayer());
    }

    private void releasePlayer(){
        mainPresenter.releasePlayer();
    }


    @Override
    public void onStart() {
        super.onStart();
        System.out.println(TAG+" onStart");
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(TAG+" onResume");
        if ((Util.SDK_INT <= 23 || mainPresenter.getPlayer() == null)) {
            Log.d(TAG, "onResume: getPlayer returns null");
            System.out.println(TAG+" onResume getplayer is null");
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}
