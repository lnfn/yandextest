package www.mytest.ru.yandextest.interactor;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.mytest.ru.yandextest.domain.Artist;
import www.mytest.ru.yandextest.network.APIWebService;

/**
 * Created by Eugene on 24.03.2016.
 */
public class ArtistListInteractor implements IArtistListInteractor {
    private APIWebService apiWebService;

    public ArtistListInteractor(APIWebService apiWebService) {
        this.apiWebService = apiWebService;
    }

    @Override
    public Observable<List<Artist>> getArtists() { //Возвращаем поток данных, который выполнится в background и вернет результат в главный поток
        return apiWebService.getArtistsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}