package net.androidbootcamp.bookxchange.Fragments;

import net.androidbootcamp.bookxchange.Notifications.MyResponse;
import net.androidbootcamp.bookxchange.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService
{
    @Headers (
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAgW9nViU:APA91bHQVJXt4Vo7kzEjPGRehNH3Cz0ma_nE4gQxcTHEU908nkF1tBr_sPrNs_-M6uoT1--X6Vp5w9TeCruS6xvcGx2d9ozxHkZA-x6iXwpupS8IorBJyF9jJwyWYqmLCCGRYjO2wLKw"
            }
    )

    @POST ("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
