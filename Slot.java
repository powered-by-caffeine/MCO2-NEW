

import java.util.ArrayList;

public class Slot {
    /**
     * Defines the minimum amount of items that can be stocked in the slot
     */
    final int MIN_ITEM_STOCK = 10;

    /**
     * Defines the maximum amount of items that can be stocked in the slot
     */
    final int MAX_ITEM_STOCK = 15;

    /**
     * The name of the slot. Should have the same name as the items stocked in the slot
     */
    private String slotName;

    /**
     * The amount of a specific item stocked in the slot
     */
    private ArrayList<Item> items;

    /**
     * The price of all of the items contained in the slot
     */
    private int itemPrice;

    /**
     * Constructor for the slot
     * @param slotName the name of the slot
     */
    public Slot(String slotName)
    {
        this.slotName = slotName;
        this.items = new ArrayList<>();
    }

    /**
     * Constructor for the slot. Primarily used for temporary slots where only the price is needed
     * @param slotName the name of the slot
     * @param itemPrice the price associated with what the slot holds
     */
    public Slot(String slotName, int itemPrice)
    {
        this.slotName = slotName;
        this.itemPrice = itemPrice;
        items = new ArrayList<>();
        items.add(new Item("Custom Order", itemPrice, 0));
        items.add(new Item("Custom Order", itemPrice, 0));
    }

    /**
     * Getter for the slot name
     * @return the slot's name
     */
    public String getSlotName()
    {
        return this.slotName;
    }

    /**
     * Setter for the slot name
     * @param slotName the slot's name
     */
    public void setSlotName(String slotName)
    {
        this.slotName = slotName;
    }

    /**
     * Getter for the price of all items in the slot
     * @return
     */
    public int getItemPrice()
    {
        return this.itemPrice;
    }

    /**
     * sets the price of all items in the slot
     * @param itemPrice the new price
     */
    public void setItemPrice(int itemPrice)
    {
        this.itemPrice = itemPrice;

        for (Item item : items)
        {
            item.setItemPrice(itemPrice);
        }
    }

    /**
     * Getter for the items contained in the slot
     * @return the items in the slot
     */
    public ArrayList<Item> getItems() 
    {
        return items;
    }

    /**
     * Stocks/restocks items into the slot
     * @param item the item to be stocked
     * @param quantity the amount to be stocked
     * @return TRUE if stock is successful
     */
    public boolean restockItems(Item item, int quantity)
    {
        if (!item.getItemName().equals(slotName)) //checks if the item has the same name as the slot (ensures that all items are of the same type)
            return false;

        if (items.size() + quantity < MIN_ITEM_STOCK) //checks if the quantity is less than the minimum amount of items allowed
            return false;

        if (items.size() + quantity > MAX_ITEM_STOCK) //checks if the quantity exceeds the maximum amount of items allowed
            return false;

        for (int i = 0; i < quantity; i++)
        {
            items.add(new Item(item.getItemName(), item.getItemPrice(), item.getItemCalorie())); //creates new instances of the item when stocked (as specified in specs)
        }
        
        this.itemPrice = item.getItemPrice();

        return true;
    }

    /**
     * Reduces items from the slot
     * @param amount the amount to be removed
     * @return TRUE if reduction is successful
     */
    public boolean reduceItems(int amount)
    {
        if (items.size() - amount < 0) //checks if reducing by the specified amount leads to a negative number of items
            return false;

        for (int i = 0; i < amount; i++)
        {
            items.remove(items.size() - 1); //removes items starting from the last index
        }
        return true;
    }
}
