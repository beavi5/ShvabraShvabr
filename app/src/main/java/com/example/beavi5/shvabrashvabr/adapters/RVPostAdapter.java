package com.example.beavi5.shvabrashvabr.adapters;



import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


import com.example.beavi5.shvabrashvabr.R;
import com.example.beavi5.shvabrashvabr.model.RSSItem;

import java.util.ArrayList;
import java.util.List;


public class RVPostAdapter extends RecyclerView.Adapter<RVPostAdapter.PostHolder> {
    List<RSSItem> listPosts = new ArrayList();

    public RVPostAdapter(List<RSSItem> listPosts) {
        this.listPosts = listPosts;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);

        return new PostHolder(v);
    }

    @Override
    public void onBindViewHolder(PostHolder holder, final int position) {
        holder.mTitle.setText(listPosts.get(position).getTitle());

      holder.mDescWebView.loadDataWithBaseURL(null, listPosts.get(position).getDesc(), "text/html", "utf-8", null);

        holder.mPubDate.setText(listPosts.get(position).getPubDate());
        holder.mAuthor.setText(listPosts.get(position).getAuthor());
        holder.mCategories.setText(listPosts.get(position).getCategories());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Uri address = Uri.parse(listPosts.get(position).getLink());
                Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                v.getContext().startActivity(openlinkIntent);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }


    public class PostHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mCategories;
        TextView mPubDate;
        TextView mAuthor;
        WebView mDescWebView;

        public PostHolder(View itemView) {
            super(itemView);

            mDescWebView = itemView.findViewById(R.id.postDescWebView);
            mTitle = itemView.findViewById(R.id.postTitle);
            mPubDate = itemView.findViewById(R.id.postPubDate);
            mAuthor = itemView.findViewById(R.id.postAuthor);
            mCategories = itemView.findViewById(R.id.postCategories);
            mCategories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textViewCategories =  (TextView)view;
                    if (textViewCategories.getMaxLines()>1) textViewCategories.setMaxLines(1);
                    else     textViewCategories.setMaxLines(20);
                }
            });







        }
    }
}

