package com.mythri.reclancer_app.Recruiter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mythri.reclancer_app.Freelancer.FDashRAdapter;
import com.mythri.reclancer_app.Freelancer.int_Rad_details;
import com.mythri.reclancer_app.Model.F_RDashAds;
import com.mythri.reclancer_app.Model.R_FDashAds;
import com.mythri.reclancer_app.R;

import java.util.List;

public class RDashFAdapter extends RecyclerView.Adapter<RDashFAdapter.ViewHolderFJobs> {

    // declaring variables
    private Context context;
    private List<R_FDashAds> results;

    //constructor
    RDashFAdapter(Context context, List<R_FDashAds> R_Jobs) {
        this.context = context;
        this.results = R_Jobs;
    }

    @Override
    public RDashFAdapter.ViewHolderFJobs onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.activity_mfjobs,parent,false);

        View view = LayoutInflater.from(context).inflate(R.layout.activity_f_r_dash, parent, false);
        RDashFAdapter.ViewHolderFJobs viewholder = new RDashFAdapter.ViewHolderFJobs(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final RDashFAdapter.ViewHolderFJobs holder, int position) {

        //setting data on each row of list according to position.
        //results.clear();
        holder.txtname.setText(results.get(position).getName());
        holder.txtemail.setText(results.get(position).getEmail());
        holder.txtmob.setText(results.get(position).getMob());

        holder.txtad_id.setText(results.get(position).getAd_id());
        holder.txtmainad_id.setText(results.get(position).getmainAd_id());

        holder.ad_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //adid = (TextView)findViewById(R.id.);
                //final String token = ((Globals)v.this.getToken();

                String adid = holder.txtad_id.getText().toString().trim();
                String mad_id = holder.txtmainad_id.getText().toString().trim();
                Intent sad = new Intent(v.getContext(), int_F_ad_details.class);
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
        TextView txtemail, txtname,txtmob,txtad_id,txtskills,txtexp,txtmainad_id,txtcat;
        Button ad_details;
        CardView layoutCard;

        ViewHolderFJobs(final View v) {
            super(v);

            txtname = (TextView) v.findViewById(R.id.name);
            txtemail = (TextView) v.findViewById(R.id.email);
            txtmob = (TextView) v.findViewById(R.id.mobile);
            txtad_id = (TextView) v.findViewById(R.id.ad_id);
            txtad_id.setVisibility(View.INVISIBLE);
            txtmainad_id =(TextView) v.findViewById(R.id.mainad_id);
            txtmainad_id.setVisibility(View.INVISIBLE);
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
