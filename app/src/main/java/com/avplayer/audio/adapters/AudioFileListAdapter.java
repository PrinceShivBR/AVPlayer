package com.avplayer.audio.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.audio.activity.AudioFolderListActivity;
import com.avplayer.helper.FileUtilsHelper;
import com.avplayer.video.models.FileInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shivappar.b on 13-03-2019
 */
public class AudioFileListAdapter extends RecyclerView.Adapter<AudioFileListAdapter.FileViewHolder>
        implements View.OnClickListener {
    private List<FileInfo> fileItemList;
    private Context context;
    private HashMap<Integer, FileInfo> fileToDelete;

    public AudioFileListAdapter(List<FileInfo> fileList, Context context) {
        this.fileItemList = fileList;
        this.context = context;
        fileToDelete = new HashMap<>();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audio_file_item_list, viewGroup, false);
        return new FileViewHolder(view);
    }

    public HashMap<Integer, FileInfo> getFileToDelete() {
        return fileToDelete;
    }

    public void deleteFiles(HashMap<Integer, FileInfo> fileList) {
        for (Map.Entry<Integer, FileInfo> info : fileList.entrySet()) {
            FileUtilsHelper.deleteFiles(info.getValue());
            notifyItemRemoved(info.getKey());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder fileViewHolder, final int i) {
        final FileInfo info = fileItemList.get(i);
        fileViewHolder.tvFileName.setText(fileItemList.get(i).getFileName());
        fileViewHolder.tvFileName.setTag(fileItemList.get(i).getFilePath());
        fileViewHolder.tvFileName.setOnClickListener(this);
        fileViewHolder.ivOptions.setOnClickListener(this);
        fileViewHolder.cbSelect.setOnCheckedChangeListener(null);
        fileViewHolder.cbSelect.setChecked(info.isSelected());
        fileViewHolder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                info.setSelected(isChecked);
                if (isChecked) {
                    fileToDelete.put(i, fileItemList.get(i));
                } else {
                    fileToDelete.remove(i);
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
                break;
            case R.id.iv_options:
                break;
        }
    }

    class FileViewHolder extends RecyclerView.ViewHolder /*implements CompoundButton.OnCheckedChangeListener*/ {
        TextView tvFileName;
        ImageView ivOptions;
        CheckBox cbSelect;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            ivOptions = itemView.findViewById(R.id.iv_options);
            cbSelect = itemView.findViewById(R.id.cb_delete);
        }
    }
}

