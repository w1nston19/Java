package bg.uni.sofia.fmi.mjt.Labs.wallet.quote;

import bg.uni.sofia.fmi.mjt.Labs.wallet.asset.Asset;

public interface QuoteService {

    /**
     * @param asset
     * @return the quote for the given asset. If there is no quote, return null.
     * @throws IllegalArgumentException if the asset is null
     */
    Quote getQuote(Asset asset);
}
