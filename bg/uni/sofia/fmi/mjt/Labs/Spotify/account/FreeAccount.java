package bg.uni.sofia.fmi.mjt.Labs.Spotify.account;

public class FreeAccount extends Account {
    public FreeAccount(String email) {
        super(email);
    }

    @Override
    public int getAdsListenedTo() {
        return plays / 5;
    }

    @Override
    public AccountType getType() {
        return AccountType.FREE;
    }
}
