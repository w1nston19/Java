package bg.uni.sofia.fmi.mjt.Labs.wallet.asset;

public class Fiat extends AssetAbstract {
    public Fiat(String id, String name) {
        super(id, name, AssetType.FIAT);
    }
}
