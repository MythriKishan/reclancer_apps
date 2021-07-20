package com.mythri.reclancer_app.Freelancer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mythri.reclancer_app.Model.FMAds;
import com.mythri.reclancer_app.R;

import java.util.List;

public class FManageadsAdapter extends  RecyclerView.Adapter<FManageadsAdapter.ViewHolderFJobs>{


        // declaring variables
        private Context context;
        private List<FMAds> Fjobs;

        //constructor
        FManageadsAdapter(Context context, List<FMAds> F_Jobs) {
            this.context = context;
            this.Fjobs = F_Jobs;
        }

        @Override
        public ViewHolderFJobs onCreateViewHolder(ViewGroup parent, int viewType) {
            //View view = LayoutInflater.from(context).inflate(R.layout.activity_mfjobs,parent,false);

            View view = LayoutInflater.from(context).inflate(R.layout.activity_m_fjobs,parent,false);
            ViewHolderFJobs viewholder = new ViewHolderFJobs(view);

            return viewholder;
        }

        @Override
        public void onBindViewHolder(final ViewHolderFJobs holder, int position) {

            //setting data on each row of list according to position.
            holder.txtid.setText(Fjobs.get(position).getId());
            holder.txtemail.setText(Fjobs.get(position).getEmail());
            holder.txtad_id.setText(Fjobs.get(position).getAd_id());
            holder.txtcat.setText(Fjobs.get(position).getCategory());
           // ViewHolderFJobs.textView.setText(list.get(i));
            holder.edit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    //adid = (TextView)findViewById(R.id.);
                    String id = holder.txtid.getText().toString().trim();
                    String adId = holder.txtad_id.getText().toString().trim();
                    //String cat = holder.txtcat.getText().toString().trim();

                    Intent edit_ad = new Intent(v.getContext(),EditF_ads.class);
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

                    Intent sad = new Intent(v.getContext(),F_Adstatus.class);
                    sad.putExtra("ad_id",adId);
                    sad.putExtra("Id",id);
                    v.getContext().startActivity(sad);



                }


            });

        }

        //returns list size

        @Override
        public int getItemCount() {
            return Fjobs.size();
        }

        public static class ViewHolderFJobs extends RecyclerView.ViewHolder{

            // declaring variables
            TextView txtemail,txtad_id,txtid,txtcat;
            Button edit,adstatus;
            CardView layoutCard;

            ViewHolderFJobs(View v) {
                super(v);

                txtid = (TextView) v.findViewById(R.id.id);
                txtid.setVisibility(View.INVISIBLE);
                txtemail = (TextView) v.findViewById(R.id.email);
                txtad_id = (TextView) v.findViewById(R.id.ad_id);
                txtad_id.setVisibility(View.INVISIBLE);
                txtcat = (TextView) v.findViewById(R.id.category);
                edit = (Button) v.findViewById(R.id.editad);
                adstatus = (Button) v.findViewById(R.id.status);
                layoutCard = (CardView) v.findViewById(R.id.card);

            }
        }


}
