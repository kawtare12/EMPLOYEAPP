package com.mourid.employeeapp.ui.employee;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.api.RetrofitEmploye;
import com.mourid.employeeapp.api.EmployeApi;
import com.mourid.employeeapp.api.RetrofitService;
import com.mourid.employeeapp.api.ServiceApi;
import com.mourid.employeeapp.entities.Employe;
import com.mourid.employeeapp.entities.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployeDialogFragment extends DialogFragment {
    private Long id;
    private EditText name,prenom;
    private Button update;
    private Button delete;
    private Context parentFragmentContext;
    private Employe employe;

    private Spinner serviceSpinner;

    public UpdateEmployeDialogFragment(Long id,Context context) {
        this.id = id;
        this.parentFragmentContext = context;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updateemploye, container, false);

        name = view.findViewById(R.id.name);
        prenom = view.findViewById(R.id.prenom);
        serviceSpinner = view.findViewById(R.id.serviceSpinner);

        delete = view.findViewById(R.id.delete);
        update = view.findViewById(R.id.update);
        getEmployeAndServices(id);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showConfirmationDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employe.setNom(name.getText().toString());
                employe.setPrenom(prenom.getText().toString());

                updateEmploye(employe,employe.getId());
                dismiss();
            }
        });

        return view;
    }

    public void getEmployeAndServices(long id) {
        EmployeApi employeApi = RetrofitEmploye.getClient().create(EmployeApi.class);
        ServiceApi serviceApi = RetrofitService.getClient().create(ServiceApi.class);

        // Appeler la méthode pour obtenir l'employé
        Call<Employe> employeCall = employeApi.getEmployeById(id);
        employeCall.enqueue(new Callback<Employe>() {
            @Override
            public void onResponse(Call<Employe> call, Response<Employe> response) {
                employe = response.body();
                if (employe != null) {
                    name.setText(employe.getNom());
                    prenom.setText(employe.getPrenom());

                    // Appeler la méthode pour obtenir la liste des services
                    getServices();
                }
            }

            @Override
            public void onFailure(Call<Employe> call, Throwable t) {
                // Gérer l'échec
            }
        });
    }

    public void getServices() {
        ServiceApi serviceApi = RetrofitService.getClient().create(ServiceApi.class);
        Call<List<Service>> serviceCall = serviceApi.getAllServices();

        serviceCall.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                List<Service> services = response.body();

                if (services != null) {
                    // Créer un adaptateur pour le Spinner
                    ArrayAdapter<Service> adapter = new ArrayAdapter<>(parentFragmentContext, android.R.layout.simple_spinner_item, services);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Associer l'adaptateur au Spinner
                    serviceSpinner.setAdapter(adapter);

                    // Sélectionner le service actuel de l'employé (si disponible)
                    if (employe != null && employe.getService() != null) {
                        int position = adapter.getPosition(employe.getService());
                        serviceSpinner.setSelection(position);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                // Gérer l'échec
            }
        });
    }

    public void updateEmploye(Employe employe, Long id) {
        // Obtenez le service sélectionné dans le Spinner
        Service selectedService = (Service) serviceSpinner.getSelectedItem();

        // Assurez-vous que le service n'est pas nul
        if (selectedService != null) {
            employe.setService(selectedService);
        }

        EmployeApi employeApi = RetrofitEmploye.getClient().create(EmployeApi.class);
        Call<Void> call = employeApi.updateEmploye(id, employe);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("UpdateEmploye", "Mise à jour réussie");
                } else {
                    Log.e("UpdateEmploye", "Échec de la mise à jour. Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Gérer l'échec
            }
        });
    }


    public void deletService(Long id){
        EmployeApi employeApi = RetrofitEmploye.getClient().create(EmployeApi.class);
        Call<Void> call = employeApi.deleteEmploye(id);
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
