package com.vhddev.moblabtester;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder>
{

    private Context mCtx;
    private List<UsersList> usersLists;

    public UsersListAdapter(Context mCtx, List<UsersList> usersLists)
    {
        this.mCtx = mCtx;
        this.usersLists = usersLists;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.task_list, null );

        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        UsersList usersList = usersLists.get(position);

        holder.textViewUserName.setText(usersList.getUserName());
        holder.textViewUserDob.setText(usersList.getUserDob());

    }

    @Override
    public int getItemCount() {
        return usersLists.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private final Context context;

        TextView textViewUserName, textViewUserDob;


        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            textViewUserName = itemView.findViewById(R.id.text_username);
            textViewUserDob = itemView.findViewById(R.id.text_userdob);
            //btnViewTests = itemView.findViewById(R.id.btn_view_tasks);

        }

        @Override
        public void onClick(View v) {

            int req_user_id;

            Intent intent = new Intent();

            for (int i=0;i<getItemCount();i++)
            {

                if (v==itemView)
                {
                    req_user_id = usersLists.get(getLayoutPosition()).getUserId();
                    intent = new Intent(context, TestsActivity.class);
                    intent.putExtra("userid", String.valueOf(req_user_id));
                    intent.putExtra("user_name",usersLists.get(getLayoutPosition()).getUserName());
                    intent.putExtra("user_dob",usersLists.get(getLayoutPosition()).getUserDob());
                    intent.putExtra("user_loc",usersLists.get(getLayoutPosition()).getUserLoc());
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }
}