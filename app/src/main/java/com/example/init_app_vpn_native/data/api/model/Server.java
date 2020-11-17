package com.example.init_app_vpn_native.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Server {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("auth")
    @Expose
    private Auth auth;
    @SerializedName("vpnConfig")
    @Expose
    private String vpnConfig;
    @SerializedName("geo")
    @Expose
    private Geo geo;

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

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public String getVpnConfig() {
        return vpnConfig;
    }

    public void setVpnConfig(String vpnConfig) {
        this.vpnConfig = vpnConfig;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

}