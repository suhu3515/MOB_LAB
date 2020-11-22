package com.vhddev.moblab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsAdapterActivity extends RecyclerView.Adapter<ResultsAdapterActivity.ResultsViewHolder> {

    private Context mCtx;
    private List<ResultList> resultListList;

    public ResultsAdapterActivity(Context mCtx, List<ResultList> resultListList) {
        this.mCtx = mCtx;
        this.resultListList = resultListList;
    }

    @NonNull
    @Override
    public ResultsAdapterActivity.ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.result_list,null);

        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapterActivity.ResultsViewHolder holder, int position) {

        ResultList resultList = resultListList.get(position);

        holder.textViewTestDate.setText(resultList.getTest_date());
        holder.textViewDocName.setText(resultList.getDoctor_name());

    }

    @Override
    public int getItemCount() {

        return resultListList.size();
    }

    class ResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final Context context;

        TextView textViewDocName,textViewTestDate;

        public ResultsViewHolder(View itemView)
        {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            textViewDocName = itemView.findViewById(R.id.text_doc_name);
            textViewTestDate = itemView.findViewById(R.id.text_tr_date);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            for (int i=0;i<getItemCount();i++)
            {
                if (v == itemView)
                {
                    intent = new Intent(context, ResultDetailsActivity.class);
                    intent.putExtra("doc_name", resultListList.get(getLayoutPosition()).doctor_name);
                    intent.putExtra("test_date", resultListList.get(getLayoutPosition()).test_date);
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }

}
