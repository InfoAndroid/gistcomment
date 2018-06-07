package com.infoandroid.gistcomment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infoandroid.gistcomment.view.CommentActivity;
import com.infoandroid.gistcomment.view.LoginActivity;
import com.infoandroid.gistcomment.R;
import com.infoandroid.gistcomment.model.GistRepo;
import com.infoandroid.gistcomment.preferences.AppSharedPreference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mukesh .
 */

public class GistRepoAdapter extends RecyclerView.Adapter<GistRepoAdapter.ViewHolder> {
    List<GistRepo> list;

    GistRepo gistRepo;
    Context mContext;
    public GistRepoAdapter(Context context,  List<GistRepo> list) {// super();
        this.mContext = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gist_repo_row, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        gistRepo=list.get(position);

        holder.tvDescription.setText( gistRepo.getDescription().toString());
        holder.tvOwner.setText( gistRepo.getOwner().getLogin().toString());
        holder.tvCreated.setText( gistRepo.getCreatedAt().toString());
        holder.tvUpdate.setText( gistRepo.getUpdatedAt().toString());
        holder.tvComment.setText( gistRepo.getComments().toString());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDescription)
         TextView tvDescription;

        @BindView(R.id.tvOwner)
         TextView tvOwner;

        @BindView(R.id.tvCreated)
         TextView tvCreated;

        @BindView(R.id.tvUpdate)
         TextView tvUpdate;

        @BindView(R.id.tvComment)
         TextView tvComment;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppSharedPreference.putString("id",list.get(getAdapterPosition()).getId().toString(),mContext);
                    String id = AppSharedPreference.getString("name","",mContext);
                    String password = AppSharedPreference.getString("pass","",mContext);
                    if (id.equals(null)||id.equals("")||password.equals(null)||password.equals("")) {
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    }else {
                        mContext.startActivity(new Intent(mContext, CommentActivity.class));
                    }

                }
            });
        }
    }

}
