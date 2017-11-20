package co.in.puyangann;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Recents extends Fragment implements OnClickListener{
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	GridView gridview;
	JSONArray productslist = null;
	String ProductId,Name,Image,Regular_price,Sale_price,Price;
//	public static String Product_Id;
	ArrayList<Holder> list = new ArrayList<Holder>();
	int  LoginId;
	Button btnvieworder,btnall,btnrecent,btnfeature,btncategory;

	public static TextView txtstore,txtstoreclick;
	JSONArray cartinfo = null;
	Fragment fragment;
	FragmentManager fm;
	FragmentTransaction ft;
	TextView emptylist;
	int p_length;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.recent, container, false);
		//img=(ImageView)view.findViewById(R.id.banner);
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean("Signinpage", false);
		editor.putString("page", "Recents");
		editor.commit();
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);
		gridview=(GridView)view.findViewById(R.id.productlist);
		txtstore=(TextView)view.findViewById(R.id.txt11);
		txtstoreclick=(TextView)view.findViewById(R.id.txtstoreclick);
		btnvieworder=(Button)view.findViewById(R.id.btnvieworder);
		btnall=(Button)view.findViewById(R.id.btnall);
		btnrecent=(Button)view.findViewById(R.id.btnrecents);
		btnfeature=(Button)view.findViewById(R.id.btnfeature);
		btncategory=(Button)view.findViewById(R.id.btncategory);

		btncategory.setTextColor(Color.parseColor("#000000"));
		btnrecent.setTextColor(Color.parseColor("#d0691b"));
		btnfeature.setTextColor(Color.parseColor("#000000"));
		btnall.setTextColor(Color.parseColor("#000000"));

		emptylist=(TextView)view.findViewById(R.id.emptylist);
		emptylist.setVisibility(View.GONE);

		btnall.setOnClickListener(this);
		btnfeature.setOnClickListener(this);
		btncategory.setOnClickListener(this);
//		Home.mDrawerList.setItemChecked(5, true);
		txtstore.setText(Categories.store);

		//		gridview.setBackgroundColor(Color.WHITE);
		//		gridview.setVerticalSpacing(1);
		//		gridview.setHorizontalSpacing(1);
		new ProductProgressBar().execute();
		btnvieworder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Order fragment = new Order();
//				FragmentManager fm = getActivity().getSupportFragmentManager();
//				FragmentTransaction ft = fm.beginTransaction();
//				ft.add(R.id.content_frame, fragment);
//				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//				ft.addToBackStack("add"+MainActivity.add);
//				ft.commit();
//				MainActivity.add++;
//				fragment = new Order();
//				ft = getActivity().getSupportFragmentManager().beginTransaction();
//				ft.replace(R.id.frame,fragment);
//				ft.commit();


				fragment = new Order();
				ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.add(R.id.frame, fragment);
				ft.addToBackStack("add" + MainActivity.add);
				ft.commit();
				MainActivity.add++;
				MainActivity.navigationView.getMenu().getItem(2).setChecked(true);
//				 MainActivity.m.setCheckable(true);

			}
		});
		txtstoreclick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				fragment = new Cart();
				ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.add(R.id.frame, fragment);
				ft.addToBackStack("add" + MainActivity.add);
				ft.commit();
				MainActivity.add++;
				MainActivity.navigationView.getMenu().getItem(1).setChecked(true);

			}
		});
		return view;
	}
	public class ProductProgressBar extends AsyncTask<String,String, String>
	{

		private ProgressDialog pdia;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdia = new ProgressDialog(getActivity());
			pdia.setMessage("Loading...");
			pdia.show();
			pdia.setCancelable(false);
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			list.clear();

			JSONParser jParser = new JSONParser();
			// http://www.puyangan.com/api/products.php?show=featured
//			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/products.php?show=recent");
			JSONObject[] json = new JSONObject[2];
			json[0] = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/products.php?show=recent");
			json[1] = jParser.getJSONFromUrl(getResources().getString(R.string.url) + "/api/cart.php?user_id=" + LoginId+"&device_id="+Splash.device_id);

			try {
				//http://www.puyangan.com/api/category.php?cid=178
				// Getting Array of Employee
				productslist = json[0].getJSONArray("products");
				cartinfo = json[1].getJSONArray("cartinfo");
				p_length= productslist.length();
				int lengths=cartinfo.length();
				// looping of List
				for (int i = 0; i < p_length; i++) {
					JSONObject c = productslist.getJSONObject(i);
					//					Bitmap myBitmap = null;
					InputStream input = null;
					ProductId = c.getString("id");
					Name = c.getString("name");
					Image = c.getString("image");
					Regular_price = c.getString("regular_price");
					Sale_price = c.getString("sale_price");
					Price = c.getString("price");
					Holder h = new Holder();
					if(Image.equalsIgnoreCase(""))
					{

					}
					else
					{

						URL url = new URL(Image);
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						connection.setDoInput(true);
						connection.setInstanceFollowRedirects(false);
						connection.setRequestMethod("GET");
						connection.connect();
						input = connection.getInputStream();

						BitmapFactory.Options opts = new BitmapFactory.Options();
						// opts.inJustDecodeBounds = true; 
						opts.inSampleSize=4;    
						Bitmap myBitmap = BitmapFactory.decodeStream(input,null, opts);
						//						Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 200, 200, true);
						//myBitmap.recycle();

						//						Bitmap myBitmap = BitmapFactory.decodeStream(input);
						h.setBitmap(myBitmap);
						connection.disconnect();
					}


					h.setId(ProductId);
					h.setName(Name);
					h.setRegular_price(Regular_price);
					h.setSale_price(Sale_price);
					h.setPrice(Price);
					h.setImage(Image);

					list.add(h);

				}
				for(int j=0;j<lengths;j++)
				{
					JSONObject cc = cartinfo.getJSONObject(j);

					Categories.store=cc.getString("totalitems");

				}
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e)
			{

			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdia.dismiss();
			pdia = null;

			//			ExpandableListAdapter	listAdapter = new com.puyangan.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

			// setting list adapter
			//			expListView.setAdapter(listAdapter);
			gridview.setAdapter(new MyCustomAdapter(getActivity(),list));
			txtstore.setText(Categories.store);
			//			ListUtils.setDynamicHeight(gridview);
			if(p_length==0)
			{
				emptylist.setVisibility(View.VISIBLE);
				gridview.setVisibility(View.GONE);
			}
			else
			{
				emptylist.setVisibility(View.GONE);
				gridview.setVisibility(View.VISIBLE);
			}


		}

	}
	public static class ListUtils {
		public static void setDynamicHeight(GridView gridview) {
			ListAdapter mListAdapter = gridview.getAdapter();
			if (mListAdapter == null) {
				// when adapter is null
				return;
			}
			int height = 0;
			int desiredWidth = MeasureSpec.makeMeasureSpec(gridview.getWidth(), MeasureSpec.UNSPECIFIED);
			for (int i = 0; i < mListAdapter.getCount(); i++) {
				View listItem = mListAdapter.getView(i, null, gridview);
				listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
				height += listItem.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = gridview.getLayoutParams();
			params.height = height + (gridview.getHeight() * (mListAdapter.getCount() - 1));
			gridview.setLayoutParams(params);
			gridview.requestLayout();
		}
	}
	class MyCustomAdapter extends BaseAdapter {

		LayoutInflater inflater;
		ArrayList<Holder> list;
		public MyCustomAdapter(FragmentActivity fragmentActivity, ArrayList<Holder> list) {
			inflater = LayoutInflater.from(fragmentActivity);
			this.list =list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int paramInt) {
			return paramInt;
		}

		class ViewHolder{
			TextView name,price;
			Button btnAddtocart;
			ImageView productImage;
		}

		@Override
		public long getItemId(int paramInt) {
			return paramInt;
		}
		@Override
		public View getView(final int paramInt, View paramView,ViewGroup paramViewGroup) {

			ViewHolder holder;
			if(paramView==null)
			{
				paramView = inflater.inflate(R.layout.product_list_item, paramViewGroup, false);
				holder= new ViewHolder();

				holder.name = (TextView) paramView.findViewById(R.id.txtname);
				holder.price = (TextView) paramView.findViewById(R.id.txtprice);
				holder.btnAddtocart = (Button)paramView.findViewById(R.id.addtocart);
				holder.productImage = (ImageView)paramView.findViewById(R.id.product_img);

				paramView.setTag(holder);
			}
			else{
				holder = (ViewHolder) paramView.getTag();
			}

			Holder h = list.get(paramInt);
			String name = h.getName();
			holder.name.setText(name);
			String price=h.getPrice();
			holder.price.setText("$"+price);
			Bitmap bitmap=h.getBitmap();
			if(h.getImage().equalsIgnoreCase(""))
			{
				holder.productImage.setBackgroundResource(R.drawable.header);
			}
			else
			{
				holder.productImage.setImageBitmap(bitmap);
			}


			holder.btnAddtocart.setTag(paramInt);
			holder.btnAddtocart.setOnClickListener(new OnClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View vv) {
					// TODO Auto-generated method stub
					int pos1 = (Integer) vv.getTag();
					Holder h1 = (Holder) list.get(pos1);

					Product.Product_Id=h1.getId();
					sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
					LoginId =sharedpreferences.getInt("LoginId", 0);
					//		img=(ImageView)view.findViewById(R.id.banner);
//					if(LoginId==0)
//					{
//
//						AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();
//
//						alertDialog.setTitle("First Signin");
//						alertDialog.setIcon(R.drawable.lounchicon);
//						alertDialog.setButton("SIGNIN", new  DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// TODO Auto-generated method stub
//								//							TabStackTemple.this.finish();
//								SharedPreferences.Editor editor = sharedpreferences.edit();
//								editor.putString("page", "Product");
//								editor.commit();
//
////								Home.mDrawerList.setItemChecked(1, true);
////								Fragment fragment = new Signin();
////								FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////								FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////								fragmentTransaction.replace(R.id.content_frame, fragment);
////								fragmentTransaction.addToBackStack(null);
////								fragmentTransaction.commit();
////								fragment = new Signin();
////								ft = getActivity().getSupportFragmentManager().beginTransaction();
////								ft.replace(R.id.frame,fragment);
////								ft.commit();
//								fragment = new Signin();
//								ft = getActivity().getSupportFragmentManager().beginTransaction();
//								ft.add(R.id.frame, fragment);
//								ft.addToBackStack("add" + MainActivity.add);
//								ft.commit();
//								MainActivity.add++;
//							}
//						});
//						alertDialog.setButton2("CANCEL", new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int which) {
//
//							}
//						});
//						alertDialog.show();
//					}
//					else
//					{
						new AddToCartProgress().execute();
//					}


				}
			});
			holder.productImage.setTag(paramInt);
			holder.productImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View vv) {
					// TODO Auto-generated method stub
					int pos1 = (Integer) vv.getTag();
					Holder h1 = (Holder) list.get(pos1);
					Product.Product_Id=h1.getId();
					Categories.str_categories="";
					Categories.str_subcategories="";
					Product.str_product=h1.getName();
					//					Fragment fragment = new ProductDetails();
					//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					//					fragmentTransaction.replace(R.id.content_frame, fragment);
					//					fragmentTransaction.addToBackStack(null);
					//					fragmentTransaction.commit();
//					ProductDetails fragment = new ProductDetails();
//					FragmentManager fm = getActivity().getSupportFragmentManager();
//					FragmentTransaction ft = fm.beginTransaction();
//					ft.add(R.id.content_frame, fragment);
//					ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//					ft.addToBackStack("add"+MainActivity.add);
//					ft.commit();
//					MainActivity.add++;
//					fragment = new ProductDetails();
//					ft = getActivity().getSupportFragmentManager().beginTransaction();
//					ft.replace(R.id.frame,fragment);
//					ft.commit();
					fragment = new ProductDetails();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.add(R.id.frame, fragment);
					ft.addToBackStack("add" + MainActivity.add);
					ft.commit();
					MainActivity.add++;
				}
			});
			return paramView;
		}
	}
	public class AddToCartProgress extends AsyncTask<String, String, String>
	{


		private ProgressDialog pdia;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdia = new ProgressDialog(getActivity());
			pdia.setMessage("Loading...");
			pdia.show();
			pdia.setCancelable(false);
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub


			JSONParser jParser = new JSONParser();

			JSONObject[] json = new JSONObject[2];
			json[0] = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/addtocart.php?user_id="+LoginId+"&product_id="+Product.Product_Id+"&qunt=1"+"&device_id="+Splash.device_id);
			json[1] = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/cart.php?user_id="+LoginId+"&device_id="+Splash.device_id);


			// getting JSON string from URL//checkuserforgotpwd=1,user_login=emailid
			//			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/addtocart.php?user_id="+LoginId+"&product_id="+Product_Id+"&qunt=1");
			try {
				//http://www.puyangan.com/api/category.php
				// Getting Array of Employee
				cartinfo = json[1].getJSONArray("cartinfo");
				int lengths=cartinfo.length();

				for(int j=0;j<lengths;j++)
				{
					JSONObject cc = cartinfo.getJSONObject(j);

					Categories.store=cc.getString("totalitems");

				}
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdia.dismiss();
			pdia = null;
			txtstore.setText(Categories.store);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btncategory:
//			fragment = new Categories();
//			fm = getActivity().getSupportFragmentManager();
//			ft = fm.beginTransaction();
//			ft.add(R.id.content_frame, fragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			ft.addToBackStack("add"+MainActivity.add);
//			ft.commit();
//			MainActivity.add++;
			fragment = new Categories();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.frame,fragment);
			ft.add(R.id.frame, fragment);
//			ft.addToBackStack("add" + MainActivity.add);
			ft.commit();
//			MainActivity.add++;
			MainActivity.navigationView.getMenu().getItem(3).setChecked(true);
			break;
		case R.id.btnfeature:
//			fragment = new Features();
//			fm = getActivity().getSupportFragmentManager();
//			ft = fm.beginTransaction();
//			ft.add(R.id.content_frame, fragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			ft.addToBackStack("add"+MainActivity.add);
//			ft.commit();
//			MainActivity.add++;
			fragment = new Features();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.frame,fragment);
			ft.add(R.id.frame, fragment);
//			ft.addToBackStack("add" + MainActivity.add);
			ft.commit();
//			MainActivity.add++;
			MainActivity.navigationView.getMenu().getItem(5).setChecked(true);
			break;
		case R.id.btnall:
//			fragment = new All();
//			fm = getActivity().getSupportFragmentManager();
//			ft = fm.beginTransaction();
//			ft.add(R.id.content_frame, fragment);
//			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			ft.addToBackStack("add"+MainActivity.add);
//			ft.commit();
//			MainActivity.add++;
			fragment = new All();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
//			ft.replace(R.id.frame,fragment);
			ft.add(R.id.frame,fragment);
//			ft.addToBackStack("add" + MainActivity.add);
			ft.commit();
//			MainActivity.add++;
			MainActivity.navigationView.getMenu().getItem(6).setChecked(true);
			break;

		}
	}
}
