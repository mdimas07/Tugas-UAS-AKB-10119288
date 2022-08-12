package com.example.notnote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/*  NIM : 10119288
    Nama : Muhamad Dimas
    Kelas : IF-7
    Tanggal : Rabu, 11 Agustus 2022
    */
public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    ArrayList<ObjectInfo> justObject;

    public InfoAdapter(ArrayList<ObjectInfo> justObject){
        this.justObject = justObject;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item,
                parent,
                false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectInfo justObjects = justObject.get(position);
        holder.ivObject.setImageResource(justObjects.ivObject);
        holder.tvName.setText(justObjects.txTips);
    }

    @Override
    public int getItemCount() {
        return justObject.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivObject;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivObject = itemView.findViewById(R.id.ivAboutList);
            tvName = itemView.findViewById(R.id.txTipsList);
        }
    }
}
