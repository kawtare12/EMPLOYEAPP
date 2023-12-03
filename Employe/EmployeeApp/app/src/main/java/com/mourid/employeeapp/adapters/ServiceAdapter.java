package com.mourid.employeeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.entities.Service;

import java.util.List;

public class ServiceAdapter extends BaseAdapter {
    private List<Service> services;
    private LayoutInflater inflater;

    public ServiceAdapter(List<Service> services, Context context) {
        this.services = services;
        this.inflater = LayoutInflater.from(context);
    }
    public void updateServiceList(List<Service> newEmployees) {
        services.clear();
        services.addAll(newEmployees);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return services.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.service_item, null);
        TextView name = convertView.findViewById(R.id.name);
        TextView id = convertView.findViewById(R.id.id);
        name.setText(services.get(position).getNom());
        id.setText(services.get(position).getId().toString());
        return convertView;
    }
}
