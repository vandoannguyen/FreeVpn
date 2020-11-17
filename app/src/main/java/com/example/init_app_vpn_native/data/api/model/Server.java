package com.example.init_app_vpn_native.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Server {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("configDatas")
    @Expose
    private List<String> configDatas = null;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getConfigDatas() {
        return configDatas;
    }

    public void setConfigDatas(List<String> configDatas) {
        this.configDatas = configDatas;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", location=" + location +
                ", configDatas=" + configDatas +
                ", countryCode='" + countryCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}