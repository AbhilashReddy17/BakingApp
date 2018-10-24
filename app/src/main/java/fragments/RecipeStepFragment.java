package fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhi.bakingapp.AppUtils;
import com.abhi.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Recipe;
import models.SingletonClass;

import static com.abhi.bakingapp.Constants.PLAYER_POSITION;
import static com.abhi.bakingapp.Constants.PLAYER_STATE;
import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;
import static com.abhi.bakingapp.Constants.RECIPE_STEP_CLICKED;


public class RecipeStepFragment extends Fragment implements ExoPlayer.EventListener {

    static RecipeStepFragment fragment;

    @BindView(R.id.playerView)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.step_description_id)
    TextView stepDescription;
    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    public static final String TAG = RecipeStepFragment.class.getSimpleName();

    int recipeClicked;
    int recipeStepClicked;
    long playerPosition = 0;
    boolean playerState;
    Recipe recipe;
    public RecipeStepFragment() {
        // Required empty public constructor
    }


    public static RecipeStepFragment getInstance(int recipeClicked,int recipeStepClicked) {
        fragment = new RecipeStepFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_CLICKED,recipeClicked);
        bundle.putInt(RECIPE_STEP_CLICKED,recipeStepClicked);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getContext()).inflate(R.layout.recipe_step_fragment, container, false);

        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION, 0);
            playerState = savedInstanceState.getBoolean(PLAYER_STATE);
        }
        recipeClicked = getArguments().getInt(RECIPE_CLICKED, 0);
        recipeStepClicked = getArguments().getInt(RECIPE_STEP_CLICKED, 0);
         recipe = SingletonClass.getsInstance().getRecipes().get(recipeClicked); //getting the recipe selected


        Bitmap thumbnail = BitmapFactory.decodeResource(getResources(), AppUtils.getsInstance().getRecipeImage(recipe.getName(), getContext()));
        simpleExoPlayerView.setDefaultArtwork(thumbnail);

        stepDescription.setText(recipe.getSteps().get(recipeStepClicked).getDescription());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Uri uri = Uri.parse(recipe.getSteps().get(recipeStepClicked).getVideoURL());
        if (!(uri.getPath().equals(""))) {
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            initializeMediaSession();
            initializePlayer(uri);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if(mExoPlayer!=null) {
            releasePlayer();
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        long position;
        boolean player_state = false;
        if(mExoPlayer !=null) {
            position = mExoPlayer.getCurrentPosition();
            outState.putLong(PLAYER_POSITION, position);
             player_state = mExoPlayer.getPlayWhenReady();
        }
        outState.putBoolean(PLAYER_STATE,player_state);
        super.onSaveInstanceState(outState);

    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new RecipeStepFragment.MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.seekTo(playerPosition);
            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playerState);
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }


    /**
     * Broadcast Receiver registered to receive the MEDIA_BUTTON intent coming from clients.
     */
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
}
