
public class ItemOrder {
    public Item item;
    public int count;
    public ItemOrder(Item i, int q) {
        this.item = i;
        count = q;
    }
    public double getPrice() {
        return item.priceFor(count);
    }
    public Item getItem(){
        return item;
    }
}
