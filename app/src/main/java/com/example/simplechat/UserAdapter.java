package com.example.simplechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    /**
     This is a context object that holds information about the environment
     in which your code is executing. It can be used to access resources and services,
     such as the system services, and to share data between different components of your application."
     */
    private Context context;

    private List<UserModel> userModelList;

    /**
     Constructor for the UserAdapter class.
     @param context The context of the activity using the adapter.
     */
    public UserAdapter(Context context) {
        this.context = context;
        userModelList = new ArrayList<>();
    }

    /**
     Method to add a new user to the userModelList.
     @param userModel The user model to be added to the list.
     */
    public void add (UserModel userModel){
        userModelList.add(userModel);
        notifyDataSetChanged();
    }

    /**
     Method to clear the userModelList.
     */
    public void clear (){
        userModelList.clear();
        notifyDataSetChanged();
    }


    /**
     Called when RecyclerView needs a new {@link MyViewHolder} of the given type to represent
     an item.
     @param parent The ViewGroup into which the new View will be added after it is bound to
     --> an adapter position.
     @param viewType The view type of the new View.
     @return A new MyViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewHolder(view);
    }

    /**
     Called by RecyclerView to display the data at the specified position. This method should
     update the contents of the {@link MyViewHolder#itemView} to reflect the item at the given
     position.
     @param holder The MyViewHolder which should be updated to represent the contents of the
     --> item at the given position in the data set.
     @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel userModel = userModelList.get(position);
        holder.name.setText(userModel.getUserName());
        holder.email.setText(userModel.getUserEmail());
    }

    /**
     Returns the total number of items in the data set held by the adapter.
     @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    /**
     MyViewHolder class for the UserAdapter.
     This class is used to hold the views for each row of the recycler view.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name,email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
        }
    }
}
