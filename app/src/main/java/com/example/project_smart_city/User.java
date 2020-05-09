package com.example.project_smart_city;


import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private int id;
    private String pseudo;
    private String name;
    private String surname;
    private String email;
    private String sexe;
    private String birthday;
    private String password;
    private int size;
    private int weight;
    private byte[] profilPicture;
    private ArrayList<String> listChoices;
    private ArrayList<String> listInterests;

    public User() {}

    public User(String pseudo, String name, String surname, String email,String sex, String birthday, String password, int size, int weight, byte[] img) {
        this.pseudo = pseudo;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.sexe = sex;
        this.birthday = birthday;
        this.password = password;
        this.size = size;
        this.weight = weight;
        this.profilPicture = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setProfilPicture(byte[] profilPicture) {
        this.profilPicture = profilPicture;
    }

    public void setListChoices(ArrayList<String> listChoices) {
        this.listChoices = listChoices;
    }

    public void setListInterests(ArrayList<String> listInterests) {
        this.listInterests = listInterests;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getSexe() {
        return sexe;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public byte[] getProfilPicture() {
        return profilPicture;
    }

    public ArrayList<String> getListChoices() {
        return listChoices;
    }

    public ArrayList<String> getListInterests() {
        return listInterests;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", sexe='" + sexe + '\'' +
                ", birthday='" + birthday + '\'' +
                ", password='" + password + '\'' +
                ", size=" + size +
                ", weight=" + weight +
                ", profilPicture=" + profilPicture +
                ", listChoices=" + listChoices +
                ", listInterests=" + listInterests +
                '}';
    }
}
