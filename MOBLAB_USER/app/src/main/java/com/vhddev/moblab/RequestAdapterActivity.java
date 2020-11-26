package com.vhddev.moblab;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RequestAdapterActivity extends RecyclerView.Adapter<RequestAdapterActivity.RequestViewHolder> {

   private Context mCtx;
   private List<Requests> requestsList;

    public RequestAdapterActivity(Context mCtx, List<Requests> requestsList) {
        this.mCtx = mCtx;
        this.requestsList = requestsList;
    }

    @NonNull
    @Override
    public RequestAdapterActivity.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.tests_lists,null);

        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapterActivity.RequestViewHolder holder, int position) {

        Requests requests = requestsList.get(position);

        holder.tv_docname.setText(requests.getDoc_name());
        holder.tv_date.setText(requests.getTest_date());
        holder.tv_status.setText(requests.getTest_stat());

    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;

        TextView tv_docname,tv_date,tv_status;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            tv_docname = itemView.findViewById(R.id.text_doc_name);
            tv_date = itemView.findViewById(R.id.text_tr_date);
            tv_status = itemView.findViewById(R.id.text_status_name);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            for (int i=0;i<getItemCount();i++)
            {
                if (v == itemView)
                {
                    intent = new Intent(context, RequestDetailsActivity.class);
                    intent.putExtra("tester_name", requestsList.get(getLayoutPosition()).tester_name);
                    intent.putExtra("tester_mob", requestsList.get(getLayoutPosition()).tester_mobile);
                    intent.putExtra("request_date", requestsList.get(getLayoutPosition()).test_date);
                    intent.putExtra("doc_name", requestsList.get(getLayoutPosition()).doc_name);
                    intent.putExtra("p_stat", requestsList.get(getLayoutPosition()).pay_stat);
                    intent.putExtra("stat", requestsList.get(getLayoutPosition()).test_stat);
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }

}
