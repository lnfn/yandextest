package www.mytest.ru.yandextest.presenter;

import www.mytest.ru.yandextest.view.ArtistListView;

/**
 * Created by Eugene on 24.03.2016.
 */
public interface IArtistListPresenter {
    void getArtists();
    void setView(ArtistListView artistListView);
    void onDestroy();
}