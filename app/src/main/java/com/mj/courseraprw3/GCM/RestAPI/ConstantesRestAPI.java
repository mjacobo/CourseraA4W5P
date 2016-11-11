package com.mj.courseraprw3.GCM.RestAPI;

/**
 * Created by leyenda on 10/26/16.
 */

public interface ConstantesRestAPI {
    public static final String ROOT_URL = "https://stark-shelf-11701.herokuapp.com/";
    public static final String KEY_POST_ID_TOKEN = "registrar-usuario/";
    public static final String KEY_POST_LIKES    = "registrar-likes/";
    public static final String KEY_TOQUE_FOTO = "toque-foto/{user_id}/{media_id}";
    public static final String KEY_GET_DEVICE_ID = "consultar-dispositivo/{device_id}";
}
