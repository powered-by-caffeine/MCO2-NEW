public class Item {
    private String itemName;
    private int itemPrice;
    private int itemCalorie;

    final int MIN_ITEMS = 8;
    final int MIN_ITEM_STOCK = 10;

    final int MAX_ITEMS = 10;
    final int MAX_ITEM_STOCK = 15;

    /**
     * Constructs an item with a name, price, and calorie amount
     * @param itemName the item's name
     * @param itemPrice the item's price
     * @param itemCalorie the item's calorie amount
     */
    public Item(String itemName, int itemPrice, int itemCalorie){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCalorie = itemCalorie;

    }

    /**
     * gets item name
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * gets the item price
     * @return the item price
     */
    public int getItemPrice() {
        return itemPrice;
    }

    /**
     * Gets the item calorie
     * @return the item calorie
     */
    public int getItemCalorie() {
        return itemCalorie;
    }
    
    /**
     * Sets the item's price
     * @param itemPrice
     */
    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
}
