package com.m3rc.githubstargazers.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m3rc.githubstargazers.R;
import com.m3rc.githubstargazers.network.output.GitHubUser;

import java.util.ArrayList;
import java.util.List;

public class GitHubUserRecyclerViewAdapter extends
        RecyclerView.Adapter<GitHubUserRecyclerViewAdapter.GitHubUserViewHolder> {

    private static final int EMPTY = -1, USER = 0, LOAD_MORE = 1;

    private List<GitHubUser> users;
    private boolean complete = false;

    public GitHubUserRecyclerViewAdapter(List<GitHubUser> users) {
        this.users = new ArrayList<>(users);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 1)
            return EMPTY;
        else if (position == getItemCount() - 1)
            return LOAD_MORE;
        else
            return USER;
    }

    @Override
    public GitHubUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = null;
        switch (viewType) {
            case EMPTY:
                row = inflater.inflate(R.layout.row_empty, parent, false);
                break;
            case LOAD_MORE:
                row = inflater.inflate(R.layout.row_load_more, parent, false);
                break;
            case USER:
                row = inflater.inflate(R.layout.row_github_user, parent, false);
                break;
        }
        return new GitHubUserViewHolder(row);
    }

    @Override
    public void onBindViewHolder(GitHubUserViewHolder holder, int position) {
        switch (getItemViewType(position)) {

            case USER:
                GitHubUser user = users.get(position);

                Glide.with(holder.userImage.getContext())
                        .load(user.getAvatar_url())
                        .placeholder(R.drawable.github_icon)
                        .animate(android.R.anim.fade_in)
                        .into(holder.userImage);

                holder.userName.setText(user.getLogin());
                break;

            case LOAD_MORE:
                if (complete)
                    holder.loadMoreButton.setVisibility(View.GONE);
                else
                    holder.loadMoreButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (users != null) {
            if (users.isEmpty()) {
                return 1;
            } else
                return users.size() + 1;
        } else
            return 1;
    }

    public void addUsers(List<GitHubUser> newUsers) {
        int oldSize = users.size();
        users.addAll(newUsers);
        notifyItemRangeInserted(oldSize, newUsers.size());
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    class GitHubUserViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName;

        Button loadMoreButton;

        GitHubUserViewHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.row_github_user_image);
            userName = (TextView) itemView.findViewById(R.id.row_github_user_name);

            loadMoreButton = (Button) itemView.findViewById(R.id.row_load_more);
        }
    }
}
