package bg.uni.sofia.fmi.mjt.Labs.Spotify.account;

public class PremiumAccount extends Account {

    final double PREMIUM_FEE = 25.0;

    public PremiumAccount(String email) {
        super(email);
    }

    @Override
    public int getAdsListenedTo() {
        return 0;
    }

    @Override
    public AccountType getType() {
        return AccountType.PREMIUM;
    }
}
