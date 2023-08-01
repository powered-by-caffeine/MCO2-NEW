public class Denomination 
{
    private final int value;
    private int stock;

    /**
     * Constructor for the denomination
     * @param value the value of the denomination
     * @param stock the remaining stock of the denomination
     */
    public Denomination (int value, int stock)
    {
        this.value = value;
        this.stock = stock;
    }

    /** 
     * gets the denomination's value
     */
    public int getValue() 
    {
        return value;
    }

    /**
     * gets the denomination's stock
     * @return the stock
     */
    public int getStock() 
    {
        return stock;
    }

    /**
     * adds to the stock of the denomination
     * @param amount the amount to be added to the stock
     */
    public void addStock (int amount)
    {
        this.stock += amount;
    }

    /**
     * reduces the stock from the denomination
     * @param amount the amount to be reduced from the stock
     */
    public void reduceStock (int amount)
    {
        this.stock -= amount;
    }

    
}
