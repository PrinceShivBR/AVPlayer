package com.avplayer.audio.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.audio.fragments.AudioFilesListFragment;
import com.avplayer.audio.fragments.AudioFoldersFragment;
import com.avplayer.video.fragments.FolderListFragment;

/**
 * Created by shivappar.b on 13-03-2019
 */
public class AudioFolderListActivity extends AppCompatActivity {
    public static final String TAG = "AudioFolderListActivity";
    private Toolbar toolbar;
    private TextView toolBarText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_list);
        toolbar = findViewById(R.id.tool_bar);
        toolBarText = findViewById(R.id.tv_toolbar_text);
        toolBarText.setText("Folders");
        if (getSupportActionBar() != null) {
            setSupportActionBar(toolbar);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.root, new AudioFoldersFragment(), "MainFragment");
        transaction.addToBackStack("MainFragment");
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
        AudioFilesListFragment filesListFragment = (AudioFilesListFragment) getSupportFragmentManager().findFragmentByTag("AudioFilesListFragment");
        if (filesListFragment != null) {
            filesListFragment.playMedia(fileLength);
        }
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
}
