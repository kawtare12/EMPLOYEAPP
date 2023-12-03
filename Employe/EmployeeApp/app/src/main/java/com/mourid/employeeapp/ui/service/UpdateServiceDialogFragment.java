package com.mourid.employeeapp.ui.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.api.RetrofitService;
import com.mourid.employeeapp.api.ServiceApi;
import com.mourid.employeeapp.entities.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateServiceDialogFragment extends DialogFragment {
    private Long id;
    private EditText name;
    private Button update;
    private Button delete;
    private Context parentFragmentContext;
    private Service service;

    public UpdateServiceDialogFragment(Long id,Context context) {
        this.id = id;
        this.parentFragmentContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updateservice, container, false);

        name = view.findViewById(R.id.name);
        delete = view.findViewById(R.id.delete);
        update = view.findViewById(R.id.update);
        getService(id);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showConfirmationDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.setNom(name.getText().toString());
                updateService(service,service.getId());
                dismiss();
            }
        });

        return view;
    }

    public void getService(long id){
        ServiceApi serviceApi = RetrofitService.getClient().create(ServiceApi.class);
        Call<Service> call = serviceApi.getServiceById(id);
        call.enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                service = response.body();
                if(service != null)
                    name.setText(service.getNom().toString());
            }

            @Override
            public void onFailure(Call<Service> call, Throwable t) {

            }
        });

    }
    public void updateService(Service service,Long id) {
        ServiceApi serviceApi = RetrofitService.getClient().create(ServiceApi.class);
        Call<Void> call = serviceApi.updateService(id,service);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void deletService(Long id){
        ServiceApi serviceApi = RetrofitService.getClient().create(ServiceApi.class);
        Call<Void> call = serviceApi.deleteService(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletService(id);
                dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        builder.create().show();
    }

}
