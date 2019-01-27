package com.example.photoalbum.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.photoalbum.R;
import com.example.photoalbum.activities.AlbumsActivity;
import com.example.photoalbum.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.photoalbum.util.General.INTENT_EXTRA_USER;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private ArrayList<User> usersDataSet;

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        UserViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        CardView getCardView() {
            return cardView;
        }
    }

    public UsersAdapter(ArrayList<User> usersDataSet) {
        this.usersDataSet = usersDataSet;
    }

    @NonNull
    @Override
    public UsersAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_user, parent, false);
        return new UserViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
        final User user = usersDataSet.get(position);
        final CardView cardView = holder.getCardView();

        TextView usernameTextView = cardView.findViewById(R.id.recyclerview_item_user_username);
        usernameTextView.setText(user.getUsername());

        TextView nameTextView = cardView.findViewById(R.id.recyclerview_item_user_name);
        nameTextView.setText(user.getName());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbumsActivityForClickedUserCardView();
            }

            private void openAlbumsActivityForClickedUserCardView() {
                Context context = cardView.getContext();
                Intent goToAlbumsIntent = new Intent(context, AlbumsActivity.class);
                goToAlbumsIntent.putExtra(INTENT_EXTRA_USER, new Gson().toJson(user));
                context.startActivity(goToAlbumsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersDataSet.size();
    }

    public void setDataSet(ArrayList<User> users) {
        this.usersDataSet = users;
    }
}
