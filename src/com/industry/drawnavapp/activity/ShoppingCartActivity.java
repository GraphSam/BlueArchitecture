package com.industry.drawnavapp.activity;

import java.text.DecimalFormat;
import java.util.List;

import com.industry.drawnavapp.data.Product;
import com.industry.drawnavapp.data.ProductAdapter;
import com.industry.drawnavapp.data.ShoppingCartHelper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShoppingCartActivity extends BaseActivity {
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	
	private List<Product> mCartList;  
	private ProductAdapter mProductAdapter;     

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
		
		setContentView(R.layout.activity_second);
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
		// titles
		// from
		// strings.xml

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
		// strings.xml

		set(navMenuTitles, navMenuIcons);
		
		TextView shoppingCartTextView = (TextView) findViewById(R.id.TextView01);
		shoppingCartTextView.setText(getString(R.string.shopping_cart));
		
		TextView editQuantityTextView = (TextView) findViewById(R.id.textView1);
		editQuantityTextView.setText(getString(R.string.edit_quantity));
		
		mCartList = ShoppingCartHelper.getCartList();       
		// Make sure to clear the selections   
		for(int i=0; i<mCartList.size(); i++) {    
			mCartList.get(i).selected = false;   
		}         
		
		// Create the list   
		final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);   
		mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);   
		listViewCatalog.setAdapter(mProductAdapter);       
		listViewCatalog.setOnItemClickListener(new OnItemClickListener() {      
			
			@Override   
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String selected = ((TextView)view.findViewById(R.id.TextViewItem)).getText().toString();
				Log.d("ShoppingCartActivity", "Clicked item is "+selected);
				Intent productDetailsIntent = new Intent(getBaseContext(), ProductDetailsActivity.class);     
				productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_TITLE, selected);
				ProductDetailsActivity.setExtrasIndex(false);
				startActivity(productDetailsIntent);    
			}   
		});
		
		Button proceedButton = (Button) findViewById(R.id.Button02);
		proceedButton.setText(getString(R.string.proceed_to_checkout));
		proceedButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Button Proceed to Checkout is clicked", Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	@Override 
	protected void onResume() {   
		super.onResume();       
		// Refresh the data   
		if(mProductAdapter != null) {    
			mProductAdapter.notifyDataSetChanged();   
		}       
		
		double subTotal = 0;  
		for(Product p : mCartList) {    
			int quantity = ShoppingCartHelper.getProductQuantity(p);    
			subTotal += p.price * quantity;   
		}       
		
		TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		productPriceTextView.setText(getString(R.string.subtotal) + df.format(subTotal));  
	}   
}
