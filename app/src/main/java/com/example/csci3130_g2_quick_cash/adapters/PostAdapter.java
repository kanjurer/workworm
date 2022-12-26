package com.example.csci3130_g2_quick_cash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.RecyclerView.PostRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private PostRecyclerViewInterface postRecyclerViewInterface;

    private final Context context;
    private final List<Post> listOfPosts;

    public PostAdapter(Context context) {
        this.context = context;
        this.listOfPosts = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_job_list, parent, false);
        return new MyViewHolder(view, postRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = listOfPosts.get(position);
        holder.title.setText(post.getPostTitle());
        holder.rate.setText(post.getPostSalary());
        holder.postId = post.getPostId();
    }

    @Override
    public int getItemCount() {
        return listOfPosts.size();
    }

    public void addToList(List<Post> list) {
        this.listOfPosts.clear();
        this.listOfPosts.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addItemToList(Post item) {
        this.listOfPosts.add(item);
        this.notifyItemChanged(getItemCount());
    }

    public void setPostRecyclerViewInterface(PostRecyclerViewInterface postRecyclerViewInterface) {
        this.postRecyclerViewInterface = postRecyclerViewInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView rate;
        String postId;

        public MyViewHolder(@NonNull View itemView, PostRecyclerViewInterface postRecyclerViewInterface) {
            super(itemView);
            title = itemView.findViewById(R.id.jTitle);
            rate = itemView.findViewById(R.id.pRate);
            postId = "";
            itemView.setOnClickListener(view -> {
                if (postRecyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        postRecyclerViewInterface.onPostClick(this.postId);
                    }
                }
            });
        }
    }
}
