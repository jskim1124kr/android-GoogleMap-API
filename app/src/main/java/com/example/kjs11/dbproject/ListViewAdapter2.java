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

public class ListViewAdapter2 extends BaseAdapter implements Filterable {
    private ArrayList<ListViewItem2> listViewItemList = new ArrayList<ListViewItem2>();
    private ArrayList<ListViewItem2> filteredItemList = listViewItemList;

    Filter listFilter;

    public ListViewAdapter2() { }


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
            convertView = inflater.inflate(R.layout.item_activity2,parent,false);
        }

        ImageView iconImageView = convertView.findViewById(R.id.imageView1);
        TextView nameTextView = convertView.findViewById(R.id.nameText);
        TextView addrTextView = convertView.findViewById(R.id.addrText);

        ListViewItem2 listViewItem = filteredItemList.get(position);

        iconImageView.setImageDrawable(listViewItem.getIcon());
        nameTextView.setText(listViewItem.getName());
        addrTextView.setText(listViewItem.getAddr());

        return convertView;
    }

    public void addItem(Drawable icon, String name, String addr, String lati, String longti )
    {
        ListViewItem2 item = new ListViewItem2();

        item.setIcon(icon);
        item.setName(name);
        item.setAddr(addr);
        item.setLati(lati);
        item.setLongti(longti);
        item.setKey(name);
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
                ArrayList<ListViewItem2> itemList = new ArrayList<ListViewItem2>();
                for(ListViewItem2 item : listViewItemList) {
                    if (item.getName().toUpperCase().contains(constraint.toString().toUpperCase()) ||
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
            filteredItemList = (ArrayList<ListViewItem2>) results.values;

            if(results.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}


