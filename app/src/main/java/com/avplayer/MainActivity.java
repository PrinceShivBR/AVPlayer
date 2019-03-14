package com.avplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.avplayer.audio.activity.AudioFolderListActivity;
import com.avplayer.video.activity.VideoFolderListActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showVideoList(View view) {
        Intent intent = new Intent(this, VideoFolderListActivity.class);
        startActivity(intent);
    }

    public void showAudioList(View view) {
        Intent intent = new Intent(this, AudioFolderListActivity.class);
        startActivity(intent);
    }
}
