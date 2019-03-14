package com.avplayer.video.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avplayer.R;
import com.avplayer.video.models.FileInfo;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by shivappar.b on 08-03-2019
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FileViewHolder> {
    private List<FileInfo> fileItemList;
    private Context context;

    public FileListAdapter(List<FileInfo> folderList, Context context) {
        this.fileItemList = folderList;
        this.context = context;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_item_list, viewGroup, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder fileViewHolder, final int i) {
        fileViewHolder.tvFileName.setText(fileItemList.get(i).getFileName());
        Glide.with(context)
                .load(fileItemList.get(i).getFilePath())
                .into(fileViewHolder.ivVideoThumbnail);
        fileViewHolder.cvFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW);
                playIntent.setDataAndType(Uri.parse(fileItemList.get(i).getFilePath()), "video/*");
                context.startActivity(playIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileItemList.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName;
        ImageView ivVideoThumbnail;
        CardView cvFile;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            ivVideoThumbnail = itemView.findViewById(R.id.iv_video_thumbnail);
            cvFile = itemView.findViewById(R.id.cv_file);
        }
    }
}
