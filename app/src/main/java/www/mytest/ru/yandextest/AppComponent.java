package www.mytest.ru.yandextest;

import javax.inject.Singleton;

import dagger.Component;
import www.mytest.ru.yandextest.activity.ArtistListActivity;

/**
 * Created by Eugene on 24.03.2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    ArtistListActivity inject(ArtistListActivity artistListActivity);
}