import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

interface Callable {
    void call();
}

public class View {
    private boolean testMachineStatus;
    private boolean createMachineSuccess;

    private Controller controller;

    private JTextField itemNames[];
    private JTextField itemPrices[];
    private JTextField itemCalories[];
    
    private JTextField textField;
    private JLabel feedbackLbl;
    private JTextField textField2;

    /**
     * Constructor for the view
     * @param controller the controller that the view will interact with
     */
    public View(Controller controller) { 
        this.controller = controller;

        this.testMachineStatus = false;
        this.createMachineSuccess = false;

        this.textField = new JTextField();
        this.feedbackLbl = new JLabel();

        mainMenu();
        
    }

    /**
     * The main menu of the program
     */
    private void mainMenu() 
    {
        JFrame mainFrame = new JFrame("Vending Machine Factory Simulator");

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Add a cool image
        ImageIcon imageIcon = loadImageFromURL("https://media.architecturaldigest.com/photos/5da0c37126455c000815e404/1:1/w_2000,h_2000,c_limit/11-Sony_A7Riii_201903130415_0076_3000px.jpg");
        JLabel imageLabel = new JLabel(resizeImage(imageIcon, 200 *2 , 150 * 2));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Add a menu label
        JLabel titleLabel = new JLabel("VENDING MACHINE FACTORY SIMULATOR");
        //JLabel menuLabel = new JLabel("MAIN MENU");

        //Button for creating a vending machine
        JButton createMachine = new JButton("Create a Vending Machine");
        createMachine.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createMachine();
                mainFrame.dispose();
            }
        });
        
        
        //Button for testing the vending machine created
        JButton testMachine = new JButton("Test a Vending Machine ( " + (testMachineStatus ? "Available" : "Not yet Available") + " )");
        testMachine.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (testMachineStatus)
                {
                    testMachineChoice();
                    mainFrame.dispose();   
                }
                
            }
        });
        
        //Button for exiting the program
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0); //Ends the program (0 indicates a successful termination)
            }
        });

        //Center the buttons
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createMachine.setAlignmentX(Component.CENTER_ALIGNMENT);
        testMachine.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Add the image to the frame
        mainFrame.add(imageLabel);

        
        //Add the buttons to the panel
        buttonPanel.add(titleLabel);
        //buttonPanel.add(new JLabel(" "));
        //buttonPanel.add(menuLabel);
        buttonPanel.add(new JLabel(" "));
        buttonPanel.add(new JLabel(" "));
        buttonPanel.add(createMachine);
        buttonPanel.add(new JLabel(" "));
        buttonPanel.add(testMachine);
        buttonPanel.add(new JLabel(" "));
        buttonPanel.add(exitButton);

        //Add the panel to the frame
        mainFrame.add(buttonPanel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * Provides choices for testing
     */
    private void testMachineChoice()
    {
        JFrame mainFrame = new JFrame("Test Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Label for instructing the user
        JLabel instructions = new JLabel("What do you want to do?");
        
        JButton testVending = new JButton("Test the Vending Features");
        testVending.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.isCurrentMachineSpecial())
                {
                    testSpecialMachineChoice();
                }
                else
                {
                    testRegularVending();
                }
                mainFrame.dispose();
            }
        });

        JButton testMaintenance = new JButton("Test the Maintenance Features");
        testMaintenance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                maintainVendingMachineChoice();
                mainFrame.dispose();
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        //Center the components
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        testVending.setAlignmentX(Component.CENTER_ALIGNMENT);
        testMaintenance.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add components to panel
        panel.add(instructions);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(testVending);
        panel.add(new JLabel(" "));
        panel.add(testMaintenance);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        
        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * Provides choices for machine maintenance
     */
    private void maintainVendingMachineChoice()
    {
        JFrame mainFrame = new JFrame("Maintain Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Label for instructing the user
        JLabel instructions = new JLabel("What do you want to do?");
        
        JButton restockItems = new JButton("Restock Items");
        restockItems.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                restockItems();
                mainFrame.dispose();
            }
        });

        JButton restockChange = new JButton("Restock Change");
        restockChange.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                restockChange();
                mainFrame.dispose();
            }
        });

        JButton setItemPrice = new JButton("Set Item Price");
        setItemPrice.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setItemPrice();
                mainFrame.dispose();
            }
        });

        JButton printSummary = new JButton("Print Transaction Summary");
        printSummary.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                printSummary();
                mainFrame.dispose();
            }
        });

        JButton collectPayment = new JButton("Collect Payment");
        collectPayment.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                collectPayment();
                mainFrame.dispose();
            }
        });

        JButton removeItems = new JButton("Remove Slot");
        removeItems.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                removeSlot();
                mainFrame.dispose();
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        //Center the components
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        restockItems.setAlignmentX(Component.CENTER_ALIGNMENT);
        restockChange.setAlignmentX(Component.CENTER_ALIGNMENT);
        setItemPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
        printSummary.setAlignmentX(Component.CENTER_ALIGNMENT);
        collectPayment.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeItems.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add components to panel
        panel.add(instructions);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(restockItems);
        panel.add(new JLabel(" "));
        panel.add(restockChange);
        panel.add(new JLabel(" "));
        panel.add(setItemPrice);
        panel.add(new JLabel(" "));
        panel.add(printSummary);
        panel.add(new JLabel(" "));
        panel.add(collectPayment);
        panel.add(new JLabel(" "));
        panel.add(removeItems);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        
        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for collecting payments from the current machine
     */
    private void collectPayment() {
        JFrame mainFrame = new JFrame("Collect Payment");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        JLabel label = new JLabel(controller.collectPayment() + " PHP collected successfully!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

    
        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        panel.add(label);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);

        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for restocking items into the machine
     */
    private void restockItems() {
        JFrame mainFrame = new JFrame("Restock Items");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        JLabel label = new JLabel("Enter Item name");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.textField = new JTextField();
        this.textField.setColumns(10);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("Enter item quantity");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.textField2 = new JTextField();
        this.textField2.setColumns(10);
        textField2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel feedbackLbl = new JLabel(" ");
        feedbackLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.restockItems()) {
                    popUpWindow("Restock Items", "Item stocked successfully");
                    mainFrame.dispose();
                }
                else
                {
                    feedbackLbl.setText("Error restocking item");
                }
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(new JLabel(" "));
        panel.add(label2);
        panel.add(textField2);
        panel.add(new JLabel(" "));
        panel.add(submitButton);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        panel.add(feedbackLbl);

        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for restocking change into the machine
     */
    private void restockChange() {
        JFrame mainFrame = new JFrame("Restock Change");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        JLabel label = new JLabel("Enter denomination value");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.textField = new JTextField();
        this.textField.setColumns(10);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("Enter denomination quantity");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.textField2 = new JTextField();
        this.textField2.setColumns(10);
        textField2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel feedbackLbl = new JLabel(" ");
        feedbackLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.restockChange()) {
                    popUpWindow("Restock change", "Denomination stocked successfully");
                    mainFrame.dispose();
                }
                else
                {
                    feedbackLbl.setText("Error restocking denomination");
                }
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(new JLabel(" "));
        panel.add(label2);
        panel.add(textField2);
        panel.add(new JLabel(" "));
        panel.add(submitButton);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        panel.add(feedbackLbl);

        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for setting the price of an item in the machine
     */
    private void setItemPrice() {
        JFrame mainFrame = new JFrame("Set Item Price");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        JLabel label = new JLabel("Enter Item Name");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.textField = new JTextField();
        this.textField.setColumns(10);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("Enter New Item Price");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.textField2 = new JTextField();
        this.textField2.setColumns(10);
        textField2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel feedbackLbl = new JLabel(" ");
        feedbackLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.setItemPrice()) {
                    popUpWindow("Set Item Price", "Item price set successfully");
                    mainFrame.dispose();
                }
                else
                {
                    feedbackLbl.setText("Error setting item price");
                }
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(new JLabel(" "));
        panel.add(label2);
        panel.add(textField2);
        panel.add(new JLabel(" "));
        panel.add(submitButton);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        panel.add(feedbackLbl);

        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for printing the transaction summary kept by the machine
     */
    private void printSummary() {
        JFrame mainFrame = new JFrame("Print Transaction Summary");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        
        for (String string : controller.getTransactionSummary())
        {
            JLabel label = new JLabel(string);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
        }

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        panel.add(new JLabel(" "));
        panel.add(returnToMain);

        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for removing a slot from the machine
     */
    private void removeSlot() {
        JFrame mainFrame = new JFrame("Remove Slot");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        JLabel label = new JLabel("Enter Item/Slot Name to Remove");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.textField = new JTextField();
        this.textField.setColumns(10);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel feedbackLbl = new JLabel(" ");
        feedbackLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.removeSlot()) {
                    popUpWindow("Remove Slot", "Slot deleted successfully");
                    mainFrame.dispose();
                }
                else
                {
                    feedbackLbl.setText("Error deleting slot");
                }
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(new JLabel(" "));
        panel.add(submitButton);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        panel.add(feedbackLbl);

        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for testing a regular vending machine
     */
    private void testRegularVending() {
        JFrame mainFrame = new JFrame("Test Regular Vending Machine");
        
        JLabel paymentsLabel = new JLabel("Inserted: " + controller.getPaymentsReceived() + " PHP");
        paymentsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());

        //Panel for holding the slot components
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel instructions1 = new JLabel("1. Insert denominations into the machine");
        JLabel instructions2 = new JLabel("2. Select the item you'd like to purchase");
        instructions1.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions2.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (Slot slot : controller.getMachineSlots())
        {
            JButton button = new JButton(slot.getSlotName() + " | " + slot.getItemPrice() + " PHP ( " + (controller.getItemAvailability(slot.getSlotName()) ? "Available" : "OUT OF STOCK") + " )");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (controller.getItemAvailability(slot.getSlotName())) //if the item is available
                    {
                        ArrayList<String> prompts = controller.purchaseItem(slot);

                        if (prompts == null)
                        {
                            popUpWindow("Purchase Error", "Purchase Error. Returning change...");
                            mainFrame.dispose();
                        }
                        else
                        {
                            popUpWindow("Purchase Success", prompts);
                            mainFrame.dispose();
                        }

                    }
                }
            });

            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(button);
            panel.add(new JLabel(" "));
        }

        //Panel for holding the denomination components
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        Callable updatePaymentLabel = () -> paymentsLabel.setText("Inserted: " + controller.getPaymentsReceived() + " PHP");
        JButton button1 = new JButton("1 PHP");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(1);
                updatePaymentLabel.call();
            }   
        });

        JButton button2 = new JButton("5 PHP");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(5);
                updatePaymentLabel.call();
            }   
        });

        JButton button3 = new JButton("10 PHP");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(10);
                updatePaymentLabel.call();
            }   
        });

        JButton button4 = new JButton("20 PHP");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(20);
                updatePaymentLabel.call();
            }   
        });

        JButton button5 = new JButton("50 PHP");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(50);
                updatePaymentLabel.call();
            }   
        });

        JButton button6 = new JButton("100 PHP");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(100);
                updatePaymentLabel.call();
            }   
        });

        JButton button7 = new JButton("500 PHP");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(500);
                updatePaymentLabel.call();
            }   
        });

        panel2.add(button1);
        panel2.add(new JLabel(" "));
        panel2.add(button2);
        panel2.add(new JLabel(" "));
        panel2.add(button3);
        panel2.add(new JLabel(" "));
        panel2.add(button4);
        panel2.add(new JLabel(" "));
        panel2.add(button5);
        panel2.add(new JLabel(" "));
        panel2.add(button6);
        panel2.add(new JLabel(" "));
        panel2.add(button7);

        //Panel for holding other components
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        this.feedbackLbl = new JLabel(" ");
        feedbackLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        
        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                popUpWindow("Exiting vending", "Change returned.");
                mainFrame.dispose();
            }
        });

        JPanel fillerPanel = new JPanel();
        fillerPanel.setLayout(new BoxLayout(fillerPanel, BoxLayout.Y_AXIS));
        fillerPanel.add(new JLabel("                           "));

        JPanel fillerPanel2 = new JPanel();
        fillerPanel2.setLayout(new BoxLayout(fillerPanel2, BoxLayout.Y_AXIS));
        fillerPanel2.add(new JLabel("                           "));

        panel3.add(instructions1);
        panel3.add(instructions2);
        panel3.add(new JLabel(" "));
        panel3.add(paymentsLabel);
        panel3.add(new JLabel(" "));
        panel3.add(this.feedbackLbl);
        panel3.add(returnToMain);

        panel.setSize(250, 500);
        panel2.setSize(250, 500);
        panel3.setSize(250, 500);
        
        mainFrame.add(panel);
        mainFrame.add(fillerPanel);
        mainFrame.add(panel2);
        mainFrame.add(fillerPanel2);
        mainFrame.add(panel3);

        mainFrame.pack();

        //mainFrame.setSize(1500, 500);
        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * Provides choices for testing a special vending machine
     */
    private void testSpecialMachineChoice()
    {
        JFrame mainFrame = new JFrame("Test Special Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Label for instructing the user
        JLabel instructions = new JLabel("What do you want to do?");
        
        JButton testVending = new JButton("Purchase an ingredient");
        testVending.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                testSpecialVending();
                mainFrame.dispose();
            }
        });

        JButton testMaintenance = new JButton("Purchase a custom order");
        testMaintenance.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                testSpecialPreparing();             
                mainFrame.dispose();
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        //Center the components
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        testVending.setAlignmentX(Component.CENTER_ALIGNMENT);
        testMaintenance.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add components to panel
        panel.add(instructions);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(testVending);
        panel.add(new JLabel(" "));
        panel.add(testMaintenance);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        
        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for testing the vending features of a special vending machine
     */
    private void testSpecialVending() {
        JFrame mainFrame = new JFrame("Test Special Vending Machine");
        
        JLabel paymentsLabel = new JLabel("Inserted: " + controller.getPaymentsReceived() + " PHP");
        paymentsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());

        //Panel for holding the slot components
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel instructions1 = new JLabel("1. Insert denominations into the machine");
        JLabel instructions2 = new JLabel("2. Select the item you'd like to purchase");
        JLabel instructions3 = new JLabel("Note that all ingredients are purchased in sellable packs.");
        instructions1.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions2.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions3.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (Slot slot : controller.getMachineSlots())
        {
            JButton button = new JButton(slot.getSlotName() + " | " + slot.getItemPrice() + " PHP ( " + (controller.getItemAvailability(slot.getSlotName()) ? "Available" : "OUT OF STOCK") + " )");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (controller.getItemAvailability(slot.getSlotName())) //if the item is available
                    {
                        ArrayList<String> prompts = controller.purchaseItem(slot);

                        if (prompts == null)
                        {
                            popUpWindow("Purchase Error", "Purchase Error. Returning change...");
                            mainFrame.dispose();
                        }
                        else
                        {
                            popUpWindow("Purchase Success", prompts);
                            mainFrame.dispose();
                        }

                    }
                }
            });

            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(button);
            panel.add(new JLabel(" "));
        }

        //Panel for holding the denomination components
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        Callable updatePaymentLabel = () -> paymentsLabel.setText("Inserted: " + controller.getPaymentsReceived() + " PHP");
        JButton button1 = new JButton("1 PHP");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(1);
                updatePaymentLabel.call();
            }   
        });

        JButton button2 = new JButton("5 PHP");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(5);
                updatePaymentLabel.call();
            }   
        });

        JButton button3 = new JButton("10 PHP");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(10);
                updatePaymentLabel.call();
            }   
        });

        JButton button4 = new JButton("20 PHP");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(20);
                updatePaymentLabel.call();
            }   
        });

        JButton button5 = new JButton("50 PHP");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(50);
                updatePaymentLabel.call();
            }   
        });

        JButton button6 = new JButton("100 PHP");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(100);
                updatePaymentLabel.call();
            }   
        });

        JButton button7 = new JButton("500 PHP");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(500);
                updatePaymentLabel.call();
            }   
        });

        panel2.add(button1);
        panel2.add(new JLabel(" "));
        panel2.add(button2);
        panel2.add(new JLabel(" "));
        panel2.add(button3);
        panel2.add(new JLabel(" "));
        panel2.add(button4);
        panel2.add(new JLabel(" "));
        panel2.add(button5);
        panel2.add(new JLabel(" "));
        panel2.add(button6);
        panel2.add(new JLabel(" "));
        panel2.add(button7);

        //Panel for holding other components
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        this.feedbackLbl = new JLabel(" ");
        feedbackLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        
        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                popUpWindow("Exiting vending", "Change returned.");
                mainFrame.dispose();
            }
        });

        JPanel fillerPanel = new JPanel();
        fillerPanel.setLayout(new BoxLayout(fillerPanel, BoxLayout.Y_AXIS));
        fillerPanel.add(new JLabel("                           "));

        JPanel fillerPanel2 = new JPanel();
        fillerPanel2.setLayout(new BoxLayout(fillerPanel2, BoxLayout.Y_AXIS));
        fillerPanel2.add(new JLabel("                           "));

        panel3.add(instructions1);
        panel3.add(instructions2);
        panel3.add(instructions3);
        panel3.add(new JLabel(" "));
        panel3.add(paymentsLabel);
        panel3.add(new JLabel(" "));
        panel3.add(this.feedbackLbl);
        panel3.add(returnToMain);

        panel.setSize(250, 500);
        panel2.setSize(250, 500);
        panel3.setSize(250, 500);
        
        mainFrame.add(panel);
        mainFrame.add(fillerPanel);
        mainFrame.add(panel2);
        mainFrame.add(fillerPanel2);
        mainFrame.add(panel3);

        mainFrame.pack();

        //mainFrame.setSize(1500, 500);
        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for testing the special vending feature of a special vending machine
     */
    private void testSpecialPreparing() {
        controller.clearSpecialVendingMachine();

        JFrame mainFrame = new JFrame("Test Special Vending Machine");
        
        JLabel paymentsLabel = new JLabel("Inserted: " + controller.getPaymentsReceived() + " PHP");
        paymentsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel doughLabel = new JLabel("Dough: " + controller.getDoughSelected());
        doughLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sauceLabel = new JLabel("Sauce: " + controller.getSauceSelected());
        sauceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel toppingsLabel = new JLabel("Toppings: " + String.join(", ", controller.getToppingsSelected()));
        toppingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel("Total Price: " + controller.getTotalItemPrice());
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());

        //Panel for holding the slot components
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel instructions1 = new JLabel("1. Insert denominations into the machine");
        JLabel instructions2 = new JLabel("2. Select the item you'd like to purchase");
        JLabel instructions3 = new JLabel("Please be patient while waiting for images to load after purchasing an item :)");
        instructions1.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions2.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions3.setAlignmentX(Component.CENTER_ALIGNMENT);

        Callable updateDoughLabel = () -> doughLabel.setText("Dough: " + controller.getDoughSelected());
        Callable updateSauceLabel = () -> sauceLabel.setText("Sauce: " + controller.getSauceSelected());
        Callable updateToppingsLabel = () -> toppingsLabel.setText("Toppings: " + String.join(", ", controller.getToppingsSelected()));
        Callable updateTotalPriceLabel = () -> priceLabel.setText("Total Price: " + controller.getTotalItemPrice());
        for (Slot slot : controller.getMachineSlots())
        {
            JButton button = new JButton(slot.getSlotName() + " | " + slot.getItemPrice() + " PHP ( " + (controller.getItemAvailability(slot.getSlotName()) ? "Available" : "OUT OF STOCK") + " )");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (controller.getItemAvailability(slot.getSlotName())) //if the item is available
                    {
                        switch(controller.selectIngredient(slot))
                        {
                            case 1:
                                updateDoughLabel.call();
                                break;
                            case 2:
                                updateSauceLabel.call();
                                break;
                            case 3:
                                updateToppingsLabel.call();
                                break;
                        }
                    }
                    
                    updateTotalPriceLabel.call();
                }

                
            });

            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(button);
            panel.add(new JLabel(" "));
        }

        //Panel for holding the denomination components
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        Callable updatePaymentLabel = () -> paymentsLabel.setText("Inserted: " + controller.getPaymentsReceived() + " PHP");
        JButton button1 = new JButton("1 PHP");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(1);
                updatePaymentLabel.call();
            }   
        });

        JButton button2 = new JButton("5 PHP");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(5);
                updatePaymentLabel.call();
            }   
        });

        JButton button3 = new JButton("10 PHP");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(10);
                updatePaymentLabel.call();
            }   
        });

        JButton button4 = new JButton("20 PHP");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(20);
                updatePaymentLabel.call();
            }   
        });

        JButton button5 = new JButton("50 PHP");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(50);
                updatePaymentLabel.call();
            }   
        });

        JButton button6 = new JButton("100 PHP");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(100);
                updatePaymentLabel.call();
            }   
        });

        JButton button7 = new JButton("500 PHP");
        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.updatePaymentsReceived(500);
                updatePaymentLabel.call();
            }   
        });

        panel2.add(button1);
        panel2.add(new JLabel(" "));
        panel2.add(button2);
        panel2.add(new JLabel(" "));
        panel2.add(button3);
        panel2.add(new JLabel(" "));
        panel2.add(button4);
        panel2.add(new JLabel(" "));
        panel2.add(button5);
        panel2.add(new JLabel(" "));
        panel2.add(button6);
        panel2.add(new JLabel(" "));
        panel2.add(button7);

        //Panel for holding other components
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        this.feedbackLbl = new JLabel(" ");
        feedbackLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.getDoughSelected() != null && controller.getSauceSelected() != null && controller.getToppingsSelected() != null)
                {
                    ArrayList<String> prompts = controller.purchaseItem();

                    if (prompts == null)
                    {
                        popUpWindow("Purchase Error", "Purchase Error. Returning change...");
                        controller.clearSpecialVendingMachine();
                        mainFrame.dispose();
                    }
                    else
                    {
                        prepareSpecialOrder();
                        popUpWindow("Purchase Success", prompts);
                        mainFrame.dispose();
                    }
                }
            }
        }); 
        
        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.clearSpecialVendingMachine();
                popUpWindow("Exiting vending", "Change returned.");
                mainFrame.dispose();
            }
        });

        JPanel fillerPanel = new JPanel();
        fillerPanel.setLayout(new BoxLayout(fillerPanel, BoxLayout.Y_AXIS));
        fillerPanel.add(new JLabel("                           "));

        JPanel fillerPanel2 = new JPanel();
        fillerPanel2.setLayout(new BoxLayout(fillerPanel2, BoxLayout.Y_AXIS));
        fillerPanel2.add(new JLabel("                           "));

        panel3.add(instructions1);
        panel3.add(instructions2);
        panel3.add(instructions3);
        panel3.add(new JLabel(" "));
        panel3.add(doughLabel);
        panel3.add(new JLabel(" "));
        panel3.add(sauceLabel);
        panel3.add(new JLabel(" "));
        panel3.add(toppingsLabel);
        panel3.add(new JLabel(" "));
        panel3.add(priceLabel);
        panel3.add(new JLabel(" "));
        panel3.add(paymentsLabel);
        panel3.add(new JLabel(" "));
        panel3.add(purchaseButton);
        panel3.add(new JLabel(" "));
        panel3.add(this.feedbackLbl);
        panel3.add(returnToMain);

        panel.setSize(250, 500);
        panel2.setSize(250, 500);
        panel3.setSize(250, 500);
        
        mainFrame.add(panel);
        mainFrame.add(fillerPanel);
        mainFrame.add(panel2);
        mainFrame.add(fillerPanel2);
        mainFrame.add(panel3);

        mainFrame.pack();

        //mainFrame.setSize(1500, 500);
        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     *  Illustrates preparing a custom order for the special vending machine
     */
    private void prepareSpecialOrder()
    {
        ArrayList<String> preparationPrompts = controller.getPreparationPrompts();
        ArrayList<String> preparationPictures = controller.getPreparationPictures();

        int[] currentIndex = {0};
        
        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex[0] < preparationPrompts.size()) {
                    popUpWindow("Assembling Order...", preparationPictures.get(currentIndex[0] ), preparationPrompts.get(currentIndex[0]));
                    currentIndex[0] ++;
                }
            }
        }).start();

    }

    /**
     * Opens a window with a prompt
     * @param windowName the window's name
     * @param prompts the prompts to be displayed
     */
    private void popUpWindow(String windowName, ArrayList<String> prompts)
    {
        JFrame popUp = new JFrame(windowName);

        popUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        popUp.setLayout(new FlowLayout());
        popUp.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        for (String prompt : prompts)
        {
            JLabel label = new JLabel(prompt);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);    

            panel.add(label);
        }
        

    
        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                popUp.dispose(); //close the window
            }
        });

        panel.add(new JLabel(" "));
        panel.add(returnToMain);

        popUp.add(panel);

        popUp.pack();

        // Center the window on the screen
        popUp.setLocationRelativeTo(null);

        popUp.setVisible(true);
    }

    /**
     * Opens a window with a prompt
     * @param windowName the window's name
     * @param prompts the prompt to be displayed
     */
    private void popUpWindow(String windowName, String prompt)
    {
        JFrame popUp = new JFrame(windowName);

        popUp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        popUp.setLayout(new FlowLayout());
        popUp.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        JLabel label = new JLabel(prompt);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

    
        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                popUp.dispose(); //close the window
            }
        });

        panel.add(label);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);

        popUp.add(panel);

        popUp.pack();

        // Center the window on the screen
        popUp.setLocationRelativeTo(null);

        popUp.setVisible(true);
    }

    /**
     * Used for preparing orders using the special vending machine
     * @param windowName the name of the window
     * @param imageURL the URL of the image to be presented
     * @param prompt the prompt of the current preparation step
     */
    private void popUpWindow(String windowName, String imageURL, String prompt)
    {
        JFrame popUp = new JFrame(windowName);

        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popUp.setLayout(new FlowLayout());
        popUp.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Label containing the preparation prompt
        JLabel label = new JLabel(prompt);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Image containing the preparation image
        ImageIcon imageIcon = loadImageFromURL(imageURL);
        JLabel imageLabel = new JLabel(resizeImage(imageIcon,200 * 2, 150 * 2));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(new JLabel(" "));
        panel.add(imageLabel);

        popUp.add(panel);

        popUp.pack();

        // Center the window on the screen
        popUp.setLocationRelativeTo(null);

        popUp.setVisible(true);

        //starts a timer that would close the window after the time specified in the delay param
        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                popUp.dispose();
            }
        }).start();
    }

    /**
     * UI for creating a machine
     */
    private void createMachine()
    {
        JFrame mainFrame = new JFrame("Create Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Label for instructing the user
        JLabel instructions = new JLabel("What kind of vending machine would you like to create?");
        
        //Button for creating a Regular Vending Machine
        JButton createRegular = new JButton("Regular Vending Machine");
        createRegular.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createRegularMachineChoice();
                mainFrame.dispose();
            }
        });

        //Button for creating a Special Vending Machine
        JButton createSpecial = new JButton("Special Vending Machine");
        createSpecial.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createSpecialMachineChoice();
                mainFrame.dispose();
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        //Center the components
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        createRegular.setAlignmentX(Component.CENTER_ALIGNMENT);
        createSpecial.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add components to panel
        panel.add(instructions);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(createRegular);
        panel.add(new JLabel(" "));
        panel.add(createSpecial);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        
        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * Provides choices for creating a regular vending machine
     */
    private void createRegularMachineChoice()
    {
        
        JFrame mainFrame = new JFrame("Create Regular Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Label for instructing the user
        JLabel instructions = new JLabel("What kind of vending machine would you like to create?");
        
        //Button for creating from preset
        JButton createPreset = new JButton("Create Preset Vending Machine (Drink Vending Machine)");
        createPreset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.initializeRegularMachinePreset();
                mainMenu();
                mainFrame.dispose();
            }
        });

        //Button for creating manually
        JButton createManual = new JButton("Create Vending Machine Manually");
        createManual.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createRegularMachineManually();
                mainFrame.dispose();
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        if (createMachineSuccess==true) {
            mainMenu(); //return to main menu
            mainFrame.dispose(); //close the window
        }

        //Center the components
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        createPreset.setAlignmentX(Component.CENTER_ALIGNMENT);
        createManual.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add components to panel
        panel.add(instructions);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(createPreset);
        panel.add(new JLabel(" "));
        panel.add(createManual);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        
        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for manually creating a regular vending machine
     */
    private void createRegularMachineManually()
    { 
        this.itemNames = new JTextField[8];
        this.itemPrices = new JTextField[8];
        this.itemCalories = new JTextField[8];
        
        JFrame mainFrame = new JFrame("Create Regular Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setSize(500, 500);

        //Panel for slot labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        for (int i = 1; i <= 8; i++)
        {
            labelPanel.add(new JLabel("Slot " + i + ": "));
            labelPanel.add(new JLabel(" "));
        }

        //Panel for item names
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        namePanel.add(new JLabel("Name"));
        for (int i = 0; i < 8; i++)
        {
            itemNames[i] = new JTextField();
            itemNames[i].setColumns(10);
            itemNames[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            namePanel.add(itemNames[i]);
            namePanel.add(new JLabel(" "));
        }

        //Panel for item Prices
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        pricePanel.add(new JLabel("Price"));
        for (int i = 0; i < 8; i++)
        {
            itemPrices[i] = new JTextField();
            itemPrices[i].setColumns(10);
            itemPrices[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            pricePanel.add(itemPrices[i]);
            pricePanel.add(new JLabel(" "));
        }

        //Panel for item Calories
        JPanel caloriesPanel = new JPanel();
        caloriesPanel.setLayout(new BoxLayout(caloriesPanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        caloriesPanel.add(new JLabel("Calories"));
        for (int i = 0; i < 8; i++)
        {
            itemCalories[i] = new JTextField();
            itemCalories[i].setColumns(10);
            itemCalories[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            caloriesPanel.add(itemCalories[i]);
            caloriesPanel.add(new JLabel(" "));
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        JLabel machineNameLbl = new JLabel("Enter machine name: ");
        this.textField = new JTextField();
        this.textField.setColumns(10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.initializeRegularMachineManual()) {
                    mainMenu();
                    mainFrame.dispose();
                }
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });
        

        panel.add(machineNameLbl);
        panel.add(textField);
        panel.add(submitButton);
        panel.add(new JLabel(" "));
        panel.add(feedbackLbl);
        panel.add(returnToMain);

        mainFrame.add(labelPanel);
        mainFrame.add(namePanel);
        mainFrame.add(pricePanel);
        mainFrame.add(caloriesPanel);
        mainFrame.add(panel);

        if (createMachineSuccess == true) //return to the main menu when machine creation is successful
        {
            mainMenu();
            mainFrame.dispose();
        }

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * Provides choices for creating a special vending machine
     */
    private void createSpecialMachineChoice()
    {
        
        JFrame mainFrame = new JFrame("Create Pizza Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setSize(500, 500);

        //Panel for holding the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another

        //Label for instructing the user
        JLabel instructions = new JLabel("What kind of vending machine would you like to create?");
        
        //Button for creating from preset
        JButton createPreset = new JButton("Create Preset Vending Machine");
        createPreset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.initializeSpecialMachinePreset();
                mainMenu();
                mainFrame.dispose();
            }
        });

        //Button for creating manually
        JButton createManual = new JButton("Create Vending Machine Manually");
        createManual.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createSpecialMachineManually();
                mainFrame.dispose();
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });

        if (createMachineSuccess==true) {
            mainMenu(); //return to main menu
            mainFrame.dispose(); //close the window
        }

        //Center the components
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        createPreset.setAlignmentX(Component.CENTER_ALIGNMENT);
        createManual.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add components to panel
        panel.add(instructions);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(createPreset);
        panel.add(new JLabel(" "));
        panel.add(createManual);
        panel.add(new JLabel(" "));
        panel.add(returnToMain);
        panel.add(new JLabel(" "));
        
        mainFrame.add(panel);

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * UI for manually creating a special vending machine
     */
    private void createSpecialMachineManually()
    { 
        this.itemNames = new JTextField[8];
        this.itemPrices = new JTextField[8];
        this.itemCalories = new JTextField[8];
        
        JFrame mainFrame = new JFrame("Create Special Vending Machine");

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setSize(500, 500);

        //Panel for slot labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        for (int i = 1; i <= 8; i++)
        {
            labelPanel.add(new JLabel("Slot " + i + ": "));
            labelPanel.add(new JLabel(" "));
        }

        //Panel for item names
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        namePanel.add(new JLabel("Name"));
        for (int i = 0; i < 8; i++)
        {
            itemNames[i] = new JTextField();
            itemNames[i].setColumns(10);
            itemNames[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            namePanel.add(itemNames[i]);
            namePanel.add(new JLabel(" "));
        }

        //Panel for item Prices
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        pricePanel.add(new JLabel("Price"));
        for (int i = 0; i < 8; i++)
        {
            itemPrices[i] = new JTextField();
            itemPrices[i].setColumns(10);
            itemPrices[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            pricePanel.add(itemPrices[i]);
            pricePanel.add(new JLabel(" "));
        }

        //Panel for item Calories
        JPanel caloriesPanel = new JPanel();
        caloriesPanel.setLayout(new BoxLayout(caloriesPanel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        caloriesPanel.add(new JLabel("Calories"));
        for (int i = 0; i < 8; i++)
        {
            itemCalories[i] = new JTextField();
            itemCalories[i].setColumns(10);
            itemCalories[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            caloriesPanel.add(itemCalories[i]);
            caloriesPanel.add(new JLabel(" "));
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); //Puts all the buttons on top of one another
        JLabel machineNameLbl = new JLabel("Enter machine name: ");
        this.textField = new JTextField();
        this.textField.setColumns(10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.initializeSpecialMachineManual()) {
                    mainMenu();
                    mainFrame.dispose();
                }
            }
        });

        //Button for returning to main menu
        JButton returnToMain = new JButton("Return");
        returnToMain.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainMenu(); //return to main menu
                mainFrame.dispose(); //close the window
            }
        });


        panel.add(machineNameLbl);
        panel.add(textField);
        panel.add(submitButton);
        panel.add(new JLabel(" "));
        panel.add(feedbackLbl);
        panel.add(returnToMain);

        mainFrame.add(labelPanel);
        mainFrame.add(namePanel);
        mainFrame.add(pricePanel);
        mainFrame.add(caloriesPanel);
        mainFrame.add(panel);

        if (createMachineSuccess == true) //return to the main menu when machine creation is successful
        {
            mainMenu();
            mainFrame.dispose();
        }

        mainFrame.pack();

        // Center the window on the screen
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    /**
     * Setter for the feedback text label
     * @param text the new text
     */
    public void setFeedbackLblText(String text) 
    {
		this.feedbackLbl.setText(text);
	}

    /**
     * Getter for the item names text field
     * @return the item names received by itemNames text fields
     */
    public String[] getItemNamesText() {
        String[] itemNamesText = new String[itemNames.length];

        for (int i = 0; i < itemNames.length; i++)
        {
            itemNamesText[i] = itemNames[i].getText();
        }

        return itemNamesText;
    }

    /**
     * Getter for the item prices text field
     * @return the item prices received by itemPrices text fields
     */
    public String[] getItemPricesText() {
        String[] itemPricesText = new String[itemPrices.length];

        for (int i = 0; i < itemPrices.length; i++)
        {
            itemPricesText[i] = itemPrices[i].getText();
        }

        return itemPricesText;
    }

    /**
     * Getter for the item calories text field
     * @return the item calories received by itemCalories text fields
     */
    public String[] getItemCaloriesText() {
        String[] itemCaloriesText = new String[itemCalories.length];

        for (int i = 0; i < itemCalories.length; i++)
        {
            itemCaloriesText[i] = itemCalories[i].getText();
        }

        return itemCaloriesText;
    }

    /**
     * Gets the text from a text field
     * @return the text from a text field
     */
    public String getTextFieldText() {
        return this.textField.getText();
    }

    /**
     * Gets the text from a text field
     * @return the text from a text field
     */
    public String getTextField2Text() {
        return this.textField2.getText();
    }

    /**
     * Loads the image from a URL
     * @param imageUrl the URL of the image to be loaded
     * @return
     */
    private ImageIcon loadImageFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return new ImageIcon(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Resizes the image to a desired width and height
     * @param imageIcon the image to be resized
     * @param width the width to be resized to
     * @param height the height to be resized to
     * @return the resized image
     */
    private ImageIcon resizeImage(ImageIcon imageIcon, int width, int height) {
        if (imageIcon != null) {
            Image image = imageIcon.getImage();
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        }
        return null;
    }
    
    /**
     * Setter for the status of "Test Machine"
     * @param testMachineStatus the status of "Test Machine"
     */
    public void setTestMachineStatus(boolean testMachineStatus)
    {
        this.testMachineStatus = testMachineStatus;
    }
    
    /**
     * Setter for the status of successful machine creation
     * @param createMachineSuccess successful machine creation
     */
    public void setCreateMachineSuccess(boolean createMachineSuccess) {
        this.createMachineSuccess = createMachineSuccess;
    }

    /**
     * Clears all the text fields in the view
     */
    public void clearTextFields() {

        if (textField != null)
        {
            this.textField.setText("");    
        }
        
        if (textField2 != null)
        {
            this.textField2.setText("");    
        }
        

        if (itemNames != null)
        {
            for (JTextField textField : itemNames)
            {
                textField.setText("");
            }    
        }
        
        if (itemNames != null)
        {
            for (JTextField textField : itemPrices)
            {
                textField.setText("");
            }    
        }
        
        if (itemCalories != null)
        {
            for (JTextField textField : itemPrices)
            {
                textField.setText("");
            }    
        }
        

    }
}
