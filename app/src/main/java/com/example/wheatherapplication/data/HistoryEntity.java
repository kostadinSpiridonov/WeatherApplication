package com.example.wheatherapplication.data;

public class HistoryEntity {
    public static final String TableName = "History";
    public static final String IdColumn = "id";
    public static final String CityColumn = "City";
    public static final String UpdateDateColumn = "LastUpdate";

    private int id;
    private String city;
    private String updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
