package bg.uni.sofia.fmi.mjt.Labs.wallet.asset;

public abstract class AssetAbstract implements Asset {
    protected String id, name;
    protected AssetType type;

    public AssetAbstract(String id, String name, AssetType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AssetType getType() {
        return type;
    }
}
