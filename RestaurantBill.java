/**  PE10.19 RestaurantBill       Written by:  Jenifer Nguyen

       Date:  July 20, 2022

       Description: This program creates a graphical application that produces a restaurant bill.
       It provides buttons for five popular menu items as well as a reset button that clears everything.
       It shows the subtotal, tax, a suggested tip that is a percentage of the subtotal, and the total bill.
       The tip can be modified by the user.
**/

//import javafx packages
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
   Create an abstract class with a method that must be overriden
   to save lines of code later
*/
abstract class Repeat
{
   public void adjustTaxTipTotal()
   {
      //nothing happens -- must be overriden
   }//end adjustTotal

}//end Repeat

   /**
      Contains an introductory message explaining what the program does and who wrote it
   */

public class RestaurantBill extends Application
{
   //create instance variables and initialize them
   private double subtotal = 0;
   private double tax = 0;
   private double tip = 0;
   private double total = 0;

   //create constants -- tax/suggested tip rates and menu prices
   private static final double TAX_RATE = 0.08;
   private static final double S_TIP_RATE = 0.15;
   private static final double PIZZA_PRICE = 9.45;
   private static final double SALAD_PRICE = 5.45;
   private static final double CHEESY_BREAD_PRICE = 5.25;
   private static final double REG_DRINK_PRICE = 2.25;
   private static final double LG_DRINK_PRICE = 2.55;
   
   //start the GUI
   public void start(Stage stage1)
   {
      Pane root = createRootPane();
      Scene scene1 = new Scene(root);
      stage1.setScene(scene1);
      stage1.setTitle("Blaze Pizza");
      stage1.show();
   }//end start
   
   /*
      Create the components of the GUI 
      and set up the functionality and layout of the components
      @return pane1 the vertical box pane containing the functioning GUI components
   */
   private Pane createRootPane()
   {      
      //create the labels and their corresponding text fields
      
      Label subtotalLabel = new Label("Subtotal:");   //makes a new label
      TextField subtotalField = new TextField(""); //makes a new, blank text field below the corresponding label
      subtotalField.setEditable(false);   //prohibits the user from directly editing the value in the field
      
      Label taxLabel = new Label("Tax:");
      TextField taxField = new TextField("");
      taxField.setEditable(false);
      
      //make a tip field that allows for the user to make adjustments
      Label tipLabel = new Label("Suggested tip:");
      TextField tipField = new TextField("");
      
      Label totalLabel = new Label("Total:");
      TextField totalField = new TextField("");
      totalField.setEditable(false);

      //add menu item buttons
      Button addPizzaButton = new Button("BYO 11-inch pizza ($9.45)");
      Button addSaladButton = new Button("Side Salad ($5.45)");
      Button addCheesyBreadButton = new Button("Cheesy bread ($5.25)");
      Button addRegDrinkButton = new Button("Regular drink ($2.25)");
      Button addLgDrinkButton = new Button("Large drink ($2.55)");
      Button addResetButton = new Button("Reset");
      
      //create the results area and set its dimensions
      TextArea resultsArea = new TextArea();
      final int ROW_COUNT = 20;     //set the row count, or height of the window
      final int COLUMN_COUNT = 25;  //set the column count, or length of the window
      resultsArea.setPrefRowCount(ROW_COUNT);
      resultsArea.setPrefColumnCount(COLUMN_COUNT);
      resultsArea.setEditable(false);
      
      /*
         Create the pane displaying the objects vertically in the order listed:
         -Labels and their corresponding TextFields
         -Buttons
         -The results area where the chosen items and their prices are listed
      */
      Pane pane1 = new VBox(subtotalLabel, subtotalField,
         taxLabel, taxField, tipLabel, tipField, totalLabel, totalField,
         addPizzaButton, addSaladButton, addCheesyBreadButton, addRegDrinkButton, addLgDrinkButton,
         addResetButton, resultsArea);
         
      resultsArea.appendText(introMessage());   
      /*
         Use the abstract class declared above 
         to create a method within the createRootPane method
         and avoid repeating completely identical lines of code later on
      */
      Repeat r = new Repeat()
      {
         /**
            Calculates the tax, tip, and total bill,
            enters the results in the corresponding fields,
            and formats them
         */
         public void adjustTaxTipTotal()
         {
            tax = subtotal * TAX_RATE; //calculate the tax
            
            /* 
               Enter the amount into the text field, 
               format it with a dollar sign($) 
               and rounding to the nearest hundredth
            */
            taxField.setText(String.format("$%.2f%n", tax));
            
            /*
               Calculate, format, and enter the tip into the text field
            */
            tip = subtotal * S_TIP_RATE;
            
            //round the tip to avoid round-off errors that would show when the user manually adjusts the tip
            tip = Math.round(tip * 100.0) / 100.0; 
            tipField.setText(String.format("$%.2f%n", tip));
         
            //calculate, format, and enter the total into the text field
            total = subtotal + tax + tip;
            totalField.setText(String.format("$%.2f%n", total));
         }//end adjustTotal
      };//end Repeat
      
      
      /*
         Add functionality to the buttons
      */
      
      //add pizza button functionality
      addPizzaButton.setOnAction(event ->
      {
         //calculate, format, and enter the subtotal into the text field
         subtotal += PIZZA_PRICE;
         subtotalField.setText(String.format("$%.2f%n", subtotal));

         r.adjustTaxTipTotal();  //calculates, formats, and enters the tax, tip, and total
         
         //add the item and amount to the results area
         resultsArea.appendText(String.format("%-30s", "BYO 11-inch pizza") + String.format("$%.2f%n", PIZZA_PRICE));
           
      });//end addPizzaButton.setOnAction
      
      //add salad button functionality
      addSaladButton.setOnAction(event ->
      {
         subtotal += SALAD_PRICE;
         subtotalField.setText(String.format("$%.2f%n", subtotal));
         
         r.adjustTaxTipTotal();
         
         resultsArea.appendText(String.format("%-36s", "Side Salad") + String.format("$%.2f%n", SALAD_PRICE)); 
      });//end addSaladButton.setOnAction
      
      //add cheesy bread button functionality
      addCheesyBreadButton.setOnAction(event ->
      {
         subtotal += CHEESY_BREAD_PRICE;
         subtotalField.setText(String.format("$%.2f%n", subtotal));
         
         r.adjustTaxTipTotal();
         
         resultsArea.appendText(String.format("%-33s", "Cheesy Bread") + String.format("$%.2f%n", CHEESY_BREAD_PRICE));
      });//end addCheesyBreadButton.setOnAction
      
      //add regular drink button functionality
      addRegDrinkButton.setOnAction(event ->
      {
         subtotal += REG_DRINK_PRICE;
         subtotalField.setText(String.format("$%.2f%n", subtotal));
         
         r.adjustTaxTipTotal();
         
         resultsArea.appendText(String.format("%-34s", "Regular Drink") + String.format("$%.2f%n", REG_DRINK_PRICE));
      });//end addRegDrinkButton.setOnAction
      
      //add large drink button functionality
      addLgDrinkButton.setOnAction(event ->
      {
         subtotal += LG_DRINK_PRICE;
         subtotalField.setText(String.format("$%.2f%n", subtotal));
         
         r.adjustTaxTipTotal();
         
         resultsArea.appendText(String.format("%-35s", "Large Drink") + String.format("$%.2f%n", LG_DRINK_PRICE));
      });//end addLgDrinkButton.setOnAction
      
      //add reset button functionality
      addResetButton.setOnAction(event ->
      {
         //clears the results area
         resultsArea.clear();
         
         //set all of the amounts to 0
         subtotal = 0;
         subtotalField.setText(String.format("$%.2f%n", subtotal));
         
         r.adjustTaxTipTotal();
      });//end addLgDrinkButton.setOnAction

      
      //allows the tip to be changed, with the total automatically adjusting to the change
      tipField.setOnAction(event ->
      {
         String tipText = tipField.getText();   //reads user input in the text field
         
         //takes off the dollar sign so that the number can be read by parseDouble
         if(tipText.charAt(0) == '$')
         {
            tipText = tipText.substring(1);
         }//end if
         
         tip = Double.parseDouble(tipText);  //get the number from the string and convert to a double
         tipField.setText(String.format("$%.2f%n", tip));   //formats the tip again
         
         total = subtotal + tax + tip; //adjust the total
         totalField.setText(String.format("$%.2f%n", total));  //updates the total in the field
      });//end tipField.setOnAction

      return pane1;
   }//end createRootPane method
   
   /**
      Contains an introductory message explaining what the program does and who wrote it
      @return the introductory message
   */
   public String introMessage()
   {
      String str = "";
      str += "This program is a restaurant bill application.\n";
      str += "There are buttons for five popular menu items.\n";
      str += "There's also a reset button that clears everything.\n";
      str += "The bill includes the following:\n";
      str += "-Subtotal\n";
      str += "-Tax\n";
      str += "-A suggested tip that is 15% of the subtotal\n";
      str += "-The total bill\n\n";
      str += "NOTE: To change the tip,\n"; 
      str += "type in the new amount and hit \'Enter\'.\n\n";
      str += "Written by: Jenifer Nguyen\n\n";
      str += "To clear this message, hit the \'Reset\' button.\n\n";
      
      return str;
      
   }//end introMessage
}//end RestaurantBill class
