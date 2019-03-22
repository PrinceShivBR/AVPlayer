package com.avplayer.audio.helper;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.avplayer.R;

import java.io.IOException;

/**
 * Created by shivappar.b on 18-03-2019
 */
public class AudioPlayHelper extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener, AudioManager.OnAudioFocusChangeListener {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initMediaPlayer();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
    }

    private void prepareMediaPlayer() {
    }

    private void playMedia() {
    }

    private void stopMediaPlayer() {
    }

    private void playNextFile() {
    }

    private void playPreviousFile() {
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
