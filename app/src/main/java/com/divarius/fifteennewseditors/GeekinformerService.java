package com.divarius.fifteennewseditors;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Dima on 09.06.2016.
 */
public interface GeekinformerService {
    String BASE_URL = "http://geekinformer.net/api/";

    @GET("db/resources/all")
    Observable<OneNews[]> getNews();
}
