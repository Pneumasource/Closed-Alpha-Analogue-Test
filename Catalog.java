
public class Catalog {
    public String name;
    public int size;
    public Item[] catalog;
    public int pointer = 0;
    public Catalog(String name,int size) {
        this.name = name;
        this.size = size;
        catalog = new Item[size];
    }
    public boolean add(Item item) {
        if (pointer < catalog.length) {
            catalog[pointer] = item;
            pointer++;
            return true;
        } else {
            return false;
        }
    }
    public int size() {
        return pointer;
    }
    public Item get(int index) {
        return catalog[index];
    }
    public String getName() {
        return name;
    }
}
