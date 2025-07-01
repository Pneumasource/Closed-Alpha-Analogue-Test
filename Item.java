public class Item {
    public String name;
    public Integer bulk = 1;
    public double price;
    public double bprice;
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        this.bprice = price;
    }
    public Item(String name, double price, int bulk, double bprice) {
        this.name = name;
        this.bulk = bulk;
        this.price = price;
        this.bprice = bprice;
    }
    public double priceFor(int q) {
        if (q < bulk) {
            return (q * price);
        } else {
            return (bprice * (q / bulk) + price * (q % bulk));
        }
    }
    public String toString() {
        String rep = name + ", $" + String.format("%.2f", price);
        if (bulk != 1) {
            rep = rep + " (" + bulk + " for SE$" + String.format("%.2f", bprice) + ")";
        }
        return rep;
    }
}
