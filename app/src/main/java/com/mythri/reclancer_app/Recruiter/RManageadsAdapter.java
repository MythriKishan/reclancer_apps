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

import com.mythri.reclancer_app.Model.RMAds;
import com.mythri.reclancer_app.R;

import org.w3c.dom.Text;

import java.util.List;

public class RManageadsAdapter extends  RecyclerView.Adapter<RManageadsAdapter.ViewHolderFJobs> {


    // declaring variables
    private Context context;
    private List<RMAds> Rjobs;

    //constructor
    RManageadsAdapter(Context context, List<RMAds> R_Jobs) {
        this.context = context;
        this.Rjobs = R_Jobs;
    }

    @Override
    public RManageadsAdapter.ViewHolderFJobs onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(context).inflate(R.layout.activity_mfjobs,parent,false);

        View view = LayoutInflater.from(context).inflate(R.layout.activity_r_mjobs, parent, false);
        RManageadsAdapter.ViewHolderFJobs viewholder = new RManageadsAdapter.ViewHolderFJobs(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(final RManageadsAdapter.ViewHolderFJobs holder, int position) {

        //setting data on each row of list according to position.
        holder.txtcat.setText(Rjobs.get(position).getCategory());
        holder.txtname.setText(Rjobs.get(position).getName());
        holder.txtskills.setText(Rjobs.get(position).getSkills());
        holder.txtexp.setText(Rjobs.get(position).getExp());
        holder.txtid.setText(Rjobs.get(position).getId());
        holder.txtemail.setText(Rjobs.get(position).getEmail());
        holder.txtad_id.setText(Rjobs.get(position).getAd_id());

        holder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //adid = (TextView)findViewById(R.id.);
                String id = holder.txtid.getText().toString().trim();
                String adId = holder.txtad_id.getText().toString().trim();

                Intent edit_ad = new Intent(v.getContext(), EditR_ads.class);
                edit_ad.putExtra("ad_id",adId);
                edit_ad.putExtra("Id",id);
                v.getContext().startActivity(edit_ad);

            }
        });

        holder.adstatus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //adid = (TextView)findViewById(R.id.);
                String id = holder.txtid.getText().toString().trim();
                String adId = holder.txtad_id.getText().toString().trim();

                Intent sad = new Intent(v.getContext(), R_Adstatus.class);
                sad.putExtra("ad_id",adId);
                sad.putExtra("Id",id);
                v.getContext().startActivity(sad);



            }


        });


    }

    //returns list size

    @Override
    public int getItemCount() {
        return Rjobs.size();
    }

    class ViewHolderFJobs extends RecyclerView.ViewHolder {

        // declaring variables
        TextView txtemail, txtad_id,txtid,txtname,txtskills,txtexp,txtcat;
        Button edit,adstatus;
        CardView layoutCard;

        ViewHolderFJobs(View v) {
            super(v);

            txtname = (TextView) v.findViewById(R.id.name);
            txtcat =(TextView) v.findViewById(R.id.category);
            txtskills = (TextView) v.findViewById(R.id.skills);
            txtexp = (TextView) v.findViewById(R.id.exp);
            txtid = (TextView) v.findViewById(R.id.id);
            txtid.setVisibility(View.INVISIBLE);
            txtemail = (TextView) v.findViewById(R.id.email);
            txtemail.setVisibility(View.INVISIBLE);
            txtad_id = (TextView) v.findViewById(R.id.ad_id);
            txtad_id.setVisibility(View.INVISIBLE);
            edit = (Button) v.findViewById(R.id.editad);
            adstatus = (Button) v.findViewById(R.id.status);
            layoutCard = (CardView) v.findViewById(R.id.card);
        }
    }
}