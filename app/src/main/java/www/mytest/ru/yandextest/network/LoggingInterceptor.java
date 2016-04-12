package www.mytest.ru.yandextest.network;

import android.util.Log;

import java.io.IOException;

/**
 * Created by Eugene on 21.10.2015.
 */
public class LoggingInterceptor implements okhttp3.Interceptor {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("LOGGER ", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        okhttp3.Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d("LOGGER ", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response.newBuilder()
                .header("Cache-Control", String.format("max-age=%d", 120)) //Cache
                .build();
    }
}