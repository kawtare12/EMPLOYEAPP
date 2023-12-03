package com.mourid.employeeapp.api;

import com.mourid.employeeapp.entities.Employe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeApi {


    @GET("all")
     Call<List<Employe>> getAllEmploye();

    @GET("{id}")
    Call <Employe> getEmployeById(@Path("id") Long id);

    @POST("create")
    Call <Void> addEmploye(@Body Employe employe);

    @PUT("{id}")
    Call <Void> updateEmploye(@Path("id") Long id , @Body Employe employe);

    @DELETE("{id}")
    Call <Void> deleteEmploye(@Path("id") Long id);
}