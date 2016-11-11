package com.mj.courseraprw3.restAPI.deserializador;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.mj.courseraprw3.pojo.pets;
import com.mj.courseraprw3.restAPI.JsonKeys;
import com.mj.courseraprw3.restAPI.model.PetResponse;
import com.mj.courseraprw3.restAPI.model.RelationshipResponse;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by leyenda on 11/8/16.
 */

public class RelationshipDeserealizador implements JsonDeserializer<RelationshipResponse> {

    @Override
    public RelationshipResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        RelationshipResponse relationshipResponse = gson.fromJson(json, RelationshipResponse.class);
        try {
            JsonObject relationshipResponseData = json.getAsJsonObject().getAsJsonObject(JsonKeys.GET_MY_RELATIONSHIP_ARRAY);

            relationshipResponse.setRelationshipResponse(deserializeRelationshipResponseFromJson(relationshipResponseData));
        } catch(Exception e){
            Log.d("Object fail: "," Failed!!!");
            relationshipResponse.setRelationshipResponse(new RelationshipResponse("null","null", "null"));
        }
        finally {
            return relationshipResponse;
        }
    }

    private RelationshipResponse deserializeRelationshipResponseFromJson(JsonObject relationshipResponseData) {
        RelationshipResponse myRelationshipResponse;

        String outgoing_status = relationshipResponseData.get(JsonKeys.MY_RELATIONSHIP_OUTGOING_STATUS).getAsString();
        String target_user_is_private = relationshipResponseData.get(JsonKeys.MY_TARGET_USER_IS_PRIVATE).getAsString();
        String incoming_status = relationshipResponseData.get(JsonKeys.MY_RELATIONSHIP_INCOMING_STATUS).getAsString();

        myRelationshipResponse = new RelationshipResponse();
        myRelationshipResponse.setOutgoing_status(outgoing_status);
        myRelationshipResponse.setTarget_user_is_private(target_user_is_private);
        myRelationshipResponse.setIncoming_status(incoming_status);

        return myRelationshipResponse;
    }
}
