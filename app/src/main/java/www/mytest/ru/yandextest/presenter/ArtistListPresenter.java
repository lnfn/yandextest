package www.mytest.ru.yandextest.presenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import www.mytest.ru.yandextest.R;
import www.mytest.ru.yandextest.domain.Artist;
import www.mytest.ru.yandextest.interactor.IArtistListInteractor;
import www.mytest.ru.yandextest.view.ArtistListView;

/**
 * Created by Eugene on 24.03.2016.
 */
public class ArtistListPresenter implements IArtistListPresenter {
    private IArtistListInteractor artistListInteractor;
    private ArtistListView artistListView;
    private Subscription subscription;

    @Inject
    public ArtistListPresenter(IArtistListInteractor artistListInteractor) {
        this.artistListInteractor = artistListInteractor;
    }

    @Override
    public void setView(ArtistListView artistListView) {
        this.artistListView = artistListView;
    }

    @Override
    public void getArtists() {
        artistListView.showLoading();
        subscription = artistListInteractor.getArtists()
                .subscribe(
                        new Action1<List<Artist>>() {
                               @Override
                               public void call(List<Artist> artists) {
                                   artistListView.hideLoading();
                                   if(artists != null && artists.size() > 0)
                                       artistListView.onLoadArtists(artists);
                                   else
                                       artistListView.onError(R.string.no_data);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                artistListView.hideLoading();
                                artistListView.onError(R.string.no_internet);
                            }
                        });
    }

    @Override
    public void onDestroy() {
        if(subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
        artistListView = null;
    }
}