package com.example.socialmedia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialmedia.R;

import java.lang.invoke.LambdaConversionException;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    Context context;
    List<String> mHashtags;
    List<String> mHashTagsCount;
    public TagAdapter(Context context, List<String> mHashTags, List<String> mHashTagsCount) {

        this.context=context;
        this.mHashtags=mHashTags;
        this.mHashTagsCount=mHashTagsCount;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tag_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void filter(List<String> mSearchTags, List<String> mSearchTagsCount) {

        this.mHashtags = mSearchTags;
        this.mHashTagsCount = mSearchTagsCount;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tag;
        public TextView noOfPosts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.hash_tag);
            noOfPosts = itemView.findViewById(R.id.no_of_posts);
        }
    }
}
