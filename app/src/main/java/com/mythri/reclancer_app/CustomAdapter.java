package com.mythri.reclancer_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mythri.reclancer_app.Model.FreeSearch;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<FreeSearch> dataModelArrayList;

    public CustomAdapter(Context ctx, ArrayList<FreeSearch> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.activity_f_result, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //holder.name.setText(dataModelArrayList.get(position).getName());
       myViewHolder.category.setText(dataModelArrayList.get(i).getName());
       myViewHolder.email.setText(dataModelArrayList.get(i).getEmail());
    }


    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView category,email;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

           // country = (TextView) itemView.findViewById(R.id.country);
            category = (TextView) itemView.findViewById(R.id.name);
            email= (TextView) itemView.findViewById(R.id.email);
            //iv = (ImageView) itemView.findViewById(R.id.iv);
        }

    }

}