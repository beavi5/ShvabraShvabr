package com.example.beavi5.shvabrashvabr.presenter;

import android.support.v7.widget.RecyclerView;

import com.example.beavi5.shvabrashvabr.model.RSSItem;
import com.example.beavi5.shvabrashvabr.adapters.RVPostAdapter;

import java.util.ArrayList;

/**
 * Created by beavi5 on 21.07.2017.
 */

/**
 * класс Presenter - посредник между View(MainActivity) и Моdel(RSSitem)
 */
public class Presenter implements IPresenter {
    private ArrayList<RSSItem> arrayListPosts;
    private RVPostAdapter rvPostAdapter;

    public Presenter(RecyclerView recyclerView) {
        arrayListPosts= new ArrayList<>();
        rvPostAdapter = new RVPostAdapter(arrayListPosts);

        recyclerView.setAdapter(rvPostAdapter);

    }

    /**метод обновляет View(MainActivity)*/
    @Override
    public void ShowPosts() {
        RSSItem.ParseRss("https://habrahabr.ru/rss/hubs/all/",arrayListPosts, rvPostAdapter);
        rvPostAdapter.notifyDataSetChanged();
    }
}
