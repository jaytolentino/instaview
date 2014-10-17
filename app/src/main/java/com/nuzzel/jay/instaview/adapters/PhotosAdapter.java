package com.nuzzel.jay.instaview.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.nuzzel.jay.instaview.R;
import com.nuzzel.jay.instaview.activities.ViewCommentsActivity;
import com.nuzzel.jay.instaview.models.Comment;
import com.nuzzel.jay.instaview.models.Photo;
import com.nuzzel.jay.instaview.views.FullImageView;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by jay on 10/2/14.
 */
public class PhotosAdapter extends ArrayAdapter<Photo>{

    public PhotosAdapter(Context context, List<Photo>photos) {
        super(context, R.layout.photorow, photos);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Photo currentPhoto = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.photorow, parent, false);
            setViews(viewHolder, convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        populatePhotoView(currentPhoto, viewHolder);
        return convertView;
    }

    private void setViews(ViewHolder viewHolder, View convertView) {
        viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        viewHolder.imgPhoto = (FullImageView) convertView.findViewById(R.id.imgPhoto);
        viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        viewHolder.ivUserProfilePicture = (RoundedImageView)
                convertView.findViewById(R.id.ivUserProfilePicture);
        viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment1);
        viewHolder.viewComments = (TextView) convertView.findViewById(R.id.viewComments);
    }

    private void populatePhotoView(Photo photo, ViewHolder viewHolder) {
        addCaption(photo, viewHolder);
        addPhotoImage(photo, viewHolder);
        addUsername(photo, viewHolder);
        addProfileImage(photo, viewHolder);
        addLikes(photo, viewHolder);
        addLastComment(photo.getLastComment(), viewHolder);
        addViewComments(photo, viewHolder);
    }

    private void addCaption(Photo photo, ViewHolder viewHolder) {
        if (photo.getCaption() != null) {
            String fullCaption = "<strong><font color='navy'>" + photo.getAuthor().username + "</font></strong> " + photo.getCaption();
            viewHolder.tvCaption.setText(Html.fromHtml(fullCaption));
        }
        else {
            viewHolder.tvCaption.refreshDrawableState();
        }
    }

    private void addPhotoImage(Photo photo, ViewHolder viewHolder) {
        viewHolder.imgPhoto.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.getImgUrl())
                .placeholder(R.drawable.ic_launcher)
                .into(viewHolder.imgPhoto);
    }

    private void addUsername(Photo photo, ViewHolder viewHolder) {
        String coloredUsername = "<strong><font color='navy'>" + photo.getAuthor().username + "</font></strong></strong>";
        viewHolder.tvUsername.setText(Html.fromHtml(coloredUsername));
    }

    private void addProfileImage(Photo photo, ViewHolder viewHolder) {
        viewHolder.ivUserProfilePicture.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.getAuthor().profileImgUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(viewHolder.ivUserProfilePicture);
    }

    private void addLikes(Photo photo, ViewHolder viewHolder) {
        String fullLikes = "<strong><font color='navy'>" + photo.getLikesCount() + " likes</font></strong>";
        viewHolder.tvLikes.setText(Html.fromHtml(fullLikes));
    }

    private void addLastComment(Comment comment, ViewHolder viewHolder) {
        String fullComment = "<strong><font color='navy'>" + comment.author.username + "</font></strong> "
                + comment.content;
        viewHolder.tvComment.setText(Html.fromHtml(fullComment));
    }

    private void addViewComments(Photo photo, ViewHolder viewHolder) {
        String fullViewComment = "<font color='gray'>view all " + photo.getCommentCount() + " comments</gray>";
        viewHolder.viewComments.setText(Html.fromHtml(fullViewComment));
        setupClickListener(viewHolder.viewComments, photo.getMediaId());
    }

    private void setupClickListener(TextView viewCommentsButton, final String mediaId) {
        viewCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewCommentsActivity = new Intent(getContext(), ViewCommentsActivity.class);
                viewCommentsActivity.putExtra("mediaId", mediaId);
                getContext().startActivity(viewCommentsActivity);
            }
        });
    }

    private static class ViewHolder {
        TextView tvCaption;
        FullImageView imgPhoto;
        TextView tvUsername;
        RoundedImageView ivUserProfilePicture;
        TextView tvLikes;
        TextView tvComment;
        TextView viewComments;
    }
}
