package bg.uni.sofia.fmi.mjt.Labs.wallet;

import bg.uni.sofia.fmi.mjt.Labs.wallet.acquisition.Acquisition;
import bg.uni.sofia.fmi.mjt.Labs.wallet.acquisition.AcquisitionManager;
import bg.uni.sofia.fmi.mjt.Labs.wallet.asset.Asset;
import bg.uni.sofia.fmi.mjt.Labs.wallet.exception.OfferPriceException;
import bg.uni.sofia.fmi.mjt.Labs.wallet.exception.UnknownAssetException;
import bg.uni.sofia.fmi.mjt.Labs.wallet.exception.WalletException;
import bg.uni.sofia.fmi.mjt.Labs.wallet.quote.Quote;
import bg.uni.sofia.fmi.mjt.Labs.wallet.quote.QuoteManager;

import javax.naming.InsufficientResourcesException;
import java.util.*;

public class InvestmentWallet implements Wallet {

    private List<Acquisition> acquisitions;
    private double cash;

    Map<Asset, Integer> assetQuantities;
    private final QuoteManager quoteManager;

    public InvestmentWallet(QuoteManager quoteManager) {
        this.acquisitions = new ArrayList<>();
        this.quoteManager = quoteManager;
        this.assetQuantities = new HashMap<>();
    }

    private void validateNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("null o");
        }
    }

    private void validateNotNegative(double cash) {
        if (cash < 0) {
            throw new IllegalArgumentException("null o");
        }
    }

    private void validateNotNegative(int cash) {
        if (cash < 0) {
            throw new IllegalArgumentException("null o");
        }
    }

    private Quote getQuote(Asset asset) {
        Quote result = this.quoteManager.getQuote(asset);
        if (result == null) {
            throw new UnknownAssetException();
        }
        return result;
    }


    @Override
    public double deposit(double cash) {
        validateNotNegative(cash);
        this.cash += cash;
        return this.cash;
    }

    @Override
    public double withdraw(double cash) throws InsufficientResourcesException {
        validateNotNegative(cash);
        if (this.cash < cash) {
            throw new InsufficientResourcesException();
        }
        this.cash -= cash;
        return this.cash;
    }

    @Override
    public Acquisition buy(Asset asset, int quantity, double maxPrice) throws WalletException {
        validateNotNegative(quantity);
        validateNotNegative(maxPrice);
        validateNotNull(asset);

        Quote wantedAssetQuote = this.getQuote(asset);

        if (wantedAssetQuote.askPrice() > maxPrice) {
            throw new WalletException();
        }

        Acquisition acquisition = new AcquisitionManager(asset, quantity, wantedAssetQuote);

        if (acquisition.getPrice() > this.cash) {
            // throw new InsufficientResourcesException();
        }

        cash -= acquisition.getPrice();

        this.acquisitions.add(acquisition);

        assetQuantities.put(asset, assetQuantities.get(asset) + quantity);
        return acquisition;
    }

    @Override
    public double sell(Asset asset, int quantity, double minPrice) throws WalletException {
        validateNotNull(asset);
        validateNotNegative(minPrice);

        Quote wantedAssetQuote = quoteManager.getQuote(asset);

        if (wantedAssetQuote == null) {
            throw new UnknownAssetException();
        }

        if (wantedAssetQuote.bidPrice() < minPrice) {
            throw new WalletException();
        }

        double addedCash = wantedAssetQuote.bidPrice() * quantity;

        this.cash += addedCash;

        this.assetQuantities.put(asset, assetQuantities.get(asset) - quantity);

        return addedCash;
    }

    @Override
    public double getValuation() {
        double valuation = 0.0;
        for (Asset asset : assetQuantities.keySet()) {
            valuation += this.getValuation(asset);
        }
        return valuation;
    }

    @Override
    public double getValuation(Asset asset) throws UnknownAssetException {
        validateNotNull(asset);

        if (!assetQuantities.containsKey(asset) || assetQuantities.get(asset) == 0) {
            throw new UnknownAssetException();
        }

        Quote quote = this.getQuote(asset);

        return quote.bidPrice() * assetQuantities.get(asset);
    }

    @Override
    public Asset getMostValuableAsset() {
        Asset mostValuable = null;
        for (Asset asset : assetQuantities.keySet()) {
            if (mostValuable == null || getValuation(asset) > getValuation(mostValuable)) {
                mostValuable = asset;
            }
        }
        return mostValuable;
    }

    @Override
    public Collection<Acquisition> getAllAcquisitions() {
        return List.copyOf(this.acquisitions);
    }

    @Override
    public Set<Acquisition> getLastNAcquisitions(int n) {
        if (n > this.acquisitions.size()) {
            return Set.copyOf(this.acquisitions);
        } else {
            Set<Acquisition> acquisitions = new HashSet<>();
            int startIndex = this.acquisitions.size() - n;
            int rightIndex = this.acquisitions.size();
            for (int i = startIndex; i < rightIndex; i++) {
                acquisitions.add(this.acquisitions.get(i));
            }
            return acquisitions;
        }
    }
}
