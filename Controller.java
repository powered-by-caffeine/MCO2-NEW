import java.util.ArrayList;

public class Controller {
    //Models
    private VendingMachine currentMachine;
    
    //View
    private View view;


    //Payments received from the view
    private int paymentsReceived;

    //Dough Selected by the user
    private String doughSelected;

    //Sauce Selected by the user
    private String sauceSelected;

    //Toppings Selected by the user
    private ArrayList<String> toppingsSelected;

    //Total price of a custom order
    private int totalItemPrice;

    //Prompts associated with the preparation for the user's custom order
    private ArrayList<String> preparationPrompts;
    
    

    //Prompts associated with the preparation for the user's custom order
    private ArrayList<String> preparationPictures;
    
    /**
     * Constructor for the controller
     */
    public Controller()
    {
        this.paymentsReceived = 0;
        this.totalItemPrice = 0;

        toppingsSelected = new ArrayList<>();

        view = new View(this);
        view.setTestMachineStatus(false);        
    }

    /**
     * Initializes the regular vending machine preset
     */
    public void initializeRegularMachinePreset()
    {
        currentMachine = new VendingMachine();
        view.setTestMachineStatus(true);
    }

    /**
     * Initializes the special vending machine preset
     */
    public void initializeSpecialMachinePreset()
    {
        currentMachine = new PizzaVendingMachine();
        view.setTestMachineStatus(true);
    }

    /**
     * Initializes the manual version of regular machine 
     * @return TRUE if initialization is successful
     */
    public boolean initializeRegularMachineManual() {

        view.setFeedbackLblText("Processing...");
        String[] itemNames = view.getItemNamesText();
        String[] itemPrices = view.getItemPricesText();
        String[] itemCalories = view.getItemCaloriesText();

        int itemPriceValues[] = new int[itemPrices.length];
        int itemCalorieValues[] = new int[itemCalories.length];

        String machineName = view.getTextFieldText();

        Item[] items = new Item[8];
        try
        {
            for (int i = 0; i < itemPrices.length; i++)
            {
                itemPriceValues[i] = Integer.parseInt(itemPrices[i]);
                itemCalorieValues[i] = Integer.parseInt(itemCalories[i]);
            }    
        } catch (Exception ex)
        {
            view.setFeedbackLblText("Error creating vending machine.");
            return false;
        }
        
        
        //error checking
        if (itemNames.length != 8 || itemPrices.length != 8 || itemCalories.length != 8)
        {
            view.setFeedbackLblText("Error creating vending machine.");
            return false;
        }
        else
        {
            for (int i = 0; i < itemNames.length; i++)
            {
                items[i] = new Item(itemNames[i], itemPriceValues[i], itemCalorieValues[i]);
            }
            currentMachine = new VendingMachine(machineName, items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7]);
            view.setTestMachineStatus(true);
            return true;
        }
    }

    /**
     * Initializes the manual version of regular machine 
     * @return TRUE if initialization is successful
     */
    public boolean initializeSpecialMachineManual() {

        view.setFeedbackLblText("Processing...");
        String[] itemNames = view.getItemNamesText();
        String[] itemPrices = view.getItemPricesText();
        String[] itemCalories = view.getItemCaloriesText();

        int itemPriceValues[] = new int[itemPrices.length];
        int itemCalorieValues[] = new int[itemCalories.length];

        String machineName = view.getTextFieldText();

        Item[] items = new Item[8];
        try
        {
            for (int i = 0; i < itemPrices.length; i++)
            {
                itemPriceValues[i] = Integer.parseInt(itemPrices[i]);
                itemCalorieValues[i] = Integer.parseInt(itemCalories[i]);
            }    
        } catch (Exception ex)
        {
            view.setFeedbackLblText("Error creating vending machine.");
            return false;
        }
        
        
        //error checking
        if (itemNames.length != 8 || itemPrices.length != 8 || itemCalories.length != 8)
        {
            view.setFeedbackLblText("Error creating vending machine.");
            return false;
        }
        else
        {
            for (int i = 0; i < itemNames.length; i++)
            {
                items[i] = new Item(itemNames[i], itemPriceValues[i], itemCalorieValues[i]);
            }
            currentMachine = new PizzaVendingMachine(machineName, items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7]);
            view.setTestMachineStatus(true);
            view.clearTextFields();
            return true;
        }
    }

    /**
     * Restocks items in the current vending machine
     * @return TRUE if restock is successful
     */
    public boolean restockItems() {
        String itemName = view.getTextFieldText();
        int quantity = Integer.parseInt(view.getTextField2Text());

        try
        {
            Item item = currentMachine.getItem(itemName);

            if (item == null)
            {
                view.clearTextFields();
                return false;
            }

            if (currentMachine.stockItem(item, quantity))
            {
                view.clearTextFields();
                return true;
            }
            else
            {
                view.clearTextFields();
                return false;
            }
        }
        catch (Exception e)
        {
            view.clearTextFields();
            return false;
        }  
    }

    /**
     * Restocks change into the current machine
     * @return TRUE if restock is successful
     */
    public boolean restockChange() {
        try
        {
            int denominationValue = Integer.parseInt(view.getTextFieldText());
            int quantity = Integer.parseInt(view.getTextField2Text());

            if (currentMachine.stockChangeDispenser(denominationValue, quantity))
            {
                view.clearTextFields();
                return true;    
            }
            else
            {
                view.clearTextFields();
                return false;
            }       
        }
        catch (Exception e)
        {
            view.clearTextFields();
            return false;
        } 
    }

    /**
     * Sets the price of an item in the current machine
     * @return TRUE if setting is successful
     */
    public boolean setItemPrice() {
        String itemName = view.getTextFieldText();
        int newPrice = Integer.parseInt(view.getTextField2Text());

        try
        {
            if(currentMachine.setItemPrice(itemName, newPrice))
            {
                view.clearTextFields();
                return true;    
            }
            else
            {
                view.clearTextFields();
                return false;
            }
        }
        catch (Exception e)
        {
            view.clearTextFields();
            return false;
        }  
    }

    /**
     * Removes a slot from the current machine
     * @return TRUE if removal is successful
     */
    public boolean removeSlot() {
        String itemName = view.getTextFieldText();

        try
        {
            if (currentMachine.removeSlot(itemName))
            {
                view.clearTextFields();
                return true;
            }
            else
            {
                view.clearTextFields();
                return false;
            }
        }
        catch (Exception e)
        {
            view.clearTextFields();
            return false;
        }  
    }

    /**
     * Collects the payments from the current machine
     * @return the payments received by the machine
     */
    public String collectPayment() {
        return Integer.toString(currentMachine.collectPayment());
    }

    /**
     * Gets the transaction summary from the current machine
     * @return the transaction summary of the machine
     */
    public ArrayList<String> getTransactionSummary() {
        ArrayList<String> transactionSummary = new ArrayList<>();

        int i = 1;
        int j = 1;

        transactionSummary.add("Starting Inventory since last restocking:");

        for (String item : currentMachine.getStartingInventory())
        {
            transactionSummary.add(item);
        }

        transactionSummary.add(" ");

        transactionSummary.add("Ending Inventory since last restocking:");

        for (Slot slot : currentMachine.getSlots())
        {
            transactionSummary.add("(Slot " + i + ") " + slot.getSlotName() + " | Stock: " + slot.getItems().size());
            i++;
        }

        transactionSummary.add(" ");

        transactionSummary.add("Items sold since last restocking:");
        
        for (Item item : currentMachine.getItemsPurchased())
        {
            transactionSummary.add(j + ") " + item.getItemName());
            j++;
        }

        transactionSummary.add(" ");
        transactionSummary.add("Total amount collected from sales since last collection:");
        transactionSummary.add(currentMachine.getPaymentReserve() + " PHP");
        transactionSummary.add(" ");


        return transactionSummary;
    }

    /**
     * purchases an item from the current machine
     * @param slot the slot of the item to be purchased
     * @return the resulting prompts of purchasing an item from the machine
     */
    public ArrayList<String> purchaseItem (Slot slot) {
        ArrayList<String> prompts = currentMachine.dispenseItem(slot, paymentsReceived);

        //reset payments received
        paymentsReceived = 0;

        return prompts;
    }

    /**
     * purchases an item from the current machine (used for custom orders)
     * @param slot the slot of the item to be purchased
     * @return the resulting prompts of purchasing an item from the machine
     */
    public ArrayList<String> purchaseItem () {
        PizzaVendingMachine pizzaVendingMachine = (PizzaVendingMachine) currentMachine;

        Slot tempSlot = new Slot("Custom Order", totalItemPrice);

        ArrayList<String> prompts = pizzaVendingMachine.dispenseItem(tempSlot, paymentsReceived);

        //reset payments received
        paymentsReceived = 0;

        this.preparationPrompts = pizzaVendingMachine.prepareOrder(doughSelected, sauceSelected, toppingsSelected);
        this.preparationPictures = pizzaVendingMachine.prepareOrderPictures(doughSelected, sauceSelected, toppingsSelected);

        return prompts;
    }

    /**
     * Getter for the slots in the current machine
     * @return the slots of the current machine
     */
    public ArrayList<Slot> getMachineSlots()
    {
        return currentMachine.getSlots();
    }

    /**
     * Getter for the availability of an item
     * @param slotName the name of the item to be checked
     * @return TRUE if item is still available (i.e. its stock is not 0)
     */
    public boolean getItemAvailability(String slotName)
    {
        for (Slot slot : currentMachine.getSlots())
        {
            if (slot.getSlotName().equals(slotName))
            {
                if (slot.getItems().isEmpty())
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }

        return false;
    }
    
    /**
     * Gets the payments received by the vending feature
     * @return the payments received
     */
    public int getPaymentsReceived() {
        return paymentsReceived;
    }

    /**
     * Updates the payments received by the vending feature by the amount specified
     * @param amount the amount to be added to the payments received
     */
    public void updatePaymentsReceived(int amount) {
        this.paymentsReceived += amount;
    }

    /**
     * Gets the type of vending machine the current machine is
     * @return TRUE if current machine is a special vending machine
     */
    public boolean isCurrentMachineSpecial() {
        if (this.currentMachine instanceof SpecialVendingMachine) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gives the time of item an ingredient is
     * @param slot the slot to be checked
     * @return 1 - Dough; 2 - Sauce; 3 - Topping
     */
    public int selectIngredient(Slot slot) {

        if (currentMachine instanceof PizzaVendingMachine)
        {
            PizzaVendingMachine pizzaVendingMachine = (PizzaVendingMachine) currentMachine;
            
            for (Item dough : pizzaVendingMachine.getDoughTypes())
            {
                if (dough.getItemName().equals(slot.getSlotName()))
                {
                    if (doughSelected != null)
                    {
                        for (Item doughType : pizzaVendingMachine.getDoughTypes())
                        {
                            if(doughSelected.equals(doughType.getItemName()))
                            {
                                totalItemPrice -= doughType.getItemPrice();
                            }
                        }
                    }

                    totalItemPrice += slot.getItemPrice();

                    doughSelected = slot.getSlotName();

                    return 1;
                }
            }   
            
            for (Item sauce : pizzaVendingMachine.getSauces())
            {
                if (sauce.getItemName().equals(slot.getSlotName()))
                {
                    if (sauceSelected == null)
                    {
                        totalItemPrice += slot.getItemPrice();
                    }
                    
                    sauceSelected = slot.getSlotName();

                    return 2;
                }
            }

            for (Item topping : pizzaVendingMachine.getToppings())
            {
                if (topping.getItemName().equals(slot.getSlotName()))
                {
                    totalItemPrice += slot.getItemPrice();

                    toppingsSelected.add(slot.getSlotName());
                    return 3;
                }
            }
        }

        return 0;
        
    }
    
    /**
     * Gets the dough type selected
     * @return the dough type selected
     */
    public String getDoughSelected() {
        return doughSelected;
    }

    /**
     * Gets the sauce type selected
     * @return the sauce type selected
     */
    public String getSauceSelected() {
        return sauceSelected;
    }

    /**
     * Gets the toppings selected
     * @return the toppings selected
     */
    public ArrayList<String> getToppingsSelected() {
        return toppingsSelected;
    }
    
    /**
     * Gets the total price of the custom item
     * @return the total price of te custom item
     */
    public int getTotalItemPrice() {
        return totalItemPrice;
    }

    /**
     * Clears variables associated with special vending machine custom orders
     */
    public void clearSpecialVendingMachine() {
        doughSelected = null;
        sauceSelected = null;

        toppingsSelected = null;
        toppingsSelected = new ArrayList<>();

        totalItemPrice = 0;

        preparationPrompts = null;
        preparationPrompts = new ArrayList<>(); //could also use .clear()

        preparationPictures = null;
        preparationPictures = new ArrayList<>();
    }

    /**
     * Gets the preparation prompts of a custom order
     * @return the preparation prompts of a custom order
     */
    public ArrayList<String> getPreparationPrompts() {
        return preparationPrompts;
    }

    /**
     * Gets the preparation pictures associated with a custom order
     * @return the prepataion pictures associated with a custom order
     */
    public ArrayList<String> getPreparationPictures() {
        return preparationPictures;
    }
}
