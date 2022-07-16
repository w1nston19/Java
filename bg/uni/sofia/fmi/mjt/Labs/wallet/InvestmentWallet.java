package bg.uni.sofia.fmi.mjt.Labs.wallet;

import bg.uni.sofia.fmi.mjt.Labs.wallet.acquisition.Acquisition;
import bg.uni.sofia.fmi.mjt.Labs.wallet.asset.Asset;
import bg.uni.sofia.fmi.mjt.Labs.wallet.exception.OfferPriceException;
import bg.uni.sofia.fmi.mjt.Labs.wallet.exception.UnknownAssetException;
import bg.uni.sofia.fmi.mjt.Labs.wallet.exception.WalletException;
import bg.uni.sofia.fmi.mjt.Labs.wallet.quote.Quote;
import bg.uni.sofia.fmi.mjt.Labs.wallet.quote.QuoteManager;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class InvestmentWallet implements Wallet {

    private List<Acquisition> acquisitions;
    private double cash;

    private QuoteManager quoteManager;

    public InvestmentWallet(QuoteManager quoteManager) {
        this.acquisitions = new ArrayList<>();
        this.quoteManager = quoteManager;
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
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
        validateNotNegative(maxPrice);
        validateNotNull(asset);

        Quote wantedAssetQuote = quoteManager.getQuote(asset);

        if (wantedAssetQuote == null) {
            throw new UnknownAssetException();
        }

        if (wantedAssetQuote.askPrice() > maxPrice) {
            throw new WalletException();
        }

        if (wantedAssetQuote.askPrice() * quantity > this.cash) {
            //throw new InsufficientResourcesException();
        }


        return null;
    }

    @Override
    public double sell(Asset asset, int quantity, double minPrice) throws WalletException {
        return 0;
    }

    @Override
    public double getValuation() {
        return 0;
    }

    @Override
    public double getValuation(Asset asset) throws UnknownAssetException {
        return 0;
    }

    @Override
    public Asset getMostValuableAsset() {
        return null;
    }

    @Override
    public Collection<Acquisition> getAllAcquisitions() {
        return null;
    }

    @Override
    public Set<Acquisition> getLastNAcquisitions(int n) {
        return null;
    }
}
