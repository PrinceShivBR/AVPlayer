package com.avplayer.audio.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avplayer.AVPlayerApplication;

import java.io.IOException;

/**
 * Created by shivappar.b on 14-03-2019
 */
public class AudioPlayActivity extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener {
    public static final String TAG = "AudioPlayActivity";
    private MediaPlayer mediaPlayer;
    private String mFileUrl;

    private AudioAttributes audioAttributes;
    private AudioFocusRequest audioFocusRequest;
    private AudioManager manager;

    public AudioPlayActivity(String url) {
        this.mFileUrl = url;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMediaPlayer();
    }

    public void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(AVPlayerApplication.getAppContext(), Uri.parse(mFileUrl));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playMedia() {
        if (mediaPlayer == null)
            initMediaPlayer();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
            initMediaPlayer();
            mediaPlayer.start();
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    public interface IAudioPlayingListener {
        void setCurrentLength(String playPosition);
    }
}
