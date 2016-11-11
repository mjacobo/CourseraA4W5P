package com.mj.courseraprw3;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.mj.courseraprw3.Adapter.PageAdapter;
import com.mj.courseraprw3.GCM.Adapter.RestApiAdapter;
import com.mj.courseraprw3.GCM.RestAPI.Endpoints;
import com.mj.courseraprw3.GCM.model.UsuarioResponse;
import com.mj.courseraprw3.animations.ZoomOutPageTransformer;
import com.mj.courseraprw3.fragment.PerfilFragment;
import com.mj.courseraprw3.fragment.mainFragment;
import com.mj.courseraprw3.pojo.contact;
import com.mj.courseraprw3.pojo.pets;
import com.mj.courseraprw3.restAPI.ConstantesRestApi;
import com.mj.courseraprw3.restAPI.EndpointsApi;
import com.mj.courseraprw3.restAPI.model.AccountResponse;
import com.mj.courseraprw3.restAPI.model.PetResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements mainFragment.ItemClicked {
    private Toolbar myToolBar;
    private TabLayout myTabLayout;
    private ViewPager myViewPager;
    public  Bundle extras, accountData = new Bundle();;
    public  String myInstgAccount = null;
    public  pets myInstgAccountID = null;
    public  String myFriendID = null;
    public  ArrayList<pets> mojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        setContentView(R.layout.activity_main);
        Boolean changeFrangment = getIntent().getBooleanExtra("ChangeFrame", false);
        String myFriendAccountId = getIntent().getStringExtra("id_usuario_instagram");
        extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("accountName", "Sin cuenta.") != "Sin cuenta.") {
                myInstgAccount = extras.getString("accountName", "Sin cuenta.");
                accountData.putString("accountName", myInstgAccount);
                setAccount();
            }

            if (myFriendAccountId != null){
                accountData.putString("accountId", myFriendAccountId);
                getMyFriendAccountDatabyId(myFriendAccountId);
            }
        }


        myToolBar   = (Toolbar) findViewById(R.id.myToolBar);
        myTabLayout = (TabLayout) findViewById(R.id.tlMyTabLayout);
        myViewPager = (ViewPager) findViewById(R.id.vpMyViewPager);
        myViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        setUpViewPager();

        if(myToolBar != null) {
            setSupportActionBar(myToolBar);
        }

        //getMyInstgAccountId();

        if (changeFrangment == true) {
            myViewPager.setCurrentItem(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.miContacto:
                Intent myContactDetail = new Intent(this, contact.class);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
                    Transition explode =
                            TransitionInflater.from(this)
                                    .inflateTransition(R.transition.explode);
                    getWindow().setExitTransition(explode);
                    startActivity(myContactDetail,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(this, myViewPager, getString(R.string.transicion_foto)).toBundle());
                } else {
                    startActivity(myContactDetail);
                }
                break;

            case R.id.miAcercaDe:
                Intent myAcercaDe = new Intent(this, Biography.class);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
                    Slide slide = new Slide(Gravity.BOTTOM);
                    slide.setDuration(2000);
                    slide.addListener(new Transition.TransitionListener() {
                        @Override
                        public void onTransitionStart(Transition transition) {
                            Toast.makeText(myViewPager.getContext(), "Empezando", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onTransitionEnd(Transition transition) {
                            Toast.makeText(myViewPager.getContext(), "Termina", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onTransitionCancel(Transition transition) {

                        }

                        @Override
                        public void onTransitionPause(Transition transition) {

                        }

                        @Override
                        public void onTransitionResume(Transition transition) {

                        }
                    });
                    getWindow().setEnterTransition(slide);

                    Transition fade =
                            TransitionInflater.from(this)
                                    .inflateTransition(R.transition.fade);
                    getWindow().setReturnTransition(fade);
                    startActivity(myAcercaDe,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(this, myViewPager, getString(R.string.transicion_foto)).toBundle());
                } else {
                    startActivity(myAcercaDe);
                }


                break;

            case R.id.myMenuAccount:
                Intent myMenuAccount = new Intent(this, Account.class);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
                    Slide slide  = new Slide(Gravity.TOP);
                    Slide eslide = new Slide(Gravity.BOTTOM);
                    slide.setDuration(2000);
                    getWindow().setEnterTransition(slide);
                    getWindow().setReturnTransition(eslide);
                    startActivity(myMenuAccount,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(this, myViewPager, getString(R.string.transicion_foto)).toBundle());

                } else {
                    startActivity(myMenuAccount);
                }

                break;

            case R.id.myRecNotif:
                if(myInstgAccount != null) {
                    sendMyIds();
                } else {
                    Toast.makeText(this, "Cuenta no configurada.", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMyIds(){
        String token = FirebaseInstanceId.getInstance().getToken();
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Endpoints endpoints = restApiAdapter.establecerConexionRestApi();
        Call<UsuarioResponse> usuarioResponseCall = endpoints.registrarTokenID(token, myInstgAccountID.getId());
        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();

                if(usuarioResponse != null) {
                    Log.d("ID_FIREBASE", usuarioResponse.getId());
                    Log.d("ID_DISPOSITIVO", usuarioResponse.getId_dispositivo());
                    Log.d("ID_USUARIO_INSTAGRAM", usuarioResponse.getId_usuario_instagram());
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });
        Log.d("Mi TAG: ","My TOKEN: " + token);
    }

    void setAccount (){
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
                    assignValue(tmpPet);
                }
                else{
                    myFriendID = null;
                }
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "¡Algo pasó en la conexión! Intenta de nuevo", Toast.LENGTH_SHORT).show();
                Log.e("Falló la conexión", t.toString());
            }
        });
    }

    public void getMyFriendAccountDatabyId(String myFriendAccountId){
        com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter restApiAdapter = new com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter();
        Gson gsonDeserializerAccount = restApiAdapter.buildGsonDeserializerAccount2();
        EndpointsApi endpointsApi = restApiAdapter.stablishConnectionApiInstagram(gsonDeserializerAccount);
        Call<AccountResponse> accountResponseCall = endpointsApi.getMyFriendAccountInfo(myFriendAccountId);

        accountResponseCall.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                AccountResponse accountResponse = response.body();
                pets myPetResponse = accountResponse.getMyAccount();

                pets tmpPet = new pets();
                tmpPet.setId(myPetResponse.getId());
                tmpPet.setNombreCompleto(myPetResponse.getNombreCompleto());
                tmpPet.setURLpicture(myPetResponse.getURLpicture());
                tmpPet.setLikes(myPetResponse.getLikes());
                assignValue(tmpPet);
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.d("FriendAccountDatabyId: ",t.toString());
            }
        });



    }

    public void getMyInstgAccountId(){
        com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter restApiAdapter = new com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter();
        Gson gsonDeserializerAccount = restApiAdapter.buildGsonDeserializerAccount2();
        EndpointsApi endpointsApi = restApiAdapter.stablishConnectionApiInstagram(gsonDeserializerAccount);
        Call<AccountResponse> accountResponseCall = endpointsApi.getMyInfo();


        accountResponseCall.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                AccountResponse accountResponse = response.body();
                pets myPetResponse = accountResponse.getMyAccount();

                pets tmpPet = new pets();
                tmpPet.setId(myPetResponse.getId());
                tmpPet.setNombreCompleto(myPetResponse.getNombreCompleto());
                tmpPet.setURLpicture(myPetResponse.getURLpicture());
                tmpPet.setLikes(myPetResponse.getLikes());
                assignValue(tmpPet);
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Log.d("Falló getMyAccount !!!",t.toString());
            }
        });

    }

    public void assignValue (pets value){
        myInstgAccountID = new pets();
        myInstgAccountID.setId(value.getId());
        myInstgAccountID.setNombreCompleto(value.getNombreCompleto());
        myInstgAccountID.setLikes(value.getLikes());
        myInstgAccountID.setURLpicture(value.getURLpicture());
    }

    public void showTopFive(View view){
        Intent myIntent = new Intent(this, top_five.class);
        startActivity(myIntent);
    }

    private ArrayList<Fragment>  agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new mainFragment());
        fragments.add(new PerfilFragment());
        fragments.get(1).setArguments(accountData);
        return fragments;
    }

    private void setUpViewPager () {
        myViewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), agregarFragments()));
        myTabLayout.setupWithViewPager(myViewPager);
        myTabLayout.getTabAt(0).setIcon(R.mipmap.ic_dog_house);
        myTabLayout.getTabAt(1).setIcon(R.mipmap.ic_dog_face);
    }

    @Override
    public void sendItem(Integer element) {
        PerfilFragment frag = (PerfilFragment)
                getSupportFragmentManager().getFragments().get(1);
        frag.receiveEle(element);
    }
}
