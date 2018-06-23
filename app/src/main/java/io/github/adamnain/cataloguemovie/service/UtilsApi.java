package io.github.adamnain.cataloguemovie.service;

public class UtilsApi {

    public static final String BASE_URL_API = "https://api.themoviedb.org/";

    // Mendeklarasikan Interface BaseApiService
    public static EndPoints getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(EndPoints.class);
    }

}