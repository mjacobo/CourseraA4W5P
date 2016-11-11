package com.mj.courseraprw3.GCM.model;

/**
 * Created by leyenda on 11/4/16.
 */

public class DeviceIdResponse {
    private String id;
    private String id_dispositivo;

    public DeviceIdResponse(String id, String id_dispositivo) {
        this.id = id;
        this.id_dispositivo = id_dispositivo;
    }

    public DeviceIdResponse() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }
}
