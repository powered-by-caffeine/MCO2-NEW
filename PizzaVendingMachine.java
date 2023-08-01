

import java.util.ArrayList;

public class PizzaVendingMachine extends VendingMachine implements SpecialVendingMachine
{
    //Note that the Pizza vending machine only offers pizza slices

    /**
     * List of dough types
     */
    private ArrayList<Item> doughTypes;

    /**
     * List of sauces 
     */
    private ArrayList<Item> sauces;

    /**
     * List of toppings
     */
    private ArrayList<Item> toppings;

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
     * Default constructor for the pizza vending machine
     */
    public PizzaVendingMachine()
    {
        machineName = "Pizza Vending Machine";

        //initialize a change dispenser with 10 of each denomination
        changeDispenser = new ChangeDispenser(10);

        slots = new ArrayList<>();

        doughTypes = new ArrayList<>();
        sauces = new ArrayList<>();
        toppings = new ArrayList<>();

        //https://www.vincenzaspizza.com/blog/how-many-calories-in-a-slice-of-pizza/
        //https://assets.ctfassets.net/kahtja4ygpol/KSSSLnOGMriA70VYdZVar/e159313577a2da8c8dbe81771dc6853f/PHR_Dietary_Information_Booklet_July_2023__1_.pdf

        // For this simulation, the crust is estimated to make up at least 50% of the calorie content of a pizza with one choice of topping
        // ((Total calories of 13-14" slice of pepperoni pizza with dough type - calories of pepperoni) / 2)
        // *Caloric Reference c/o Pizza Hut and Vincenza's

        // Dough Types
        Item RegularCrust = new Item("Regular Crust", 50, 115);
        Item ThinCrust = new Item("Thin Crust", 40, 79);
        Item StuffedCrust = new Item("Stuffed Crust", 65, 125);

        doughTypes.add(RegularCrust);
        doughTypes.add(ThinCrust);
        doughTypes.add(StuffedCrust);

        // Sauces
        Item TomatoSauce = new Item("Tomato Sauce", 15, 25);

        sauces.add(TomatoSauce);

        // Toppings
        Item Pepperoni = new Item("Pepperoni", 30, 50);
        Item Mushrooms = new Item("Mushrooms", 30, 15);
        Item Bacon = new Item("Bacon", 30, 43);
        Item Cheese = new Item("Cheese", 30, 38);
        Item Peppers = new Item("Peppers", 20, 20);
        Item BlackOlives = new Item("Black Olives", 20, 25);
        Item Pineapple = new Item("Pineapple", 20, 14);
        Item Ham = new Item("Ham", 30, 15);

        toppings.add(Pepperoni);
        toppings.add(Mushrooms);
        toppings.add(Bacon);
        toppings.add(Cheese);
        toppings.add(Peppers);
        toppings.add(BlackOlives);
        toppings.add(Pineapple);
        toppings.add(Ham);


        slots = new ArrayList<>();
        int i = 0;
        
        Slot temp[] = new Slot[50];

        for (Item doughType : doughTypes)
        {
            temp[i] = new Slot(doughType.getItemName());
            this.slots.add(i, temp[i]);
            this.slots.get(i).restockItems(doughType, 10);
            ++i;
        }

        for (Item sauce : sauces)
        {
            temp[i] = new Slot(sauce.getItemName());
            this.slots.add(i, temp[i]);
            this.slots.get(i).restockItems(sauce, 10);
            ++i;
        }

        for (Item topping : toppings)
        {
            temp[i] = new Slot(topping.getItemName());
            this.slots.add(i, temp[i]);
            this.slots.get(i).restockItems(topping, 10);
            ++i;        
        }

        resetStartingInventory();
    }

    /**
     * Manual Constructor for the pizza vending machine
     * @param machineName the machine's name
     * @param dough1 1st item (set as dough)
     * @param sauce1 2nd item (set as sauce)
     * @param topping1 3rd item (set as topping)
     * @param topping2 4th item (set as topping)
     * @param topping3 5th item (set as topping)
     * @param topping4 6th item (set as topping)
     * @param topping5 7th item (set as topping)
     * @param topping6 8th item (set as topping)
     */
    public PizzaVendingMachine(String machineName, Item dough1, Item sauce1, Item topping1, Item topping2, Item topping3, Item topping4, Item topping5, Item topping6)
    {
        this.machineName = machineName;
        
        //initialize a change dispenser with 10 of each denomination
        changeDispenser = new ChangeDispenser(0);

        slots = new ArrayList<>();

        doughTypes = new ArrayList<>();
        sauces = new ArrayList<>();
        toppings = new ArrayList<>();

        doughTypes.add(dough1);

        sauces.add(sauce1);

        toppings.add(topping1);
        toppings.add(topping2);
        toppings.add(topping3);
        toppings.add(topping4);
        toppings.add(topping5);
        toppings.add(topping6);

        int i = 0;
        
        Slot temp[] = new Slot[50];

        //create a slot for each item
        for (Item doughType : doughTypes)
        {
            temp[i] = new Slot(doughType.getItemName());
            this.slots.add(i, temp[i]);
            this.slots.get(i).restockItems(doughType, 10);
            ++i;
        }

        for (Item sauce : sauces)
        {
            temp[i] = new Slot(sauce.getItemName());
            this.slots.add(i, temp[i]);
            this.slots.get(i).restockItems(sauce, 10);
            ++i;
        }

        for (Item topping : toppings)
        {
            temp[i] = new Slot(topping.getItemName());
            this.slots.add(i, temp[i]);
            this.slots.get(i).restockItems(topping, 10);
            ++i;        
        }

        //Stocks an item "Cheese" (cheese is a universal ingredient for all pizzas made by this machine)
    
        Item Cheese = new Item("Cheese", 30, 38);

        temp[i] = new Slot(Cheese.getItemName());
        this.slots.add(i, temp[i]);
        this.slots.get(i).restockItems(Cheese, 10);

        resetStartingInventory();
    }

    /**
     * Gives the sequence of order preparations and reduces the quantity of the used items
     * @param doughType the chosen type of dough
     * @param sauce the chosen sauce
     * @param toppings the chosen toppings
     * @return the sequence of order preparations
     */
    public ArrayList<String> prepareOrder(String doughType, String sauce, ArrayList<String> toppings) 
    {
        ArrayList<String> orderPreparation = new ArrayList<>();

        orderPreparation.add("Preparing " + doughType + "... ");
        orderPreparation.add("Adding " + sauce + "... ");
        orderPreparation.add("Adding cheese...");
        for (String topping : toppings)
        {
            orderPreparation.add("Topping with " + topping + "... ");
        }


        orderPreparation.add("Baking pizza... Order finished. Please Enjoy!");

        //Reduce stock of ingredients used
        for (Slot slot : this.slots)
        {
            if (slot.getSlotName().equals(doughType))
                slot.reduceItems(1);

            if (slot.getSlotName().equals(sauce))
                slot.reduceItems(1);

            for (String topping : toppings)
            {
                if (slot.getSlotName().equals(topping))
                    slot.reduceItems(1);
            }

            //Reduce stock of cheese (cheese is a universal ingredient for all pizzas made by this machine)
            if (slot.getSlotName().equals("Cheese"))
            {
                slot.reduceItems(1);
            }
        }        

        return orderPreparation;
    }
     
    /**
     * Gives the pictures needed for the order preparation
     * @param doughType the dough of the pizza
     * @param sauce the sauce of the pizza
     * @param toppings the toppings of the pizza
     * @return the image URLs of the preparations performed by the machine
     */
    public ArrayList<String> prepareOrderPictures (String doughType, String sauce, ArrayList<String> toppings)
    {
        //Note that due to the limitations of this program (and to keep things simple) the pictures shown in the final output may not be entirely accurate to what the customer ordered
        
        //The machine is meant to dispense slices, but due to limited pictures available on the web, pictures shown can only be of whole pizzas

        //Also note that the pictures will be more accurate if item names are similar to those used in the default constructor

        //Lastly, how to final product looks will be based on the last topping in the toppings arraylist


        ArrayList<String> preparationPictures = new ArrayList<>();

        String finalPizza = "https://valentinos.com/wp-content/uploads/2019/06/cheese-768x768.png"; //the final pizza will be set to a default image

        //Adding dough picture
        preparationPictures.add("https://iambaker.net/wp-content/uploads/2019/07/pizza-dough-1.jpg");
        //image source: https://iambaker.net/pizza-dough-recipe/

        //Adding sauce picture
        preparationPictures.add("https://www.dogtownpizza.com/wp-content/uploads/2020/05/raw-dough-for-pizza-with-ingredient-tomato-sauce-mozzarella-tomatoes-picture-id667079158.jpg");
        //image source: https://www.dogtownpizza.com/blog/types-of-pizza-sauce/ 
        
        //Adding cheese picture
        preparationPictures.add("https://assets.shop.loblaws.ca/products/21456393/b2/en/front/21456393_front_a06_@2.png");
        //image source: https://www.atlanticsuperstore.ca/cheese-pizza-12-inch-made-fresh-in-store/p/21456393_EA

        //Adding toppings
        for (String topping : toppings)
        {
            switch (topping.toLowerCase())
            {
                case "pepperoni":
                    preparationPictures.add("https://www.samspizza.ph/wp-content/uploads/2022/03/360_F_184286756_q9Y3oFBVBotnWvtCxuVGE7XlaVuBHFAx.jpg");
                    //image source: https://www.samspizza.ph/online-order/pepperoni/    

                    finalPizza = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9TDl6bxHLrMQuZWz2Us3g1R35LbkouA-Xgf9JkgzGwA6I2dmGzu0kWofvx7PTqf_PT_Y&usqp=CAU";
                    //image source: https://valentinos.com/product/pepperoni-pizza/
                    break;
                
                case "mushrooms":
                    preparationPictures.add("https://www.harrisfarm.com.au/cdn/shop/products/MushrommSliced_1024x1024.jpg?v=1596679719");
                    //image source: https://www.harrisfarm.com.au/products/mushrooms-sliced-2362

                    finalPizza = "https://santabarbarabaker.com/wp-content/uploads/2020/08/IMG_2968.jpg";
                    //image source: https://santabarbarabaker.com/mushroom-pizza-the-fancy-treatment-recipe-how-to/
                    break;

                case "bacon":
                    preparationPictures.add("https://350burger.com/wp-content/uploads/2021/12/baconslice-1.jpg");
                    //image source: https://350burger.com/product/bacon-slice-brdn/

                    finalPizza = "https://www.momwithcookies.com/wp-content/uploads/2018/08/Easy-bacon-pizza-1024x683.jpg";
                    //image source: https://www.momwithcookies.com/easy-bacon-pizza/
                    break;

                case "cheese":
                    preparationPictures.add("https://www.getdexters.co.uk/uploads/1/3/4/5/134593336/s908403780577173274_p61_i1_w1300.jpeg");
                    //image source: https://www.getdexters.co.uk/product/grated-cheese/61

                    finalPizza = "https://live.staticflickr.com/5177/5460454767_ca3ac177bd_b.jpg";
                    //image source: https://www.flickr.com/photos/25909621@N08/5460454767
                    break;

                case "peppers":
                    preparationPictures.add("https://www.chilipeppermadness.com/wp-content/uploads/2019/08/Bell-Peppers.jpg");
                    //image source: https://www.chilipeppermadness.com/chili-pepper-types/sweet-mild-chili-peppers/sweet-bell-peppers/

                    finalPizza = "https://i0.wp.com/www.thecandidcooks.com/wp-content/uploads/2022/08/spicy-sausage-pepper-pizza-feature.jpg?w=1920&ssl=1";
                    //image source: https://www.thecandidcooks.com/pizza-of-the-month-spicy-sausage-and-pepper-pizza/
                    break;

                case "black olives":
                    preparationPictures.add("https://www.mocafoodandwine.com/wp-content/uploads/2020/11/sliced-black-olives.jpg");
                    //image source: https://www.mocafoodandwine.com/product/sliced-black-olives-4-5-kg-tin/

                    finalPizza = "https://cdn7.kiwilimon.com/recetaimagen/15739/640x426/7759.jpg.webp";
                    //https://us.kiwilimon.com/recipe/breads/pizza-with-black-olives
                    break;

                case "pineapples":
                    preparationPictures.add("https://www.thereciperebel.com/wp-content/uploads/2020/08/how-to-cut-a-pineapple-www.thereciperebel.com-600-21-of-24.jpg");
                    //image source: https://www.thereciperebel.com/how-to-cut-a-pineapple/

                    finalPizza = "https://media-cldnry.s-nbcnews.com/image/upload/t_fit-560w,f_auto,q_auto:eco,dpr_2.0/newscms/2017_46/1297263/pineapple-pizza-today-tease-171117.jpg";
                    //image source: "https://www.today.com/food/pineapple-acceptable-pizza-topping-america-decides-t118980";
                    break;

                case "ham":
                    preparationPictures.add("https://shop.gerald.ph/content/images/thumbs/0003268_forest-ham-slices.jpeg");
                    //image source: https://shop.gerald.ph/forest-ham
                    
                    finalPizza = "https://www.foodfromportugal.com/content/uploads/2022/03/ham-cheese-pizza-1.jpg";
                    //image source: https://www.foodfromportugal.com/recipes/ham-cheese-pizza/
                    break;

            }
        }

        
        //the final element of the preparation pictures is the output pizza itself
        preparationPictures.add(finalPizza);

        return preparationPictures;
    }

    /**
     * Gets the machine name
     * @return the name of the vending machine
     */
    public String getMachineName() 
    {
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
     * Creates a new item
     * @param itemType the type of the item (1 - dough, 2 - sauce, 3 - topping)
     * @param itemName the new item's name
     * @param price the new item's price
     * @param calories the new item's calories
     * @param quantity the new item's starting stock
     * @return TRUE if creation is successful
     */
    public boolean createNewItem(int itemType, String itemName, int price, int calories, int quantity)
    {
        if (slots.size() == MAX_SLOTS)
            return false;

        for (Slot slot : slots)
        {
            if (slot.getSlotName().equals(itemName)) //checks if the new item has a duplicate
                return false;
        }
        
        Item item = new Item(itemName, price, calories);

        slots.get(slots.size()).setSlotName(itemName); //creates a new slot for the new item via the first empty index in slots (slots.size)
        
        if (stockItem(item, quantity))
        {
            switch (itemType)
            {
                case 1:
                    doughTypes.add(item);
                    break;
                case 2:
                    sauces.add(item);
                    break;
                case 3:
                    toppings.add(item);
                    break;
            }

            return true;
        }
        
        return false;
    }

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
    
    /**
     * Getter for the dough types present in the machine
     * @return the dough types present in the machine
     */
    public ArrayList<Item> getDoughTypes() {
        return this.doughTypes;
    }

    /**
     * Getter for the sauce types present in the machine
     * @return the sauce types present in the machine
     */
    public ArrayList<Item> getSauces() {
        return this.sauces;
    }

    /**
     * Getter for the topping types present in the machine
     * @return the topping types present in the machine
     */
    public ArrayList<Item> getToppings() {
        return this.toppings;
    }
}    
        
