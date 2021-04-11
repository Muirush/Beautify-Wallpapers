package com.symoh.beautifywallpapers;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    Context context;
    List<Upload> mUpload;
    OnItemClickListener mListener;

    public ImageAdapter(Context context, List<Upload> mUpload) {
        this.context = context;
        this.mUpload = mUpload;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view,parent,false);
        return  new ImageHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Upload currentUpload = mUpload.get(position);
        holder.textView.setText(currentUpload.getmName());
        Picasso.get().load(currentUpload.getmImageUrl()).placeholder(R.drawable.loading).fit().centerCrop().into(holder.imageView);
        holder.textView1.setText(currentUpload.getmCategory());

    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener{
        TextView textView, textView1;
        ImageView imageView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.name);
            textView1 = itemView.findViewById(R.id.category);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                int position =getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);

                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Please select action");
            MenuItem Downloads =menu.add(Menu.NONE, 1,1,"Show Downloads");
            MenuItem Delete =menu.add(Menu.NONE, 2,2,"Delete");

            Downloads.setOnMenuItemClickListener(this);
            Delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null){
                int position =getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                   switch (item.getItemId()){
                       case 1 :mListener.TotalDownloads(position);
                       return  true;
                       case 2 :mListener.Delete(position);
                           return  true;
                   }

                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void  onItemClick(int position);
        void  TotalDownloads(int position);
        void  Delete(int position);
    }
    public void setOnclickListener(OnItemClickListener listener){
        mListener = listener;

    }
}
