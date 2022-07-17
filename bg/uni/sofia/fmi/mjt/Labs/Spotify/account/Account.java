package bg.uni.sofia.fmi.mjt.Labs.Spotify.account;

import bg.uni.sofia.fmi.mjt.Labs.Spotify.exceptions.PlaylistCapacityExceededException;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.library.Library;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.library.UserLibrary;
import bg.uni.sofia.fmi.mjt.Labs.Spotify.playable.Playable;

public abstract class Account {
    private final double PROFIT_PER_AD = 0.1;
    private final double PREMIUM_PROFIT = 25;
    String email;
    protected Library library;
    double totalListenTime;
    int plays;



    public Account(String email) {
        this.email = email;
        this.library = new UserLibrary();
        totalListenTime = 0.0;
        plays = 0;
    }

    /**
     * Returns the number of ads listened to.
     * - Free accounts get one ad after every 5 pieces of content played
     * - Premium accounts get no ads
     */
    public abstract int getAdsListenedTo();

    /**
     * Returns the account type as an enum with possible values FREE and PREMIUM
     */
    public abstract AccountType getType();

    /**
     * Simulates listening of the specified content.
     * This should increment the total number of content listened and the total listen time for this account.
     *
     * @param playable
     */
    public void listen(Playable playable) {
        playable.play();
        this.totalListenTime = playable.getDuration();
        this.plays++;
    }

    /**
     * Returns the library for this account.
     */
    public Library getLibrary() {
        return library;
    }

    /**
     * Returns the total listen time for this account. The time for any ads listened is not included.
     */
    public double getTotalListenTime() {
        return totalListenTime;
    }

    public double getRevenue() {
        if (this.getType() == AccountType.PREMIUM) {
            return PREMIUM_PROFIT;
        } else {
            return this.getAdsListenedTo() * PROFIT_PER_AD;
        }
    }

    public void like(Playable playable) throws PlaylistCapacityExceededException {
        this.library.getLiked().add(playable);
    }
}
