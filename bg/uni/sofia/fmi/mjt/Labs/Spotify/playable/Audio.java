package bg.uni.sofia.fmi.mjt.Labs.Spotify.playable;

public class Audio extends PlayableAbstract implements Playable {

    protected Audio(String title, String artist, int year, double duration) {
        super(title, artist, year, duration);
    }

    @Override
    protected String getType() {
        return "audio";
    }
}
