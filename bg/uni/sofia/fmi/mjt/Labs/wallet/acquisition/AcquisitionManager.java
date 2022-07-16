package bg.uni.sofia.fmi.mjt.Labs.wallet.acquisition;

import bg.uni.sofia.fmi.mjt.Labs.wallet.asset.Asset;
import bg.uni.sofia.fmi.mjt.Labs.wallet.quote.QuoteManager;

import java.time.LocalDateTime;

public class AcquisitionManager implements Acquisition {
    private Asset asset;
    private LocalDateTime timestamp;
    private double price;
    private int quantity;

    QuoteManager manager;

    public AcquisitionManager(Asset asset, int quantity, QuoteManager manager) {
        this.asset = asset;
        this.quantity = quantity;
        this.manager = manager;
        this.timestamp = LocalDateTime.now();
        this.price = manager.getQuote(this.asset).askPrice();
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public Asset getAsset() {
        return asset;
    }
}
