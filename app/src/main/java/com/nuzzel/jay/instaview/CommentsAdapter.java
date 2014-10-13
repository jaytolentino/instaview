package com.nuzzel.jay.instaview;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jay on 10/6/14.
 */
public class CommentsAdapter extends ArrayAdapter<String> {
    public CommentsAdapter(Context context, ArrayList<String> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String comment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.commentrow, parent, false);
        }

        TextView tvCommentContent = (TextView) convertView.findViewById(R.id.tvCommentContent);
        tvCommentContent.setText(Html.fromHtml(comment));
        return convertView;
    }
}
