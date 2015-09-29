package com.industry.drawnavapp.activity;

import java.util.List;

import com.industry.drawnavapp.data.Product;
import com.industry.drawnavapp.data.ShoppingCartHelper;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends BaseActivity {

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	
	private static boolean isIndex = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setTitle("");
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setIcon(android.R.color.transparent);
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
		getActionBar().setCustomView(R.layout.actionbar_layout);
		
		setContentView(R.layout.product_details);
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
		// titles
		// from
		// strings.xml

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
		// strings.xml

		set(navMenuTitles, navMenuIcons);

		List<Product> catalog = ShoppingCartHelper.getCatalog(getResources()); 
		final Product selectedProduct;
		if (isIndex) {
			int productIndex = getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);
			Log.d("ProductDetailsActivity", "Integer from intent is productIndex = "+productIndex);
			selectedProduct = catalog.get(productIndex);
		} else {
			String productTitle = getIntent().getExtras().getString(ShoppingCartHelper.PRODUCT_TITLE);
			Log.d("ProductDetailsActivity", "String from intent is productTitle = "+productTitle);
			selectedProduct = ShoppingCartHelper.getSelectedProduct(productTitle);
		}
		
		// Set the proper image and text   
		ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);   
		productImageView.setImageDrawable(selectedProduct.productImage);   
		
		TextView productTitleTextView = (TextView) findViewById(R.id.TextViewProductTitle);   
		productTitleTextView.setText(selectedProduct.title);   
		TextView productDetailsTextView = (TextView) findViewById(R.id.TextViewProductDetails);   
		productDetailsTextView.setText(selectedProduct.description);       
		TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);   
		productPriceTextView.setText( getString(R.string.unit_dollar) + selectedProduct.price);     
		// Update the current quantity in the cart   
		TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);   
		textViewCurrentQuantity.setText(getString(R.string.currently_in_cart) + ShoppingCartHelper.getProductQuantity(selectedProduct));     

		TextView textViewQuantity = (TextView) findViewById(R.id.textView1);
		textViewQuantity.setText(getString(R.string.quantity));
		
		// Save a reference to the quantity edit text   
		final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);     
		Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
		addToCartButton.setText(getString(R.string.set_quantity));
		
		addToCartButton.setOnClickListener(new OnClickListener() {      
			@Override   
			public void onClick(View v) {       
				// Check to see that a valid quantity was entered     
				int quantity = 0;     
				try {      
					quantity = Integer.parseInt(editTextQuantity.getText().toString());        
					if (quantity < 0) {       
						Toast.makeText(getBaseContext(), "Please enter a quantity of 0 or higher", Toast.LENGTH_SHORT).show();       
						return;      
					}       
				} catch (Exception e) {      
					Toast.makeText(getBaseContext(), "Please enter a numeric quantity", Toast.LENGTH_SHORT).show();        
					return;     
				}       
				
				Log.d("ProductDetailsActivity", "Product: "+selectedProduct.title+", quantity to set = "+quantity);
				// If we make it here, a valid quantity was entered     
				ShoppingCartHelper.setQuantity(selectedProduct, quantity);       
				// Close the activity     
				finish();    
			}

		});
		
		int chosenPosition = getIntent().getExtras().getInt(ShoppingCartHelper.PRODUCT_INDEX);
		Log.d("ProductDetailsActivity", "ChosenPosition = "+chosenPosition);
	}
	
	public static void setExtrasIndex(boolean flag) {
		isIndex = flag;
	}
}