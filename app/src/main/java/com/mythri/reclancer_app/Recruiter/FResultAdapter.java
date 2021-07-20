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

import com.mythri.reclancer_app.Freelancer.RDetails;
import com.mythri.reclancer_app.Model.FResultAds;
import com.mythri.reclancer_app.R;

import java.util.List;

public class FResultAdapter extends RecyclerView.Adapter<FResultAdapter.ViewHolderFJobs> {


    // declaring variables
    private Context context;
    private List<FResultAds> results;

    //constructor
    FResultAdapter(Context context, List<FResultAds> R_Jobs) {
        this.context = context;
        this.results = R_Jobs;
    }


    @Override
    public FResultAdapter.ViewHolderFJobs onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.activity_mfjobs,parent,false);

        View view = LayoutInflater.from(context).inflate(R.layout.activity_free_results, parent, false);
        FResultAdapter.ViewHolderFJobs viewholder = new FResultAdapter.ViewHolderFJobs(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final FResultAdapter.ViewHolderFJobs holder, int position) {

        //setting data on each row of list according to position.

        holder.txtname.setText(results.get(position).getName());
        holder.txtad_id.setText(results.get(position).getAd_id());
        holder.txtskills.setText(results.get(position).getSkills());
        holder.txtexp.setText(results.get(position).getExp());
        holder.ad_details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //adid = (TextView)findViewById(R.id.);
                String adid = holder.txtad_id.getText().toString().trim();
                Intent sad = new Intent(v.getContext(), RDetails.class);
                sad.putExtra("adId",adid);
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
        TextView txtname, txtad_id,txtskills,txtexp;
        Button ad_details;
        CardView layoutCard;

        ViewHolderFJobs(View v) {
            super(v);

            txtname = (TextView) v.findViewById(R.id.name);
            txtad_id = (TextView) v.findViewById(R.id.ad_id);
            txtskills= (TextView) v.findViewById(R.id.skills);
            txtexp = (TextView) v.findViewById(R.id.exp);
            ad_details = (Button) v.findViewById(R.id.details);
            layoutCard = (CardView) v.findViewById(R.id.card);

            /*layoutCard.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    String adid =  txtad_id.getText().toString();

                    Snackbar.make(v, "Click detected Ad_Id " + adid,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Intent i = new Intent(v.getContext(), RDetails.class);
                    i.putExtra("adId",adid);
                    v.getContext().startActivity(i);

                }


            });*/
        }
    }
}
