package com.divarius.fifteennewseditors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    NewsGSON[] newsArr;
    View.OnClickListener clickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public Button btn;
        public ImageButton icon;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.news_card_title);
            image = (ImageView) v.findViewById(R.id.news_card_img);
            btn = (Button) v.findViewById(R.id.news_card_sub);
            icon = (ImageButton) v.findViewById(R.id.news_card_sub_img);
        }
    }

    public NewsFeedAdapter (NewsGSON[] newsArr) {
        this.newsArr = newsArr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (clickListener == null) {
            clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.news_card_sub) {
                        int pos = (int) v.getTag();
                        String status = ((Button)v).getText().toString();
                        newsArr[pos].setStatus(status.equals("Подписан") ? "unsubscribed" : "subscribed");
                        notifyDataSetChanged();
                    } else if (v.getId() == R.id.news_card) {
                        Toast.makeText(v.getContext(), String.valueOf(v.getTag()), Toast.LENGTH_SHORT).show();
                    }
                }
            };
        }

        String status = newsArr[position].getStatus();

        if (status.equals("subscribed")) {
            holder.icon.setImageResource(R.drawable.ok);
            holder.btn.setText("Подписан");
        } else {
            holder.icon.setImageResource(R.drawable.rss);
            holder.btn.setText("Подписаться");
        }

        holder.itemView.setTag(newsArr[position].getId());
        holder.itemView.setOnClickListener(clickListener);
        holder.btn.setOnClickListener(clickListener);
        holder.btn.setTag(position);
        holder.title.setText(newsArr[position].getTitle());
        Picasso.with(holder.itemView.getContext())
                .load(newsArr[position].getImageThumbnail())
                .into(holder.image);

        /*if (position != 0) {
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(40, 0, 0, 0);
            holder.itemView.setLayoutParams(lp);
        }*/
    }

    @Override
    public int getItemCount() {
        return newsArr.length;
    }
}
