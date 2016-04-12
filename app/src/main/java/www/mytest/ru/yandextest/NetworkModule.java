package www.mytest.ru.yandextest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import www.mytest.ru.yandextest.network.APIWebService;
import www.mytest.ru.yandextest.network.LoggingInterceptor;

/**
 * Created by Eugene on 24.03.2016.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    APIWebService provideAPIWebService(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL_WEB_SERVICE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(APIWebService.class);
    }

    @Provides
    OkHttpClient provideOkHttpClient(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 1024 * 1024 * 10);
        return new okhttp3.OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .cache(cache)
                .build();
    }

    @Provides
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
    }
}