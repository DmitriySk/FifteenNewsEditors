package com.divarius.fifteennewseditors;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsFeedFragment extends Fragment {
    RecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(R.layout.horizontal_feed, container, false);
        return rv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeekinformerService.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeekinformerService geekService = retrofit.create(GeekinformerService.class);
        Observable<NewsGSON[]> news = geekService.getNews();

        news.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsGSON[]>() {
                    @Override
                    public void onCompleted() { Log.i("ZED", "Completed"); }

                    @Override
                    public void onError(Throwable e) { e.printStackTrace(); }

                    @Override
                    public void onNext(NewsGSON[] o) {
                        Log.i("ZED", o[0].getTitle());

                        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        NewsFeedAdapter nAdapter = new NewsFeedAdapter(o);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(llm);
                        rv.setAdapter(nAdapter);
                    }
                });
        super.onActivityCreated(savedInstanceState);
    }
}
