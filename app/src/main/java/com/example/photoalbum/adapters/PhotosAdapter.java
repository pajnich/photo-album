package com.example.photoalbum.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.photoalbum.R;
import com.example.photoalbum.activities.PhotosActivity;
import com.example.photoalbum.models.Photo;
import com.example.photoalbum.models.User;
import com.google.gson.Gson;

import static com.example.photoalbum.util.General.INTENT_EXTRA_USER;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {
    private User user;
    private final RequestOptions options;

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        PhotoViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        CardView getCardView() {
            return cardView;
        }
    }

    public PhotosAdapter(User user) {
        this.user = user;
        options = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_launcher_foreground);
    }

    @NonNull
    @Override
    public PhotosAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_photo, parent, false);
        return new PhotosAdapter.PhotoViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotosAdapter.PhotoViewHolder holder, final int position) {
        final Photo photo = user.getActiveAlbum().getPhoto(position);
        final CardView cardView = holder.getCardView();
        final Context context = cardView.getContext();

        TextView titleTextView = cardView.findViewById(R.id.recyclerview_item_photo_title);
        titleTextView.setText(photo.getTitle());

        ImageView imageView = cardView.findViewById(R.id.recyclerview_item_photo_image);

        Glide.with(context).load(photo.getThumbnailUrl()).apply(options).into(imageView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhotosActivityForClickedAlbumCardView();
            }

            private void openPhotosActivityForClickedAlbumCardView() {
                Intent goToPhotosIntent = new Intent(context, PhotosActivity.class);
                goToPhotosIntent.putExtra(INTENT_EXTRA_USER, new Gson().toJson(user));
                context.startActivity(goToPhotosIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return user.getActiveAlbum().getPhotos().size();
    }

    public void setDataSet(User user) {
        this.user = user;
    }
}
