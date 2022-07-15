package bg.uni.sofia.fmi.mjt.Spotify.playable;

public abstract class PlayableAbstract implements Playable{

    protected String title, artist;
    protected int year;
    protected double duration;

    private int plays;

    protected PlayableAbstract(String title, String artist, int year, double duration){
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.duration = duration;
        plays = 0;
    }

    @Override
    public String play() {
        plays += 1;
        return "Currently playing <" + this.getType() + " content: " + this.getTitle();
    }

    @Override
    public int getTotalPlays() {
        return plays;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    protected abstract String getType();
}
