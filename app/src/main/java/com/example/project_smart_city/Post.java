package com.example.project_smart_city;

public class Post {
    private int id;
    private int author_id;
    private int network_id;
    private String date;
    private String data;

    public Post(int author_id, int network_id, String date, String data) {
        this.author_id = author_id;
        this.network_id = network_id;
        this.date = date;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(int network_id) {
        this.network_id = network_id;
    }

    public String getDate() {
        return date;
    }

    public Post() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
