package bg.uni.sofia.fmi.mjt.Labs.Spotify.account;

public class FreeAccount extends Account {

    private final int PLAYS_BETWEEN_ADS = 5;
    public FreeAccount(String email) {
        super(email);
    }

    @Override
    public int getAdsListenedTo() {
        return plays / PLAYS_BETWEEN_ADS;
    }

    @Override
    public AccountType getType() {
        return AccountType.FREE;
    }
}
