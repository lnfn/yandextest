package www.mytest.ru.yandextest.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import www.mytest.ru.yandextest.App;
import www.mytest.ru.yandextest.DividerItemDecoration;
import www.mytest.ru.yandextest.R;
import www.mytest.ru.yandextest.adapter.ArtistListAdapter;
import www.mytest.ru.yandextest.domain.Artist;
import www.mytest.ru.yandextest.presenter.ArtistListPresenter;
import www.mytest.ru.yandextest.view.ArtistListView;

public class ArtistListActivity extends AppCompatActivity implements ArtistListView {
    private RecyclerView recyclerView;
    private TextView textView;
    private ProgressBar progressBar;

    @Inject
    public ArtistListPresenter artistListPresenter; //Constructor inject

    private ArtistListAdapter artistListAdapter;
    private List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        App.getAppComponent().inject(this); //После этого все зависимости подтянутся

        artistList = new ArrayList<>();
        artistListAdapter = new ArtistListAdapter(this, artistList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(artistListAdapter);

        artistListPresenter.setView(this);
        artistListPresenter.getArtists(); //Говорим презентору, чтобы дал список артистов
    }

    @Override
    protected void onDestroy() {
        artistListPresenter.onDestroy(); //Отписываемся
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        if(recyclerView.getVisibility() == View.VISIBLE) recyclerView.setVisibility(View.GONE);
        if(textView.getVisibility() == View.VISIBLE) textView.setVisibility(View.GONE);
        if(progressBar.getVisibility() == View.GONE) progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if(progressBar.getVisibility() == View.VISIBLE) progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoadArtists(@NonNull List<Artist> artists) {
        if(recyclerView.getVisibility() == View.GONE) recyclerView.setVisibility(View.VISIBLE);
        this.artistList = artists;
        artistListAdapter.refreshData(this.artistList);
    }

    @Override
    public void onError(int resId) {
        if(textView.getVisibility() == View.GONE) textView.setVisibility(View.VISIBLE);
        textView.setText(getString(resId));
    }
}