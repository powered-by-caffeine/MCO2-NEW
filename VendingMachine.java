import java.util.*;

public class VendingMachine{
    /**
     * The machine's name
     */
    private String machineName;

    /**
     * The machine's change dispenser
     */
    private ChangeDispenser changeDispenser;

    /**
     * The list of items in the machine
     */
    private ArrayList<Slot> slots;

    /**
     * The money currently within the machine collected from successful purchases
     */
    private int paymentReserve = 0;

    /**
     * The starting inventory of the machine. Updates when the machine is restocked
     */
    private ArrayList<String> startingInventory = new ArrayList<>();

    /**
     * The list of items purchased from the machine. Updates when the machine is restocked
     */
    private ArrayList<Item> itemsPurchased = new ArrayList<>();

    /**
     * Defines the minimum amount of slots the machine could handle
     */
    private final int MIN_SLOTS = 8;

    /**
     * Defines the maximum amount of slots the machine can hold
     */
    private final int MAX_SLOTS = 20;

    /**
     * Default constructor for the vending machine. Creates a drink vending machine with preset, stocked items and a stocked change dispenser that has 10 of each denomination
     */
    public VendingMachine()
    {
        changeDispenser = new ChangeDispenser(10);

        machineName = "Drink Vending Machine";
        Item Coke = new Item("Coca-Cola", 43, 139);
        Item CokeZero = new Item("Coke Zero", 34, 0);
        Item Pepsi = new Item("Pepsi", 43, 150);
        Item Sprite = new Item("Sprite", 37, 146);
        Item Royal = new Item("Royal", 34, 76);
        Item MountainDew = new Item("Mountain Dew", 37, 170);
        Item Monster = new Item("Monster", 99, 101);
        Item RedBull = new Item("Red Bull", 86, 168);

        Slot slot1 = new Slot(Coke.getItemName());
        Slot slot2 = new Slot(CokeZero.getItemName());
        Slot slot3 = new Slot(Pepsi.getItemName());
        Slot slot4 = new Slot(Sprite.getItemName());
        Slot slot5 = new Slot(Royal.getItemName());
        Slot slot6 = new Slot(MountainDew.getItemName());
        Slot slot7 = new Slot(Monster.getItemName());
        Slot slot8 = new Slot(RedBull.getItemName());

        slot1.restockItems(Coke, 10);
        slot2.restockItems(CokeZero, 10);
        slot3.restockItems(Pepsi, 10);
        slot4.restockItems(Sprite, 10);
        slot5.restockItems(Royal, 10);
        slot6.restockItems(MountainDew, 10);
        slot7.restockItems(Monster, 10);
        slot8.restockItems(RedBull, 10);

        slots = new ArrayList<>();

        slots.add(slot1);
        slots.add(slot2);
        slots.add(slot3);
        slots.add(slot4);
        slots.add(slot5);
        slots.add(slot6);
        slots.add(slot7);
        slots.add(slot8);


        resetStartingInventory();
    }

    public VendingMachine(String machineName, Item item1, Item item2, Item item3, Item item4, Item item5, Item item6, Item item7, Item item8)
    {
        this.machineName = machineName;
        changeDispenser = new ChangeDispenser(0);

        slots = new ArrayList<>();

        Slot slot1 = new Slot(item1.getItemName());
        Slot slot2 = new Slot(item2.getItemName());
        Slot slot3 = new Slot(item3.getItemName());
        Slot slot4 = new Slot(item4.getItemName());
        Slot slot5 = new Slot(item5.getItemName());
        Slot slot6 = new Slot(item6.getItemName());
        Slot slot7 = new Slot(item7.getItemName());
        Slot slot8 = new Slot(item8.getItemName());

        //Set the names of the slots
        slots.add(0, slot1);
        slots.add(1, slot2);
        slots.add(2, slot3);
        slots.add(3, slot4);
        slots.add(4, slot5);
        slots.add(5, slot6);
        slots.add(6, slot7);
        slots.add(7, slot8);

        slots.get(0).restockItems(item1, 10);
        slots.get(1).restockItems(item2, 10);
        slots.get(2).restockItems(item3, 10);
        slots.get(3).restockItems(item4, 10);
        slots.get(4).restockItems(item5, 10);
        slots.get(5).restockItems(item6, 10);
        slots.get(6).restockItems(item7, 10);
        slots.get(7).restockItems(item8, 10);

        resetStartingInventory();
    }

    /**
     * Gets the machine name
     * @return the name of the vending machine
     */
    public String getMachineName() {
        return machineName;
    }

    /**
     * Stocks the machine's change
     * @param denominationValue the denomination to stock
     * @param quantity the quantity to be stocked
     * @return TRUE if restock is successful
     */
    public boolean stockChangeDispenser(int denominationValue, int quantity)
    {
        if (this.changeDispenser.stockChange(denominationValue, quantity))
            return true;
        else
            return false;
    }

    // /**
    //  * Used to pre-program an item for the machine, Overloaded, one requires stock while the other does not
    //  * @param itemName adds the name to the item
    //  * @param itemPrice adds a price to the item
    //  * @param itemCalorie adds the calorie to the item
    //  */
    // public boolean createNewItem(String itemName, int price, int calories, int quantity)
    // {
    //     if (slots.size() == MAX_SLOTS)
    //         return false;

    //     for (Slot slot : slots)
    //     {
    //         if (slot.getSlotName().equals(itemName)) //checks if the new item has a duplicate
    //             return false;
    //     }
        
    //     Item item = new Item(itemName, price, calories);

    //     slots.get(slots.size()).setSlotName(itemName); //creates a new slot for the new item via the first empty index in slots (slots.size)
        
    //     if (stockItem(item, quantity))
    //     {
    //         return true;
    //     }
        
    //     return false;
    // }

    /**
     * removes a slot from the vending machine
     * @param slotName the name of the slot to be removed
     * @return TRUE if slot is successfully removed
     */
    public boolean removeSlot(String slotName)
    {
        if (slots.size() == MIN_SLOTS)
            return false;

        for (Slot slot : slots)
        {
            if (slot.getSlotName().equals(slotName)) //checks if the new item has a duplicate
            {
                slots.remove(slot);
                return true;
            }    
        }

        return false;
    }

    /**
     * Stocks an item
     * @param item the item to be stocked
     * @param quantity the amount to be stocked
     * @return TRUE if stocking is successful
     */
    public boolean stockItem(Item item, int quantity)
    {
        for (Slot slot : slots) 
        {
            if (slot.getSlotName().equals(item.getItemName()))
            {
                if (slot.restockItems(item, quantity)) //checks if restock is successful
                {
                    resetStartingInventory();
                    resetItemsPurchased();
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Dispenses the item purchased
     * @param slot the slot containing the item to be purchased
     * @param payment the payment received by the machine
     * @return the list of prompts indicating purchase if successful
     */
    public ArrayList<String> dispenseItem(Slot slot, int payment)
    {
        ArrayList<String> prompts = new ArrayList<>();

        if (payment < slot.getItemPrice()) //checks if payment is not enough
            return null;

        if (payment == slot.getItemPrice()) //checks if payment is equal
        {
            // Collect Payment
            paymentReserve += slot.getItemPrice();

            // Dispense Item
            slot.reduceItems(1); 
            
            // Add item to list of purchased items
            itemsPurchased.add(slot.getItems().get(0));

            prompts.add("Purchase Successful!");
            prompts.add("Dispensing " + slot.getSlotName() + "... ");

            return prompts;
        }
        else if (payment > slot.getItemPrice())
        {
            ArrayList<Denomination> change = changeDispenser.dispenseChange(payment - slot.getItemPrice());

            if (change.isEmpty())
            {
                return null;
            }
            else
            {
                // Collect Payment
                paymentReserve += slot.getItemPrice();

                // Dispense Item
                slot.reduceItems(1);

                // Add item to list of purchased items
                itemsPurchased.add(slot.getItems().get(0));

                prompts.add("Purchase Successful!");
                prompts.add("Dispensing " + slot.getSlotName() + "... ");

                // Dispense change
                prompts.add("Dispensing change:");

                for (Denomination denomination : change)
                {
                    prompts.add("Dispensing " + denomination.getValue() + " PHP...");
                }

                return prompts;
            }
        }

        return null;
    }

    /**
     * Updates the starting inventory
     */
    private void resetStartingInventory()
    {
        this.startingInventory.clear();

        int i = 1;

        for (Slot slot : slots)
        {
            startingInventory.add("(Slot " + i + ") " + slot.getSlotName() + " | Stock: " + slot.getItems().size());
            ++i;
        }
    }

    /**
     * Resets the items purchased
     */
    private void resetItemsPurchased()
    {
        itemsPurchased.clear();
    }
    
    /**
     * Collects the payments received by the machine and resets the payment reserve.
     * @return the payments received by the machine since last collection
     */
    public int collectPayment()
    {
        int paymentsReceived = paymentReserve;

        paymentReserve = 0; //resets payment reserve

        return paymentsReceived;
    }

    /**
     * Getter for the slots in the machine
     * @return the slots in the machine
     */
    public ArrayList<Slot> getSlots() {
        return slots;
    }

    /**
     * Getter for the starting inventory since last restocking
     * @return the starting inventory since last restocking
     */
    public ArrayList<String> getStartingInventory() {
        return startingInventory;
    }

    /**
     * Getter for the items purchased since last restocking
     * @return the items purchased since last restocking
     */
    public ArrayList<Item> getItemsPurchased() {
        return itemsPurchased;
    }

    /**
     * Gets the item instance with the specified itemName
     * @param itemName the name of the item to be searched
     * @return the stocked item with the specified name
     */
    public Item getItem(String itemName)
    {
        ArrayList<Item> itemList = new ArrayList<>();

        for (Slot slot : slots)
        {
            itemList.add(slot.getItems().get(0));
        }

        for (Item item : itemList)
        {
            if (item.getItemName().equals(itemName))
            {
                return item;
            }
        }

        return null;
    }

    /**
     * Gets the item instance with the specified itemName
     * @param itemName the name of the item to be searched
     * @return the stocked item with the specified name
     */
    public boolean setItemPrice(String itemName, int newPrice)
    {
        for (Slot slot : slots)
        {
            if (slot.getSlotName().equals(itemName))
            {
                slot.setItemPrice(newPrice);
                return true;
            }
        }

        return false;
    }

    /**
     * Getter for the payment reserve of the machine
     * @return the payment reserve of the machine
     */
    public int getPaymentReserve() {
        return this.paymentReserve;
    }
}
