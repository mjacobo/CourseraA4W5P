package com.mj.courseraprw3.notificationsServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mj.courseraprw3.Biography;
import com.mj.courseraprw3.MainActivity;
import com.mj.courseraprw3.R;
import com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter;
import com.mj.courseraprw3.restAPI.EndpointsApi;
import com.mj.courseraprw3.restAPI.model.RelationshipResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leyenda on 11/8/16.
 */

public class recibirAccion extends BroadcastReceiver {
    public Context cotext;
    public Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        String accion = intent.getAction();
        this.cotext = context;
        this.intent = intent;

        if(notificationConstants.SEE_MY_PROFILE.equals(accion)){
            Toast.makeText(context, context.getString(R.string.texto_accion_ver_perfil), Toast.LENGTH_SHORT).show();
            checkMyProfile();
        }

        if(notificationConstants.FOLLOW_USER.equals(accion)){
            Toast.makeText(context, context.getString(R.string.texto_accion_seguir_usuario), Toast.LENGTH_SHORT).show();
            folowUser(intent.getStringExtra("id_usuario_instagram"), "follow");
        }

        if(notificationConstants.UNFOLLOW_USER.equals(accion)){
            Toast.makeText(context, context.getString(R.string.texto_accion_noSeguir_usuario), Toast.LENGTH_SHORT).show();
            folowUser(intent.getStringExtra("id_usuario_instagram"), "unfollow");
        }

        if(notificationConstants.CHECK_USER.equals(accion)){
            checkUser();
            Toast.makeText(context, context.getString(R.string.texto_accion_ver_usuario), Toast.LENGTH_SHORT).show();
        }

    }

    public void checkMyProfile(){
        Intent myAcercaDe = new Intent(cotext , Biography.class);
        myAcercaDe.putExtra("ChangeFrame", true);
        myAcercaDe.putExtra("id_usuario_instagram", intent.getStringExtra("id_usuario_instagram"));
        myAcercaDe.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cotext.startActivity(myAcercaDe);
    }

    public void folowUser(String id_usuario_instagram, String action){
        final String myaction=action;
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonFollowUser = restApiAdapter.buildGsonDeserealizarRelationshipResponse();
        EndpointsApi endpoints = restApiAdapter.stablishConnectionApiInstagram(gsonFollowUser);
        Call<RelationshipResponse> relationshipResponseCall = endpoints.setMyRelationship(id_usuario_instagram, action);

        relationshipResponseCall.enqueue(new Callback<RelationshipResponse>() {
            @Override
            public void onResponse(Call<RelationshipResponse> call, Response<RelationshipResponse> response) {
                RelationshipResponse relationshipResponse = response.body();

                if(relationshipResponse != null) {
                    Log.d("MY_" + myaction.toUpperCase() + "_RESULT = ", "outgoing_status: " + relationshipResponse.getOutgoing_status());
                    Log.d("MY_" + myaction.toUpperCase() + "_RESULT = ", "target_user_is_private: " + relationshipResponse.getTarget_user_is_private());
                    Log.d("MY_" + myaction.toUpperCase() + "_RESULT = ", "incoming_status: " + relationshipResponse.getIncoming_status());
                } else {
                    Log.d("MY_" + myaction.toUpperCase() + "_RESULT = ", "Already " + myaction + "ed.");
                }
            }

            @Override
            public void onFailure(Call<RelationshipResponse> call, Throwable t) {
                Log.d("MY_" + myaction.toUpperCase() + "_RESULT = ", "Failed. Error text: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void checkUser(){
        Intent myCheckUser = new Intent(cotext , MainActivity.class);
        myCheckUser.putExtra("ChangeFrame", true);
        myCheckUser.putExtra("id_usuario_instagram", intent.getStringExtra("id_usuario_instagram"));
        myCheckUser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cotext.startActivity(myCheckUser);
    }
}
