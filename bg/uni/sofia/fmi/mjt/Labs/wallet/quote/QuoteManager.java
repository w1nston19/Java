package bg.uni.sofia.fmi.mjt.Labs.wallet.quote;

import bg.uni.sofia.fmi.mjt.Labs.wallet.asset.Asset;

import java.util.Map;

public class QuoteManager implements QuoteService {

    Map<Asset, Quote> quotes;

    public QuoteManager(Map<Asset, Quote> quotes) {
        this.quotes = quotes;
    }


    @Override
    public Quote getQuote(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("Asset cant be null");
        }
        for (Asset asset1 : quotes.keySet()) {
            if (asset == asset1) {
                quotes.get(asset);
            }
        }
        return null;
    }
}
