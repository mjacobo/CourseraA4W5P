package com.mj.courseraprw3.restAPI;

import com.mj.courseraprw3.restAPI.model.AccountResponse;
import com.mj.courseraprw3.restAPI.model.LikeSetResponse;
import com.mj.courseraprw3.restAPI.model.LikesResponse;
import com.mj.courseraprw3.restAPI.model.PetResponse;
import com.mj.courseraprw3.restAPI.model.RelationshipResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by leyenda1 on 16/10/2016.
 */

public interface EndpointsApi {

    @FormUrlEncoded
    @POST(ConstantesRestApi.URL_SET_MY_REL_WITH)
    Call<RelationshipResponse> setMyRelationship(@Path("user_id") String user_id, @Field("action") String action);

    @GET(ConstantesRestApi.URL_GET_MY_REL_WITH)
    Call<RelationshipResponse> getMyRelationship(@Path("user_id") String user_id);

    @POST(ConstantesRestApi.URL_SET_LIKE_IMAGE)
    Call<LikeSetResponse> setMyLike(@Path("media_id")  String media_id);

    @GET(ConstantesRestApi.URL_GET_LIKE_IMAGE)
    Call<LikesResponse> getMyLikeNumber(@Path("media_id")  String media_id);

    @GET(ConstantesRestApi.URL_GET_RECENT_MEDIA_USER)
    Call<PetResponse> getRecentMedia();

    @GET(ConstantesRestApi.URL_GET_MY_INFO)
    Call<AccountResponse> getMyInfo();

    @GET(ConstantesRestApi.URL_GET_LIST_USER_FOLLOWS)
    Call<PetResponse> getMyFriendList();

    @GET(ConstantesRestApi.URL_GET_FRIEND_DATA)
    Call<AccountResponse> getMyFriendAccountInfo(@Path("friendId")  String friendId);

    @GET
    Call<PetResponse> getMyFriendInfo(@Url String url);

    @GET
    Call<PetResponse> getFriendRecentMedia(@Url String url);

}
