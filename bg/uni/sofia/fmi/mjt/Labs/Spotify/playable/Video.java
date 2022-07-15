package bg.uni.sofia.fmi.mjt.Labs.Spotify.playable;

public class Video extends PlayableAbstract implements Playable {
    protected Video(String title, String artist, int year, double duration) {
        super(title, artist, year, duration);
    }

    @Override
    protected String getType() {
        return "video";
    }
}
