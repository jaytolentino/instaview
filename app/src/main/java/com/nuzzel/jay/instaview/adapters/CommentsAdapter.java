package com.nuzzel.jay.instaview.adapters;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nuzzel.jay.instaview.R;

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

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.commentrow, parent, false);
            viewHolder.tvCommentContent = (TextView) convertView.findViewById(R.id.tvCommentContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCommentContent.setText(Html.fromHtml(comment));
        return convertView;
    }

    private static class ViewHolder {
        TextView tvCommentContent;
    }
}
