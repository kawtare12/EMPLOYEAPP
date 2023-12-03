package com.mourid.employeeapp.ui.employee;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.api.EmployeApi;
import com.mourid.employeeapp.api.RetrofitEmploye;
import com.mourid.employeeapp.api.RetrofitService;
import com.mourid.employeeapp.api.ServiceApi;
import com.mourid.employeeapp.entities.Employe;
import com.mourid.employeeapp.entities.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeDialogFragment extends DialogFragment {

    private EditText etNom, etPrenom;
    private Spinner serviceSpinner;

    private ServiceApi serviceApi;
    private EmployeApi employeApi;
    private DatePicker datePicker;
    // Obtenez la date depuis le DatePicker


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_employee_dialog, null);

        etNom = view.findViewById(R.id.editTextNom);
        etPrenom = view.findViewById(R.id.editTextPrenom);
        serviceSpinner = view.findViewById(R.id.serviceSpinner);
        datePicker = view.findViewById(R.id.datePicker);

        // Initialize ServiceApi using Retrofit
        serviceApi = RetrofitService.getClient().create(ServiceApi.class);
        employeApi = RetrofitService.getClient().create(EmployeApi.class);


        // Fetch services from the API and populate the Spinner
        fetchServices();





        builder.setView(view)
                .setTitle("Ajouter un employé")
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addEmployee();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Annulation
                    }
                });
        return builder.create();
    }

    private void addEmployee() {
        String nom = etNom.getText().toString();
        String prenom = etPrenom.getText().toString();

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        // Utilisez Calendar pour construire une date
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        // Formattez la date dans le format attendu par votre API backend
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String dateNaissanceStr = dateFormat.format(calendar.getTime());


        Employe newEmployee = new Employe();
        newEmployee.setNom(nom);
        newEmployee.setPrenom(prenom);
        newEmployee.setDateNaissance(dateNaissanceStr);


        Service selectedService = (Service) serviceSpinner.getSelectedItem();
        newEmployee.setService(selectedService);


        // Appelle la méthode pour ajouter l'employé via Retrofit
        addEmployeeViaRetrofit(newEmployee);


    }

    private void fetchServices() {
        Call<List<Service>> call = serviceApi.getAllServices();

        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Service> services = response.body();
                    populateServiceSpinner(services);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                // Handle failure
            }
        });
    }
    private void populateServiceSpinner(List<Service> services) {
        ArrayAdapter<Service> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                services
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);
    }
    public void addEmploye(Employe employe) {
        Log.d("AddEmployeDialog", "Adding employe: " + employe.toString());

        Call<Void> call = employeApi.addEmploye(employe);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("AddEmployeDialog", "Employe added successfully");
                } else {
                    Log.d("AddEmployeDialog", "Failed to add employe. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddEmployeDialog", "Error adding employe", t);
            }
        });
    }


    private void addEmployeeViaRetrofit(Employe employee) {
        EmployeApi employeeApi = RetrofitEmploye.getClient().create(EmployeApi.class);
        Call<Void> call = employeeApi.addEmploye(employee);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Succès
                    // Rafraîchir la liste des employés après l'ajout
                } else {
                    // Erreur
                    Log.e("AddEmployeeDialog", "Erreur lors de la réponse : " + response.code());
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error body is null";
                        Log.e("AddEmployeeDialog", "Erreur détaillée : " + errorBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Échec de la requête
                Log.e("AddEmployeeDialog", "Échec de la requête : " + t.getMessage());
     }
        });
}
}
