package com.avplayer.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.video.fragments.VideoFilesListFragment;
import com.avplayer.video.fragments.VideoFolderListFragment;

/**
 * Created by shivappar.b on 08-03-2019
 */
public class VideoFolderListActivity extends AppCompatActivity {
    public static final String TAG = "VideoFolderListActivity";
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
        transaction.add(R.id.root, new VideoFolderListFragment(), "MainFragment");
        transaction.addToBackStack("MainFragment");
        transaction.commit();
    }

    public void showTransition(VideoFilesListFragment fragment, String folderName) {
        toolBarText.setText(folderName);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.root, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

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
