package www.mytest.ru.yandextest.network;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import www.mytest.ru.yandextest.domain.Artist;

/**
 * Created by Eugene on 24.03.2016.
 */
public interface APIWebService {
    @GET("/download.cdn.yandex.net/mobilization-2016/artists.json")
    Observable<List<Artist>> getArtistsObservable();
}