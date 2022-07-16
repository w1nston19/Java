package bg.uni.sofia.fmi.mjt.Labs.wallet.asset;

public class Stock extends AssetAbstract {
    public Stock(String id, String name) {
        super(id, name, AssetType.STOCK);
    }
}
