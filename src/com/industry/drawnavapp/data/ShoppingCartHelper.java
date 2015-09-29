package com.industry.drawnavapp.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.industry.drawnavapp.activity.R;

import android.content.res.Resources;
import android.util.Log;

public class ShoppingCartHelper {

	public static final String PRODUCT_INDEX = "PRODUCT_INDEX";
	public static final String PRODUCT_TITLE = "PRODUCT_TITLE";

	private static List<Product> catalog;
	private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();

	public static List<Product> getCatalog(Resources res) {
		if (catalog == null) {
			catalog = new Vector<Product>();
			catalog.add(new Product(
					"Villa California",
					res.getDrawable(R.drawable.californiavilla),
					"Villa California, architecture by N. Hassan with Grant Blackwood Limited Co.",
					29.99));
			catalog.add(new Product("Villa Amko", res
					.getDrawable(R.drawable.amkovilla),
					"Villa Amko, architecture by N. Hassan with Arc Blue AB",
					24.99));
			catalog.add(new Product(
					"Villa Mountain",
					res.getDrawable(R.drawable.mountainvilla),
					"Villa Mountain, architecture by N. Hassan with Arc Blue AB",
					14.99));
		}

		return catalog;
	}

	public static void setQuantity(Product product, int quantity) {
		Log.d("ShoppingCartHelper", "Product: " + product.title
				+ ", quantity = " + quantity);
		// Get the current cart entry
		ShoppingCartEntry curEntry = cartMap.get(product);

		// If the quantity is zero or less, remove the products
		if (quantity <= 0) {
			if (curEntry != null)
				removeProduct(product);
			return;
		}

		// If a current cart entry doesn't exist, create one
		if (curEntry == null) {
			curEntry = new ShoppingCartEntry(product, quantity);
			Log.d("ShoppingCartHelper",
					"Created new entry for Product: "
							+ curEntry.getProduct().title + ", quantity = "
							+ curEntry.getQuantity());
			cartMap.put(product, curEntry);
			return;
		}

		// Update the quantity
		curEntry.setQuantity(quantity);
	}

	public static int getProductQuantity(Product product) {
		Log.d("ShoppingCartHelper", "getProductQuantity for Product: "
				+ product.title);
		// Get the current cart entry
		ShoppingCartEntry curEntry = cartMap.get(product);

		if (curEntry != null) {
			Log.d("ShoppingCartHelper",
					"getProductQuantity for Product: "
							+ curEntry.getProduct().title + ", quantity = "
							+ curEntry.getQuantity());
			return curEntry.getQuantity();
		}

		return 0;
	}

	public static void removeProduct(Product product) {
		cartMap.remove(product);
	}

	public static List<Product> getCartList() {
		List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
		for (Product p : cartMap.keySet()) {
			cartList.add(p);
		}

		return cartList;
	}

	public static Product getSelectedProduct(String productTitle) {
		for (Product p : cartMap.keySet()) {
			if (p.title.equals(productTitle)) {
				return p;
			}
		}
		return null;

	}

}
