package com.industry.drawnavapp.activity;

import java.util.List;

import com.industry.drawnavapp.data.Product;
import com.industry.drawnavapp.data.ProductAdapter;
import com.industry.drawnavapp.data.ShoppingCartHelper;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CatalogActivity extends BaseActivity {
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /**
     * Product list
     */
    private List<Product> mProductList;


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
		
		setContentView(R.layout.activity_first);
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
																					// titles
																					// from
																					// strings.xml

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
															// strings.xml

		set(navMenuTitles, navMenuIcons);
		
        // Obtain a reference to the product catalog
        mProductList = ShoppingCartHelper.getCatalog(getResources());
        
        // Create the list
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false));
        
        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

        	   @Override
        	   public void onItemClick(AdapterView<?> parent, View view, int position,
        	     long id) {
        	    Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetailsActivity.class);
        	    productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
        	    ProductDetailsActivity.setExtrasIndex(true);
        	    startActivity(productDetailsIntent);
        	   }

        });
        
    	  Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
      	  viewShoppingCart.setOnClickListener(new OnClickListener() {
      	   
      	   @Override
      	   public void onClick(View v) {
      	    Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
      	    startActivity(viewShoppingCartIntent);
      	   }
      	  });
		
	}
}
