package com.google.sites.lostandfoundapp.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.sites.lostandfoundapp.R;
import com.google.sites.lostandfoundapp.database.entities.ImageEntity;

/**
 * Created by Isaac on 3/7/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private List<String> _listDataHeader;
    private Map<String, List<ImageEntity>> _listDataChild;

    public ExpandableListAdapter(final Context context, final List<String> listDataHeader,
                                 final Map<String, List<ImageEntity>> listDataChild) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    public void removeChild(int groupPosition, int childPosition) {
        this._listDataChild.get(this._listDataHeader.get(groupPosition)).remove(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        final ImageEntity imageEntity = (ImageEntity) getChild(groupPosition, childPosition);

        if (convertView == null) {
            final LayoutInflater layoutInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.locate_list_item_layout, null);
        }

        final ImageView ivItemImage = convertView.findViewById(R.id.locate_exp_list_item_image);
        final TextView txtItemLocation = convertView.findViewById(R.id.locate_exp_list_item_location);
        final TextView txtItemDescription = convertView.findViewById(R.id.locate_exp_list_item_description);

        final byte[] byteArray = stringOfByteArrayToByteArray(imageEntity.getImageBytes());
        final Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ivItemImage.setImageBitmap(bitmap);
        txtItemLocation.setText(imageEntity.getLocation());
        txtItemDescription.setText(imageEntity.getDescription());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.locate_list_group_layout, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private byte[] stringOfByteArrayToByteArray(final String byteString) {
        final String[] byteValues = byteString.substring(1, byteString.length() - 1).split(",");
        final byte[] bytes = new byte[byteValues.length];

        for (int i = 0; i < bytes.length; i++) {
            if (byteValues[i].trim() == "") {
                System.out.print("");
            }
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        return bytes;
    }
}
