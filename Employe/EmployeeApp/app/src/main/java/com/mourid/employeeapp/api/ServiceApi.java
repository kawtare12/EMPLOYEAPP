package com.mourid.employeeapp.api;
import com.mourid.employeeapp.entities.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ServiceApi {


    @GET("all")
    Call<List<Service>> getAllServices();

    @GET("{id}")
    Call <Service> getServiceById(@Path("id") Long id);

    @POST("create")
    Call <Void> addService(@Body Service service);

    @PUT("{id}")
    Call <Void> updateService(@Path("id") Long id , @Body Service service);

    @DELETE("{id}")
    Call <Void> deleteService(@Path("id") Long id);
}
