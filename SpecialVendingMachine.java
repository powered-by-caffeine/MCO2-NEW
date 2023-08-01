

import java.util.ArrayList;

public interface SpecialVendingMachine {
    /**
     * Provides the prompts associated with the prepation of a custom order
     * @param doughType dough type of the order
     * @param sauce sauce of the order
     * @param toppings toppings of the order
     * @return the prompts associated with the prepation of a custom order
     */
    public ArrayList<String> prepareOrder(String doughType, String sauce, ArrayList<String> toppings);

    /**
     * Provides the images associated with the prepation of a custom order
     * @param doughType dough type of the order
     * @param sauce sauce of the order
     * @param toppings toppings of the order
     * @return the images associated with the prepation of a custom order
     */
    public ArrayList<String> prepareOrderPictures(String doughType, String sauce, ArrayList<String> toppings);
}
