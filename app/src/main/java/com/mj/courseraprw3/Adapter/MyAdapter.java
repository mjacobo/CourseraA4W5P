package com.mj.courseraprw3.Adapter;


import android.app.Activity;
import android.provider.SyncStateContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mj.courseraprw3.GCM.RestAPI.Endpoints;
import com.mj.courseraprw3.GCM.model.DeviceIdResponse;
import com.mj.courseraprw3.GCM.model.LikesCounterResponse;
import com.mj.courseraprw3.GCM.model.UsuarioResponse;
import com.mj.courseraprw3.R;
import com.mj.courseraprw3.db.ConstructorPets;
import com.mj.courseraprw3.pojo.likesPostAnswer;
import com.mj.courseraprw3.pojo.pets;
import com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter;
import com.mj.courseraprw3.restAPI.ConstantesRestApi;
import com.mj.courseraprw3.restAPI.EndpointsApi;
import com.mj.courseraprw3.restAPI.model.LikeSetResponse;
import com.mj.courseraprw3.restAPI.model.LikesResponse;
import com.mj.courseraprw3.restAPI.model.PetResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leyenda1 on 16/09/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<pets> mDataset;
    Activity activity;
    pets myTmpPet = null;
    public  ArrayList<pets> mojo;
    public  String id_dispositivo;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView  nName;
        public TextView  mLikes;
        public ImageView mImageView;
        public ImageView mButton ;

        public ViewHolder(View view) {
            super(view);
            nName      = (TextView)  view.findViewById(R.id.actvPetName);
            mLikes     = (TextView)  view.findViewById(R.id.actvLikes);
            mImageView = (ImageView) view.findViewById(R.id.acivPetPicture);
            mButton    = (ImageView) view.findViewById(R.id.acivWhiteBone);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<pets> myDataset, Activity activity) {
        this.mDataset     = myDataset;
        this.activity     = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String myName = mDataset.get(position).getNombreCompleto();
        final String myID   = mDataset.get(position).getId();
        final int mpos = position;
        final ConstructorPets consPets  = new ConstructorPets(activity);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nName.setText(myName);
        holder.mLikes.setText(String.valueOf(mDataset.get(position).getLikes()));
        Picasso.with(activity).load(mDataset.get(position).getURLpicture()).into(holder.mImageView);
        setAccount(myName);


        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstructorPets myConsPets = new ConstructorPets(activity);

                setMyLike(myID);
                storeMyLike(myID, myTmpPet.getId(),id_dispositivo);
                myConsPets.gatherPetLikes(mDataset.get(mpos));
                Toast.makeText(activity, "Diste like a: " + myName, Toast.LENGTH_SHORT).show();
                //holder.mLikes.setText(String.valueOf(myConsPets.myLikeNumber));
            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void setMyLike(String myId){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonLikesSet = restApiAdapter.buildGsonDeserializarlikesSet();
        EndpointsApi endpoints = restApiAdapter.stablishConnectionApiInstagram(gsonLikesSet);
        Call<LikeSetResponse> likeSetResponseCall = endpoints.setMyLike(myId);

        likeSetResponseCall.enqueue(new Callback<LikeSetResponse>() {
            @Override
            public void onResponse(Call<LikeSetResponse> call, Response<LikeSetResponse> response) {
                if (response.body() != null){
                    Log.d("MY_LIKE_SUCCESS", "Code:  " + String.valueOf(response.body().getMyLike().getCode()));
                } else {
                    Log.d("MY_LIKE_ERROR", "FAILED: " + response.raw());
                }
            }
            @Override
            public void onFailure(Call<LikeSetResponse> call, Throwable t) {

            }
        });
    }

    public void storeMyLike(String id_foto_instagram, String id_usuario_instagram, String id_dispositivo) {
        com.mj.courseraprw3.GCM.Adapter.RestApiAdapter restApiAdapter = new com.mj.courseraprw3.GCM.Adapter.RestApiAdapter();
        Endpoints endpoints = restApiAdapter.establecerConexionRestApi();
        Call<LikesCounterResponse> usuarioResponseCall = endpoints.registrarLikes(id_foto_instagram, id_usuario_instagram, id_dispositivo);
        usuarioResponseCall.enqueue(new Callback<LikesCounterResponse>() {
            @Override
            public void onResponse(Call<LikesCounterResponse> call, Response<LikesCounterResponse> response) {
                LikesCounterResponse likesCounterResponse = response.body();

                if (likesCounterResponse != null){
                    Log.d("ID_FIREBASE", likesCounterResponse.getId());
                    Log.d("ID_FOTO_INSTAGRAM",likesCounterResponse.getId_foto_instagram());
                    Log.d("ID_USUARIO_INSTAGRAM", likesCounterResponse.getId_usuario_instagram());
                    Log.d("ID_DISPOSITIVO", likesCounterResponse.getId_dispositivo());
                }

            }

            @Override
            public void onFailure(Call<LikesCounterResponse> call, Throwable t) {

            }
        });
    }


    void setAccount (String myInstgAccount){
        mojo = new ArrayList<>();

        com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter restApiAdapter = new com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter();
        Gson gsonAccount = restApiAdapter.buildGsonDeserializerAccount();
        EndpointsApi endpointsApi = restApiAdapter.stablishConnectionApiInstagram(gsonAccount);
        String url =  ConstantesRestApi.KEY_GET_FRIEND_INFO + ConstantesRestApi.KEY_QUERY_TOKEN +
                myInstgAccount  + ConstantesRestApi.KEY_SACCESS_TOKEN + ConstantesRestApi.ACCESS_TOKEN;
        Call<PetResponse> petResponseCall = endpointsApi.getMyFriendInfo(url);

        petResponseCall.enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                PetResponse petResponse = response.body();
                mojo = petResponse.getMyPetsPics();

                if (mojo.size() != 0){
                    pets tmpPet = new pets();
                    tmpPet.setId(mojo.get(0).getId());
                    tmpPet.setNombreCompleto(mojo.get(0).getNombreCompleto());
                    tmpPet.setURLpicture(mojo.get(0).getURLpicture());
                    tmpPet.setLikes(mojo.get(0).getLikes());
                    getDeviceId(mojo.get(0).getId());
                    assignValue(tmpPet);
                }
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                Log.e("Falló la conexión:", t.toString());
            }
        });
    }


    public void assignValue (pets value){
        myTmpPet = new pets();
        myTmpPet.setId(value.getId());
        myTmpPet.setNombreCompleto(value.getNombreCompleto());
        myTmpPet.setLikes(value.getLikes());
        myTmpPet.setURLpicture(value.getURLpicture());
    }


    public void getDeviceId(String id_usuario_instagram) {
        com.mj.courseraprw3.GCM.Adapter.RestApiAdapter restApiAdapter = new com.mj.courseraprw3.GCM.Adapter.RestApiAdapter();
        Endpoints endpoints = restApiAdapter.establecerConexionRestApi();
        Call<DeviceIdResponse> deviceIdResponseCall = endpoints.obtenerIdDispositivo(id_usuario_instagram);

        deviceIdResponseCall.enqueue(new Callback<DeviceIdResponse>() {
            @Override
            public void onResponse(Call<DeviceIdResponse> call, Response<DeviceIdResponse> response) {
                DeviceIdResponse deviceIdResponse = response.body();

                if (deviceIdResponse != null){
                    assignDeviceID(deviceIdResponse.getId_dispositivo());
                }
            }

            @Override
            public void onFailure(Call<DeviceIdResponse> call, Throwable t) {
                Log.d("DEVICE ID:", "Falla en la comunicación.");
            }
        });
    }

    public void assignDeviceID(String id_dispositivo) {
        String tmp = new String();
        tmp = id_dispositivo;
        this.id_dispositivo = tmp;
    }
}

