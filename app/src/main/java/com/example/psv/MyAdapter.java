package com.example.psv;

import android.content.Context;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<ModelView> modelViewList;

    public  MyAdapter(List<ModelView> modelViewList){
        this.modelViewList = modelViewList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        final ModelView modelView = modelViewList.get(position);
        holder.txt_name.setText(modelView.getName());
        holder.txt_state.setText(modelView.getState());
    }

    @Override
    public int getItemCount() {
        return (modelViewList!=null?modelViewList.size():0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        protected TextView txt_name;
        protected TextView txt_state;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            this.txt_state = (TextView) itemView.findViewById(R.id.txt_state);
        }
    }
}
