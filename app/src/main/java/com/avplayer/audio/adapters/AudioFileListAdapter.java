package com.avplayer.audio.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.audio.activity.AudioFolderListActivity;
import com.avplayer.video.models.FileInfo;

import java.util.List;

/**
 * Created by shivappar.b on 13-03-2019
 */
public class AudioFileListAdapter extends RecyclerView.Adapter<AudioFileListAdapter.FileViewHolder>
        implements View.OnClickListener {
    private List<FileInfo> fileItemList;
    private Context context;

    public AudioFileListAdapter(List<FileInfo> folderList, Context context) {
        this.fileItemList = folderList;
        this.context = context;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_file_item_list, viewGroup, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder fileViewHolder, final int i) {
        fileViewHolder.tvFileName.setText(fileItemList.get(i).getFileName());
        fileViewHolder.tvFileName.setTag(fileItemList.get(i).getFilePath());
        fileViewHolder.tvFileName.setOnClickListener(this);
        fileViewHolder.ivOptions.setOnClickListener(this);
        fileViewHolder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileItemList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_file_name:
                ((AudioFolderListActivity) context).playMediaFile((String) v.getTag());

//                AudioPlayActivity playActivity = new AudioPlayActivity((String) v.getTag());
//                playActivity.playMedia();
                break;
            case R.id.iv_options:
                break;
        }
    }

    class FileViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName;
        ImageView ivOptions;
        CheckBox cbSelect;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            ivOptions = itemView.findViewById(R.id.iv_options);
            cbSelect=itemView.findViewById(R.id.cb_delete);
        }
    }
}

