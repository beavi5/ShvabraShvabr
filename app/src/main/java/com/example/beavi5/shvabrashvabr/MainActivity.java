/**Project Shvabra Shvabr
 * MVP
 *
 * @author      Alexandr Hadeev
 * @author      telegram: sashajzx100
 * @version     1.0
 * @see         vozmiteVsberTeh.))
 *
 */
package com.example.beavi5.shvabrashvabr;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.beavi5.shvabrashvabr.presenter.Presenter;


/**
 * MainActivity
 * View
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;

    /**Presenter*/
    private Presenter presenter;

    @Override
    protected void onStart() {
        super.onStart();
        presenter.ShowPosts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rvPosts);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        presenter = new Presenter(recyclerView);







        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ParseAndRefresh();
                swipeContainer.setRefreshing(false);

            }
        });
        swipeContainer.setColorSchemeResources(R.color.sbColorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }


    /**
     * метод для отправки презентеру команды обновить RecyclerListView
     */
    private void ParseAndRefresh() {
        presenter.ShowPosts();

  }

}
