package www.mytest.ru.yandextest;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import www.mytest.ru.yandextest.interactor.ArtistListInteractor;
import www.mytest.ru.yandextest.interactor.IArtistListInteractor;
import www.mytest.ru.yandextest.network.APIWebService;

/**
 * Created by Eugene on 24.03.2016.
 */
@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    IArtistListInteractor provideArtistListInteractor(APIWebService apiWebService) {
        return new ArtistListInteractor(apiWebService);
    }

    @Provides
    Context provideContext () {
        return application.getApplicationContext();
    }
}