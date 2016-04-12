package www.mytest.ru.yandextest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import www.mytest.ru.yandextest.Constants;
import www.mytest.ru.yandextest.R;
import www.mytest.ru.yandextest.activity.ArtistActivity;
import www.mytest.ru.yandextest.domain.Artist;

/**
 * Created by Eugene on 24.03.2016.
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
    private Context context;
    private List<Artist> artistList;

    public ArtistListAdapter(Context context, List<Artist> artistList) {
        this.context = context;
        this.artistList = artistList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artistlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(artistList.get(position).getCover().getSmall())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(holder.image);

        holder.name.setText(artistList.get(position).getName());
        StringBuilder genres = new StringBuilder();
        for(int i = 0; i < artistList.get(position).getGenres().length; i++) {
            genres.append(artistList.get(position).getGenres()[i]);
            if(i != artistList.get(position).getGenres().length - 1) genres.append(", ");
        }
        holder.genres.setText(genres.toString());
        holder.count.setText(String.format("%d альбомов, %d песен", artistList.get(position).getAlbums(), artistList.get(position).getTracks()));

        animate(holder);
    }

    @Override
    public int getItemCount() {
        return this.artistList.size();
    }

    public void refreshData(List<Artist> artistList) {
        this.artistList = artistList;
        notifyDataSetChanged();
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setAlpha(0.2f);
        viewHolder.itemView.setTranslationY(-20);
        ViewCompat.animate(viewHolder.itemView)
                .alpha(1)
                .translationY(0)
                .setDuration(500)
                .start();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView genres;
        private TextView count;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.genres = (TextView) itemView.findViewById(R.id.genres);
            this.count = (TextView) itemView.findViewById(R.id.count);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Artist artist = artistList.get(getAdapterPosition());
                    Intent intent = new Intent(context, ArtistActivity.class);
                    intent.putExtra(Constants.ARTIST_NAME, artist.getName());
                    intent.putExtra(Constants.ARTIST_DESC, artist.getDescription());
                    intent.putExtra(Constants.ARTIST_IMAGE, artist.getCover().getBig());
                    intent.putExtra(Constants.ARTIST_ALBUM, artist.getAlbums());
                    intent.putExtra(Constants.ARTIST_TRACK, artist.getTracks());
                    intent.putExtra(Constants.ARTIST_JENRES, artist.getGenres());

                    String transitionName = context.getString(R.string.trans_name);
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                                    image,   // The view which starts the transition
                                    transitionName    // The transitionName of the view we’re transitioning to
                            );
                    ActivityCompat.startActivity((Activity) context, intent, options.toBundle()); //Если Лиллопоп и выше, будет анимация
                }
            });
        }
    }
}