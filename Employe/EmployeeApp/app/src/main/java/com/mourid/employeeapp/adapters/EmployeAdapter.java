package com.mourid.employeeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.entities.Employe;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EmployeAdapter extends BaseAdapter {
    private List<Employe> employees;
    private LayoutInflater inflater;

    public EmployeAdapter(List<Employe> employees, Context context) {
        this.employees = employees;
        this.inflater = LayoutInflater.from(context);
    }
    public void updateEmployeList(List<Employe> newEmployees) {
        employees.clear();
        employees.addAll(newEmployees);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employees.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.employee_item, parent, false);

            holder = new ViewHolder();
            holder.nom = convertView.findViewById(R.id.nom);
            holder.id = convertView.findViewById(R.id.id);
            holder.prenom = convertView.findViewById(R.id.prenom);
            holder.service = convertView.findViewById(R.id.service);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Vérification pour éviter les NullPointerException
        if (holder.nom != null) {
            holder.nom.setText(employees.get(position).getNom());
        }
        if (holder.prenom != null) {
            holder.prenom.setText(employees.get(position).getPrenom());
        }
        if (holder.service != null && employees.get(position).getService() != null) {
            holder.service.setText(employees.get(position).getService().getNom() + "");
        }
        if (holder.id != null && employees.get(position).getId() != null) {
            holder.id.setText(String.valueOf(employees.get(position).getId()));
        }

        return convertView;
}
    private static class ViewHolder {
        TextView nom;
        TextView id;
        TextView prenom;
        TextView service;
}



}
