package com.nuzzel.jay.instaview;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.util.ArrayList;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photorow, parent, false);
        }
        return populatePhotoView(currentPhoto, convertView);
    }

    private View populatePhotoView(Photo photo, View convertView) {
        convertView = addCaption(photo, convertView);
        convertView = addPhotoImage(photo, convertView);
        convertView = addUsername(photo, convertView);
        convertView = addProfileImage(photo, convertView);
        convertView = addLikes(photo, convertView);
        convertView = addLastComment(photo.lastComment, convertView);
        convertView = addViewComments(photo, convertView);
        return convertView;
    }

    private View addCaption(Photo photo, View convertView) {
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        if (photo.caption != null) {
            String fullCaption = "<strong><font color='navy'>" + photo.author.username + "</font></strong> " + photo.caption;
            tvCaption.setText(Html.fromHtml(fullCaption));
        } else {
            tvCaption.refreshDrawableState();
        }
        return convertView;
    }

    private View addPhotoImage(Photo photo, View convertView) {
        FullImageView imgPhoto = (FullImageView) convertView.findViewById(R.id.imgPhoto);
        imgPhoto.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.imgUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(imgPhoto);
        return convertView;
    }

    private View addUsername(Photo photo, View convertView) {
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        String coloredUsername = "<strong><font color='navy'>" + photo.author.username + "</font></strong></strong>";
        tvUsername.setText(Html.fromHtml(coloredUsername));
        return convertView;
    }

    private View addProfileImage(Photo photo, View convertView) {
        RoundedImageView ivUserProfilePicture = (RoundedImageView) convertView.findViewById(R.id.ivUserProfilePicture);
        ivUserProfilePicture.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.author.profileImgUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(ivUserProfilePicture);
        return convertView;
    }

    private View addLikes(Photo photo, View convertView) {
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        String fullLikes = "<strong><font color='navy'>" + photo.likesCount + " likes</font></strong>";
        tvLikes.setText(Html.fromHtml(fullLikes));
        return convertView;
    }

    private View addLastComment(Comment comment, View convertView) {
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment1);
        String fullComment = "<strong><font color='navy'>" + comment.author.username + "</font></strong> "
                + comment.content;
        tvComment.setText(Html.fromHtml(fullComment));
        return convertView;
    }

    private View addViewComments(Photo photo, View convertView) {
        TextView viewComments = (TextView) convertView.findViewById(R.id.viewComments);
        String fullViewComment = "<font color='gray'>view all " + photo.commentCount + " comments</gray>";
        viewComments.setText(Html.fromHtml(fullViewComment));
        setupClickListener(viewComments, photo.mediaId);
        return convertView;
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

}
