package com.example.vt6002cem_assignment2.order_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vt6002cem_assignment2.R;
import com.example.vt6002cem_assignment2.shoppingcart.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> orderdate;
    private List<String> orderAmount;
    private ArrayList<Record> datedetail;


    public RecordAdapter(Context context, List<String> orderdate, List<String> orderAmount, ArrayList<Record> productListorder2){
        this.context = context;
        this.orderdate = orderdate;
        this.orderAmount = orderAmount;
        this.datedetail = productListorder2;
    }

    @Override
    public int getGroupCount() {
        return this.orderdate.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int A=0;
        for(int i = 0; i<datedetail.size();i++){
            if(this.orderdate.get(groupPosition)==datedetail.get(i).getDatekey()){
                A=i;
            }
        }
        return this.datedetail.get(A).order.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.orderdate.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        int A=0;
        for(int i = 0; i<datedetail.size();i++){
            if(this.orderdate.get(groupPosition)==datedetail.get(i).getDatekey()){
                A=i;
            }
        }
        return this.datedetail.get(A).order.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        String listTitle2 = (String) orderAmount.get(groupPosition);
        System.out.println(groupPosition);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_group_card_design,null);
        }

        TextView listTitleTextView = convertView.findViewById(R.id.txt_order_date);
        TextView total = convertView.findViewById(R.id.txt_order_price);
        total.setText("$"+listTitle2);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Order productdetail = (Order) getChild(groupPosition,childPosition);
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_listview_card_design,null);

        }
        TextView productname,productprice,quantity,size,floor,orderid;
        ImageView img;
        productname=convertView.findViewById(R.id.txt_order_name);
        productprice=convertView.findViewById(R.id.txt_order_itemprice);
        quantity=convertView.findViewById(R.id.txt_order_quantity);
        size=convertView.findViewById(R.id.txt_order_size);
        img=convertView.findViewById(R.id.img_order_image);
        orderid=convertView.findViewById(R.id.txt_order_id);


        productname.setText(productdetail.getProductname());
        productprice.setText("$"+productdetail.getProductprice());
        quantity.setText(productdetail.getQuantity());
        size.setText(productdetail.getSize());
        orderid.setText(productdetail.getDatekey());
        Picasso.with(context).load(productdetail.getImg()).into(img);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
