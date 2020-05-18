package com.example.project_smart_city;

public class Network {
    private String name;
    private String description;
    private String status;
    private String listMembers;
    private String listRequest;
    private int id;
    private int creator;

    public Network() {
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Network(String name, String description, String status, int creator) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.creator = creator;
        this.listMembers = creator + ";";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getListMembers() {
        return listMembers;
    }

    public void setListMembers(String listMembers) {
        this.listMembers = listMembers;
    }

    public void addMemberToList(String member) {
        this.listMembers += ";" + member + ";";

    }

    public Boolean addRequestToList(String member) {
        if(listRequest.contains(";" + member + ";")){
            return false;
        }
        else{
            if(listRequest == null){
                this.listRequest = ";" + member + ",";
            }
            else {
                this.listRequest += ";" + member +";";
            }
            return true;
        }


    }

    public void removeMember(String memberToRemove){
        this.listMembers = listRequest.replace(";" + memberToRemove +";","");
    }

    public void removeRequest(String requesToRemove){this.listRequest = listRequest.replace(";" + requesToRemove + ";", "");}

    public String getListRequest() {
        return listRequest;
    }

    public void setListRequest(String listRequest) {
        this.listRequest = listRequest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Network{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", listMembers='" + listMembers + '\'' +
                ", listRequest='" + listRequest + '\'' +
                ", id=" + id +
                ", creator='" + creator + '\'' +
                '}';
    }
}
