package com.vhddev.moblabtester;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestsListAdapter extends RecyclerView.Adapter<TestsListAdapter.TestsViewHolder>
{

    private Context mCtx;
    private List<Tests> testsList;

    public TestsListAdapter(Context mCtx, List<Tests> testsList) {
        this.mCtx = mCtx;
        this.testsList = testsList;
    }

    @NonNull
    @Override
    public TestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.test_list, null);

        return new TestsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TestsListAdapter.TestsViewHolder holder, int position) {

        Tests tests = testsList.get(position);

        holder.textViewTestName.setText(tests.getTestName());
        holder.textViewTestSpecimen.setText(tests.getTestSpecimen());


    }

    @Override
    public int getItemCount() {

        return testsList.size();
    }

    class TestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final Context context;

        TextView textViewTestName, textViewTestSpecimen,textViewUserName, textViewUserDob;

        public TestsViewHolder(View itemView)
        {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            textViewTestName = itemView.findViewById(R.id.text_testname);
            textViewTestSpecimen = itemView.findViewById(R.id.text_specimen);
            textViewUserName = itemView.findViewById(R.id.text_username);



        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            for (int i=0;i<getItemCount();i++)
            {
                if (v==itemView)
                {
                    intent = new Intent(context, TestDetailsActivity.class);
                    context.startActivity(intent);
                    break;
                }
            }

        }
    }

}
