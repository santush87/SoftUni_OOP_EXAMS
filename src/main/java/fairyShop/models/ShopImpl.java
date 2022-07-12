package fairyShop.models;

public abstract class ShopImpl implements Shop {

    @Override
    public abstract void craft(Present present, Helper helper);
}
