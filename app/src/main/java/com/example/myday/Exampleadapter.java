package com.example.myday;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Exampleadapter extends RecyclerView.Adapter<Exampleadapter.Exampleviewholder> {
    private ArrayList<Exampleitem>mexamplelist;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onitemclick(int position);
        void ondelete(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listner){
        mlistener = listner;
    }
    public static class Exampleviewholder extends RecyclerView.ViewHolder{
        public TextView mtitle,mtime,mdate;
        public ImageView mdelimg;

        public Exampleviewholder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mtitle = itemView.findViewById(R.id.reltitle);
            mtime = itemView.findViewById(R.id.reltime);
            mdate = itemView.findViewById(R.id.reldate);
            mdelimg = itemView.findViewById(R.id.deltask);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onitemclick(position);
                        }
                    }
                }
            });
            mdelimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.ondelete(position);
                        }
                    }
                }
            });
        }
    }
    public Exampleadapter(ArrayList<Exampleitem>examplelist){
        mexamplelist = examplelist;
    }
    @NonNull
    @Override
    public Exampleviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        Exampleviewholder evh = new Exampleviewholder(v,mlistener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull Exampleviewholder holder, int position) {
        Exampleitem curitem = mexamplelist.get(position);
        holder.mtitle.setText(curitem.getTitle());
        holder.mdate.setText(parsedate(curitem.getDate()));
        holder.mtime.setText(parsetime(curitem.getTime()));
    }
    public String parsedate(String d){
        String year = d.substring(0,4), month = d.substring(4,6), day = d.substring(6,8);
        return day+"-"+month+"-"+year;
    }
    public String parsetime(String d){
        String h = d.substring(0,2),m = d.substring(2,4);
        int hr = Integer.parseInt(h);
        Boolean pm = false;
        if(hr>=12){
            pm=true;
            hr%=12;
        }
        h = String.valueOf(hr);
        return h+":"+m+(pm?" PM":" AM");
    }
    @Override
    public int getItemCount() {
        return mexamplelist.size();
    }
}
