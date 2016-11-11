package com.mj.courseraprw3.restAPI.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mj.courseraprw3.pojo.likes;
import com.mj.courseraprw3.pojo.pets;
import com.mj.courseraprw3.restAPI.JsonKeys;
import com.mj.courseraprw3.restAPI.model.LikesResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by leyenda on 10/30/16.
 */

public class LikeDeserealizador implements JsonDeserializer<LikesResponse> {

    @Override
    public LikesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        LikesResponse likesResponse = gson.fromJson(json, LikesResponse.class);
        JsonArray likesResponseData= json.getAsJsonObject().getAsJsonArray(JsonKeys.LIKES_RESPONSE_ARRAY);

        likesResponse.setMylikes(deserializeMylikesFromJson(likesResponseData));
        return likesResponse;
    }

    private ArrayList<likes> deserializeMylikesFromJson(JsonArray likesResponseData){
        ArrayList<likes> myLikes = new ArrayList<>();

        for (int i = 0; i < likesResponseData.size() ; i++) {
            JsonObject likesResponseDataObject = likesResponseData.get(i).getAsJsonObject();

            String username        = likesResponseDataObject.get(JsonKeys.LIKES_RESPONSE_USERNAME).getAsString();
            String full_name       = likesResponseDataObject.get(JsonKeys.LIKES_RESPONSE_FULL_NAME).getAsString();
            String profile_picture = likesResponseDataObject.get(JsonKeys.LIKES_RESPONSE_PROFILE_PICTURE).getAsString();
            String id              = likesResponseDataObject.get(JsonKeys.LIKES_RESPONSE_ID).getAsString();

            likes myCurrentlike = new likes();
            myCurrentlike.setUsername(username);
            myCurrentlike.setFull_name(full_name);
            myCurrentlike.setProfile_picture(profile_picture);
            myCurrentlike.setId(id);

            myLikes.add(myCurrentlike);
        }

        return myLikes;
    }
}
