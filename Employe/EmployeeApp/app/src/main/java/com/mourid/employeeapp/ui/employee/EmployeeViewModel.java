package com.mourid.employeeapp.ui.employee;

import androidx.lifecycle.ViewModel;

public class EmployeeViewModel extends ViewModel {
    private String nom = "Yahya";
    public EmployeeViewModel() {
    }

    public String getData() {return nom;}
}
