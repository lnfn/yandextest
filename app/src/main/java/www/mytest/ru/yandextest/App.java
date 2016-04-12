package www.mytest.ru.yandextest;

import android.app.Application;

/**
 * Created by Eugene on 24.03.2016.
 */
public class App extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}