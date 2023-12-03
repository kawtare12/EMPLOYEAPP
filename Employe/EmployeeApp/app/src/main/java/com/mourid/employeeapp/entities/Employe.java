package com.mourid.employeeapp.entities;


public class Employe {
    private Long id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private Service service;

    public Employe() {
    }
    public Employe(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    public Employe(Long id, String nom, String prenom, String dateNaissance, Service service) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
