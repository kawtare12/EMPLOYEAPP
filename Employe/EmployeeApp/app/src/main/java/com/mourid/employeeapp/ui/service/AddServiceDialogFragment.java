package com.mourid.employeeapp.ui.service;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.adapters.ServiceAdapter;
import com.mourid.employeeapp.api.RetrofitService;
import com.mourid.employeeapp.api.ServiceApi;
import com.mourid.employeeapp.entities.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceDialogFragment extends DialogFragment {
    private EditText name;
    private Button cancel;
    private Button add;


    private Context parentFragmentContext;

    public AddServiceDialogFragment(Context context) {
        this.parentFragmentContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addservice, container, false);
        name = view.findViewById(R.id.name);
        cancel = view.findViewById(R.id.cancel);
        add = view.findViewById(R.id.ok);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service s = new Service(0L, name.getText().toString());
                addService(s);
                dismiss();
            }
        });

        return view;
    }

    public void addService(Service service) {
        ServiceApi serviceApi = RetrofitService.getClient().create(ServiceApi.class);
        Call<Void> call = serviceApi.addService(service);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("rep",response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}
