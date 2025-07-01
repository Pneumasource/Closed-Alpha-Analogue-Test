public class ShoppingCart {
    public ItemOrder[] orders;
    public boolean disc = false;
    public ItemOrder initialize = new ItemOrder(new Item("None", 0), 0);
    public ShoppingCart(int size) {
        orders = new ItemOrder[size];
        for (int i = 0; i < orders.length; i++) {
            orders[i] = initialize;
        }
    }
    public void add(ItemOrder i) {
        for (int j = 0; j < orders.length; j++) {
            if ((orders[j] == initialize) || (orders[j].item == i.item)) {
                orders[j] = i;
                j = orders.length;
            }
        }
    }
    public void setDiscount(boolean value) {
        disc = value;
    }
    public double getTotal() {
        double cost = 0;
        for (int i = 0; i < orders.length; i++) {
            cost += orders[i].getPrice();
        }
        if (disc) {
            cost *= 0.9;
        }
        return cost;
    }
    /*
    The ShoppingCart class stores information about the overall order. It should have the following public methods:
    public ShoppingCart(size) {
        Constructor that takes the size of the cart as a parameter and creates an empty
        array of item orders.
    }
    public static add(item order) {
        Adds an item order to the array, replacing any previous order for this item with
        the new order. The parameter will be of type ItemOrder.
    }
    public static setDiscount(value) {
        Sets whether or not this order gets a discount (true means there is a
        discount, false means no discount).
    }
    public static getTotal() {
        Returns the total cost of the shopping cart.
    }
    */
}
