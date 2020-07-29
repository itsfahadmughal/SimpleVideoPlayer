package com.example.videoplayersimple;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<VideoHolder> {
    @NonNull
    Context context;
    ArrayList<File> videoArrayList;

    public MyAdapter(@NonNull Context context, ArrayList<File> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;

    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {
        holder.fileName.setText(MainActivity.fileArrayList.get(position).getName());
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoArrayList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.thumbnailImage.setImageBitmap(bitmap);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, videoPlayerActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        if (videoArrayList.size() > 0) {
            return videoArrayList.size();
        } else
            return 1;
    }
}

class VideoHolder extends RecyclerView.ViewHolder {

    TextView fileName;
    ImageView thumbnailImage;
    CardView mCardView;

    VideoHolder(View view) {
        super(view);

        fileName = view.findViewById(R.id.videofilename);
        thumbnailImage = view.findViewById(R.id.thumbnail);
        mCardView = view.findViewById(R.id.cardViewId);
    }
}
