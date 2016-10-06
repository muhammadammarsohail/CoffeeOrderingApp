
package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p/>
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity < 100) {
            quantity = quantity + 1;
        } else {
            Toast.makeText(this, "You could not have more than 100 Coffees!", Toast.LENGTH_LONG).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {

        if(quantity > 1) {
            quantity = quantity - 1;
        } else {
            Toast.makeText(this, "You could not have less than 1 Coffee!", Toast.LENGTH_LONG).show();
        }

        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);



        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int pricePerCup = 23;

        // add 5 rupees if the user wants whipped cream

        if (addWhippedCream) {
            pricePerCup += 5;
        }

        // add 8 rupees if the user wants chocolate

        if (addChocolate) {
            pricePerCup += 8;
        }

        // calculate final price of the order

        int price = quantity * pricePerCup;
        return price;
    }

    /**
     * prepares full order summary
     *
     * @param price
     * @param addWhippedCream is whether or not the user want whipped cream topping
     * @param addChocolate is whether or not the user want chocolate topping
     *
     * @return order summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String orderSummary = "Name : " + name;
        orderSummary += "\nAdd Whipped Cream? " + addWhippedCream;
        orderSummary += "\nAdd Chocolate? " + addChocolate;
        orderSummary += "\nQuantity : " + quantity;
        orderSummary += "\nTotal : Rs." + price;
        orderSummary += "\nThanks for ordering!";
        return orderSummary;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayOrderDetails(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}