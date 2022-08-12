package com.example.notnote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.NotesViewHolder>{
/*  NIM : 10119288
    Nama : Muhamad Dimas
    Kelas : IF-7
    Tanggal : Rabu, 11 Agustus 2022
    */
    private final Context mContext;
    private Cursor mCursor;
    private onClickListenerNotes listenerNotes;

    public CatatanAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.catatan_items, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        String judul = mCursor.getString(mCursor.getColumnIndexOrThrow (DBcatatan.judul));
        String tanggalbuat = mCursor.getString(mCursor.getColumnIndexOrThrow (DBcatatan.tanggal_buat));
        String isicatatan = mCursor.getString(mCursor.getColumnIndexOrThrow (DBcatatan.isi_catatan));
        long id = mCursor.getLong(mCursor.getColumnIndexOrThrow(DBcatatan.id_notes));

        holder.itemView.setTag(id);
        holder.txJudul.setText(judul);
        holder.txTanggalBuat.setText(tanggalbuat);
        holder.txIsiCatatan.setText(isicatatan);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{

        private final TextView txJudul;
        private final TextView txTanggalBuat;
        private final TextView txIsiCatatan;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            txJudul = itemView.findViewById(R.id.txJudul);
            txTanggalBuat = itemView.findViewById(R.id.txTanggalBuat);
            txIsiCatatan = itemView.findViewById(R.id.txIsiCatatan);

            itemView.setOnClickListener(v -> {
                long position = (long) itemView.getTag();
                listenerNotes.onItemClickNotes(position);
            });
        }
    }

    public interface onClickListenerNotes{
        void onItemClickNotes(long id);
    }

    public void setOnClickListenerNotes(onClickListenerNotes listenerNotes){
        this.listenerNotes = listenerNotes;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;

        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }
}
