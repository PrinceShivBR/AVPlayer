package com.avplayer.video.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.video.activity.VideoFolderListActivity;
import com.avplayer.video.fragments.VideoFilesListFragment;
import com.avplayer.video.models.Folder;

import java.util.List;

/**
 * Created by shivappar.b on 08-03-2019
 */
public class VideoFolderListAdapter extends RecyclerView.Adapter<VideoFolderListAdapter.FolderViewHolder> {
    private List<Folder> folderList;
    private Context context;

    public VideoFolderListAdapter(List<Folder> folderList, Context context) {
        this.folderList = folderList;
        this.context = context;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.folder_item_list, viewGroup, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder folderHolder, final int i) {
        folderHolder.tvFolderName.setText(getCapitalisedWord(folderList.get(i).getFolderName()));
        folderHolder.tvVideoCount.setText(getVideosCount(folderList.get(i).getVideoCount()));
        folderHolder.ivNewFiles.setVisibility(folderList.get(i).getIsVisible());
        folderHolder.cvFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoFilesListFragment fragment = VideoFilesListFragment.getInstance(folderList.get(i).getFolderPath());
                ((VideoFolderListActivity) context).showTransition(fragment, getCapitalisedWord(folderList.get(i).getFolderName()));
            }
        });
    }

    private String getCapitalisedWord(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    private String getVideosCount(String count) {
        if (count != null)
            return count.equals("1") ? count + "video" : count + " videos";
        return count;
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class FolderViewHolder extends RecyclerView.ViewHolder {
        TextView tvFolderName, tvVideoCount;
        ImageView ivNewFiles;
        CardView cvFolder;

        FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFolderName = itemView.findViewById(R.id.tv_folder_name);
            tvVideoCount = itemView.findViewById(R.id.tv_video_count);
            ivNewFiles = itemView.findViewById(R.id.iv_new_files);
            cvFolder = itemView.findViewById(R.id.cv_folder);
        }
    }
}
