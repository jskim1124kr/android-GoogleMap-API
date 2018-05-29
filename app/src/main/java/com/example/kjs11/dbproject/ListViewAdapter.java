package com.example.kjs11.dbproject;

/**
 * Created by kjs11 on 2017-11-23.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements Filterable {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();
    private ArrayList<ListViewItem> filteredItemList = listViewItemList;

    Filter listFilter;

    public ListViewAdapter() { }


    @Override
    public int getCount() { return filteredItemList.size(); }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context  context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_activity,parent,false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.imageView1);
        TextView placeTextView = convertView.findViewById(R.id.placeText);
        TextView addrTextView = convertView.findViewById(R.id.addrText);
        TextView telTextView = convertView.findViewById(R.id.telText);

        ListViewItem listViewItem = filteredItemList.get(position);

        iconImageView.setImageDrawable(listViewItem.getIcon());
        placeTextView.setText(listViewItem.getPlace());
        addrTextView.setText(listViewItem.getAddr());
        telTextView.setText(listViewItem.getTel());

        return convertView;
    }

    public void addItem(Drawable icon, String place, String addr, String tel, String lati, String longti)
    {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setPlace(place);
        item.setAddr(addr);
        item.setTel(tel);
        item.setLati(lati);
        item.setLongti(longti);
        listViewItemList.add(item);
    }
//    public void onClick(View v)
//    {
//        int position = (Integer) v.getTag();
//
//        ListViewItem item = (ListViewItem) getItem(position);
//
//        Bundle extras = new Bundle();
//        extras.putDouble("lati",item.getLati());
//        extras.putDouble("longti",item.getLongti());
//
//        Intent intent = new Intent(this, MapActivity.class);
//
//
//    }


    @Override
    public Filter getFilter() {
        if(listFilter == null)
        {
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0) {
                results.values = listViewItemList;
                results.count = listViewItemList.size();

            } else {
                ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
                for(ListViewItem item : listViewItemList) {
                    if (item.getPlace().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            item.getAddr().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        itemList.add(item);
                    }
                }
                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItemList = (ArrayList<ListViewItem>) results.values;

            if(results.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}


