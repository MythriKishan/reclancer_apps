package com.mythri.reclancer_app.Freelancer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mythri.reclancer_app.Model.FDashAds;
import com.mythri.reclancer_app.Model.F_RDashAds;
import com.mythri.reclancer_app.Model.RResultAds;
import com.mythri.reclancer_app.R;

import java.util.List;

public class FDashboardAdapter extends RecyclerView.Adapter<FDashboardAdapter.ViewHolderFJobs> {

    // declaring variables
    private Context context;
    private List<FDashAds> results;

    //constructor
    FDashboardAdapter(Context context, List<FDashAds> R_Jobs) {
        this.context = context;
        this.results = R_Jobs;
    }

    @Override
    public FDashboardAdapter.ViewHolderFJobs onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.activity_mfjobs,parent,false);

        View view = LayoutInflater.from(context).inflate(R.layout.activity_fdash, parent, false);
        FDashboardAdapter.ViewHolderFJobs viewholder = new FDashboardAdapter.ViewHolderFJobs(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final FDashboardAdapter.ViewHolderFJobs holder, int position) {

        //setting data on each row of list according to position.
        //results.clear();
        holder.txtname.setText(results.get(position).getName());
        holder.txtskills.setText(results.get(position).getSkills());
        holder.txtexp.setText(results.get(position).getExp());
        holder.txtad_id.setText(results.get(position).getAd_id());
        holder.txtmainad_id.setText(results.get(position).getmainAd_id());

        holder.ad_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //adid = (TextView)findViewById(R.id.);
                //final String token = ((Globals)v.this.getToken();
                String adid = holder.txtad_id.getText().toString().trim();
                String mad_id = holder.txtmainad_id.getText().toString().trim();
                Intent sad = new Intent(v.getContext(), int_Rad_details.class);
                sad.putExtra("adId",adid);
                sad.putExtra("madId",mad_id);
                //sad.putExtra("token",token);
                v.getContext().startActivity(sad);

            }


        });

    }

    //returns list size

    @Override
    public int getItemCount() {
        return results.size();
    }


    class ViewHolderFJobs extends RecyclerView.ViewHolder {

        // declaring variables
        TextView txtemail, txtname,txtad_id,txtskills,txtexp,txtmainad_id;
        Button ad_details;
        CardView layoutCard;

        ViewHolderFJobs(final View v) {
            super(v);

            txtname = (TextView) v.findViewById(R.id.name);
            txtemail = (TextView) v.findViewById(R.id.email);
            txtemail.setVisibility(View.INVISIBLE);
            txtad_id = (TextView) v.findViewById(R.id.ad_id);
            txtad_id.setVisibility(View.INVISIBLE);
            txtmainad_id =(TextView) v.findViewById(R.id.mainad_id);
            txtmainad_id.setVisibility(View.INVISIBLE);
            txtskills= (TextView) v.findViewById(R.id.skills);
            txtexp = (TextView) v.findViewById(R.id.exp);
            ad_details = (Button) v.findViewById(R.id.details);
            layoutCard = (CardView) v.findViewById(R.id.card);

            /*layoutCard.setOnClickListener(new View.OnClickListener() {

             @Override public void onClick(View v) {

                    String adid =  txtad_id.getText().toString();



                    Intent i = new Intent(v.getContext(),RDetails.class);
                    i.putExtra("adId",adid);
                    v.getContext().startActivity(i);

                }


            });*/
        }
    }
}


