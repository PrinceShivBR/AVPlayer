package com.avplayer.audio.activity;

import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.audio.fragments.AudioFilesListFragment;
import com.avplayer.audio.fragments.AudioFoldersFragment;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by shivappar.b on 13-03-2019
 */
public class AudioFolderListActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    public static final String TAG = "AudioFolderListActivity";
    private Toolbar toolbar;
    private TextView toolBarText;

    private TextView tvTotalLength, tvPlayedLength;
    private ImageView ivPlay, ivPrevious, ivNext;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    public Handler handler;
    public Runnable SongDurationUpdater = new Runnable() {
        @Override
        public void run() {
            int time = mediaPlayer.getCurrentPosition();
            tvPlayedLength.setText((String.format(Locale.US, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time),
                    TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
            seekBar.setProgress(time);
            handler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_folder_list);
        toolbar = findViewById(R.id.tool_bar);
        toolBarText = findViewById(R.id.tv_toolbar_text);
        toolBarText.setText("Folders");
        if (getSupportActionBar() != null) {
            setSupportActionBar(toolbar);
        }
        ImageView ivDelete = findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(this);

        handler = new Handler();
        ivPlay = findViewById(R.id.iv_play_pause);
        ivPlay.setOnClickListener(this);
        ivPrevious = findViewById(R.id.iv_previous);
        ivPrevious.setOnClickListener(this);
        ivNext = findViewById(R.id.iv_next);
        ivNext.setOnClickListener(this);
        seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
        tvPlayedLength = findViewById(R.id.tv_time_played);
        tvTotalLength = findViewById(R.id.tv_total_time);
        mediaPlayer = new MediaPlayer();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.root, new AudioFoldersFragment(), "MainFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showTransition(AudioFilesListFragment fragment, String folderName) {
        toolBarText.setText(folderName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.root, fragment, "AudioFilesListFragment");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void playMediaFile(String fileLength) {
        playMedia(fileLength);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
            toolBarText.setText("Folders");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ivPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play));
                } else {
                    mediaPlayer.start();
                    ivPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause));
                }
                break;
            case R.id.iv_previous:
                break;
            case R.id.iv_next:
                break;
            case R.id.iv_delete:
                AudioFilesListFragment filesListFragment = (AudioFilesListFragment) getSupportFragmentManager().findFragmentByTag("AudioFilesListFragment");
                if (filesListFragment != null) {
                    filesListFragment.deleteFiles();
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mediaPlayer != null && fromUser) {
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: ");
        mp.stop();
        ivPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play));
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public void setAudioFileLength(String filePath) {
        System.out.println("filePath = [" + filePath + "]");
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(filePath);
        String audioLength = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int duration = 0;
        if (audioLength != null) {
            duration = Integer.parseInt(audioLength);
        }
        seekBar.setMax(duration);
        long h = TimeUnit.MILLISECONDS.toHours(duration);
        long m = TimeUnit.MILLISECONDS.toMinutes(duration);
        long s = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        String durationText;
        if (h == 0) {
            durationText = String.format(Locale.US, "%02d:%02d", m, s);
        } else {
            durationText = String.format(Locale.US, "%02d:%02d:%02d", h, m, s);
        }
        tvTotalLength.setText(durationText);
    }

    public void playMedia(String mFileUrl) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(mFileUrl);
            setAudioFileLength(mFileUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            ivPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause));
            handler.postDelayed(SongDurationUpdater, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
