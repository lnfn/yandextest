package www.mytest.ru.yandextest.interactor;

import java.util.List;

import rx.Observable;
import www.mytest.ru.yandextest.domain.Artist;

/**
 * Created by Eugene on 24.03.2016.
 */
public interface IArtistListInteractor {
    Observable<List<Artist>> getArtists();
}