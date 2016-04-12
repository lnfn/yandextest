package www.mytest.ru.yandextest.view;

import java.util.List;

import www.mytest.ru.yandextest.domain.Artist;

/**
 * Created by Eugene on 24.03.2016.
 */
public interface ArtistListView {
    void showLoading();
    void hideLoading();
    void onLoadArtists(List<Artist> artists);
    void onError(int resId);
}