package com.avplayer.audio.fragments;

import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.audio.adapters.AudioFileListAdapter;
import com.avplayer.helper.FileUtilsHelper;
import com.avplayer.video.models.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by shivappar.b on 13-03-2019
 */
public class AudioFilesListFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener {
    public static final String TAG = "AudioFilesListFragment";
    RecyclerView recyclerView;
    AudioFileListAdapter adapter;
    private List<File> fileListToDelete;
    private TextView tvTotalLength, tvPlayedLength;
    private ImageView ivPlay, ivPrevious, ivNext;
    private SeekBar seekBar;
    public Handler handler;
    private MediaPlayer mediaPlayer;
    public Runnable SongDurationUpdater = new Runnable() {
        @Override
        public void run() {
            int time = mediaPlayer.getCurrentPosition();
            tvPlayedLength.setText((String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(time),
                    TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))));
            seekBar.setProgress(time);
            handler.postDelayed(this, 100);
        }
    };

    public AudioFilesListFragment() {

    }

    public static AudioFilesListFragment getInstance(String folderPath) {
        AudioFilesListFragment filesFragment = new AudioFilesListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", folderPath);
        filesFragment.setArguments(bundle);
        return filesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder_list, container, false);
        handler = new Handler();
        ivPlay = view.findViewById(R.id.iv_play_pause);
        ivPlay.setOnClickListener(this);
        ivPrevious = view.findViewById(R.id.iv_previous);
        ivPrevious.setOnClickListener(this);
        ivNext = view.findViewById(R.id.iv_next);
        ivNext.setOnClickListener(this);
        seekBar = view.findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
        mediaPlayer = new MediaPlayer();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String folderName = bundle.getString("path");
            adapter = new AudioFileListAdapter(getAudioList(folderName), getActivity());
            recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            tvTotalLength = view.findViewById(R.id.tv_total_time);
            tvPlayedLength = view.findViewById(R.id.tv_time_played);
        }
        return view;
    }

    private List<FileInfo> getAudioList(String folderName) {
        List<FileInfo> myList = new ArrayList<>();
        File file = new File(folderName);
        File[] list = file.listFiles();
        for (File aList : list) {
            if (aList.getName().toLowerCase(Locale.getDefault()).endsWith(".mp3")) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(aList.getName());
                fileInfo.setFilePath(aList.getPath());
                myList.add(fileInfo);
            }
        }

        Collections.sort(myList, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo o1, FileInfo o2) {
                return o1.getFileName().compareToIgnoreCase(o2.getFileName());
            }
        });

        return myList;
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
            durationText = String.format("%02d:%02d", m, s);
        } else {
            durationText = String.format("%02d:%02d,%02d", h, m, s);
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
            ivPlay.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_pause));
            handler.postDelayed(SongDurationUpdater, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFiles() {
        HashMap<Integer, FileInfo> filesToDelete = adapter.getFileToDelete();
        adapter.deleteFiles(filesToDelete);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ivPlay.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_play));
                } else {
                    mediaPlayer.start();
                    ivPlay.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_pause));
                }
                break;
            case R.id.iv_previous:
                break;
            case R.id.iv_next:
                break;
            case R.id.iv_delete:
                deleteFiles();
                break;
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: ");
        mp.stop();
        ivPlay.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_play));
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
