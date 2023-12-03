package com.mourid.employeeapp.ui.service;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.adapters.ServiceAdapter;
import com.mourid.employeeapp.api.RetrofitService;
import com.mourid.employeeapp.api.ServiceApi;
import com.mourid.employeeapp.databinding.FragmentServiceBinding;
import com.mourid.employeeapp.entities.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServiceFragment extends Fragment {

    private FragmentServiceBinding binding;
    private Button add;
    private List<Service> services = new ArrayList<>();
    private ListView listView;
    private ServiceAdapter serviceAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ServiceViewModel serviceViewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
        binding = FragmentServiceBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        listView = binding.listservices;
        serviceAdapter = new ServiceAdapter(services,getContext());
        getServices();
        add = binding.add;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddServiceDialogFragment dialogFragment = new AddServiceDialogFragment(getContext());
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "AddEmployeeDialog");
            }
        });
        swipeRefreshLayout = binding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Trigger refresh when the user swipes
                getServices();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id = view.findViewById(R.id.id);
                UpdateServiceDialogFragment dialogFragment = new UpdateServiceDialogFragment(Long.parseLong(id.getText().toString()),getContext());
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "UpdateServiceDialog");
            }
        });

        return root;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getServices(){
       ServiceApi serviceApi = RetrofitService.getClient().create(ServiceApi.class);
       Call<List<Service>> call =serviceApi.getAllServices();
       call.enqueue(new Callback<List<Service>>() {
           @Override
           public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
               services = response.body();
               serviceAdapter.updateServiceList(services);
               listView.setAdapter(serviceAdapter);
               serviceAdapter.notifyDataSetChanged();
               swipeRefreshLayout.post(new Runnable() {
                   @Override
                   public void run() {
                       swipeRefreshLayout.setRefreshing(false);
                   }
               });
           }

           @Override
           public void onFailure(Call<List<Service>> call, Throwable t) {

           }
       });
    }
}