package com.divarius.fifteennewseditors;

import retrofit2.http.GET;
import rx.Observable;

public interface GeekinformerService {
    String BASE_URL = "http://geekinformer.net/api/";

    @GET("db/resources/all")
    Observable<NewsGSON[]> getNews();
}
