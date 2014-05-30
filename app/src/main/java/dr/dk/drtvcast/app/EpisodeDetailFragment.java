package dr.dk.drtvcast.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import dr.dk.drtvcast.app.mu.MuOnline;

/**
 * A fragment representing a single Episode detail screen.
 * This fragment is either contained in a {@link EpisodeListActivity}
 * in two-pane mode (on tablets) or a {@link EpisodeDetailActivity}
 * on handsets.
 */
public class EpisodeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private MuOnline.Item mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EpisodeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            String id = getArguments().getString(ARG_ITEM_ID);
            mItem = MuOnline.LoadProgramCard(id);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_episode_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.title)).setText(mItem.Title);
            ((TextView) rootView.findViewById(R.id.description)).setText(mItem.Description);

            ImageView image = (ImageView) rootView.findViewById(R.id.imageView);
            try {
                URL thumb_url = new URL(mItem.PrimaryImageUri);
                Drawable thumb = Drawable.createFromStream(thumb_url.openStream(), "src");
                image.setImageDrawable(thumb);
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }

            Button button = (Button) rootView.findViewById(R.id.play);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent player = new Intent(rootView.getContext(),VideoPlayer.class);
                    player.putExtra(VideoPlayer.ARG_ASSET_URI, mItem.PrimaryAsset.Uri);
                    player.putExtra(VideoPlayer.ARG_TITLE, mItem.Title);
                    startActivity(player);
                }
            });

            //image.setImageURI(Uri.parse(mItem.PrimaryImageUri));}
        }
        /*
        VideoView videoView = (VideoView) rootView.findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("http://drod02c-vh.akamaihd.net/i/dk/clear/streaming/c4/5383e0d3a11f9d1560377bc4/Flodmonstre-IV--6-7-_8a67ef4654d645289d0b4b4db42c6c83_,1126,562,248,122,.mp4.csmil/master.m3u8"));
        MediaController mc = new MediaController(rootView.getContext());
        videoView.setMediaController(mc);
        videoView.requestFocus();
        videoView.start();*/
        return rootView;
    }
}
