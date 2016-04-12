package www.mytest.ru.yandextest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import www.mytest.ru.yandextest.Constants;
import www.mytest.ru.yandextest.R;

public class ArtistActivity extends AppCompatActivity {

    private ImageView image;
    private TextView descTextView;
    private TextView albumTextView;
    private TextView trackTextView;
    private TextView genresTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = (ImageView) findViewById(R.id.image);
        descTextView = (TextView) findViewById(R.id.desc);
        albumTextView = (TextView) findViewById(R.id.album);
        trackTextView = (TextView) findViewById(R.id.track);
        genresTextView = (TextView) findViewById(R.id.genres);

        if(getIntent() != null) { //Получаем данные выбранного из списка артиста
            Intent intent = getIntent();
            String name = intent.getStringExtra(Constants.ARTIST_NAME);
            String img = intent.getStringExtra(Constants.ARTIST_IMAGE);
            String desc = intent.getStringExtra(Constants.ARTIST_DESC);
            int album = intent.getIntExtra(Constants.ARTIST_ALBUM, 0);
            int track = intent.getIntExtra(Constants.ARTIST_TRACK, 0);
            String[] genres = intent.getStringArrayExtra(Constants.ARTIST_JENRES);

            getSupportActionBar().setTitle(name);
            Glide.with(this) //Загружаем изображение с помощью библиотеки Glide
                    .load(img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //Кэшируем изображение
                    .error(R.drawable.placeholder) //Если ошибка при загрузке
                    .into(image);
            descTextView.setText(desc);
            albumTextView.setText(album + " альбомов");
            trackTextView.setText(track + " песни");

            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < genres.length; i++) { //Если несколько жанров, собираем из них строку
                stringBuilder.append(genres[i]);
                if(i != genres.length - 1) stringBuilder.append(", ");
            }
            genresTextView.setText(stringBuilder.toString());
        }
    }
}