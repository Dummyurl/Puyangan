package co.in.puyangann;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;


public class Signin extends Fragment implements OnClickListener{
	Button btnsignup,btnsubmit,btnsend,btncancel;
	ToggleButton btnshowpassword;
	EditText user_name,password,edtemail;
	TextView fpassword,text1,text2,error,txtmessage;
	String User_Name,Password;
	RelativeLayout idaccountsignin,idmessage,idtop;
	JSONArray responce = null;
	String userId;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	String page,message;
	Fragment fragment;
	FragmentTransaction ft;
	int LoginId;
	String email;
	private Button btnfb;
	private HashMap<String, String> userHashmap;
	private ArrayList<HashMap<String, String>> friendList;

	private ProgressDialog pd;

	public static final String USER_MAP = "userHashmap";
	public static final String FRIEND_LIST = "friendList";

	public static final String USER_ID = "userId";
	public static final String NAME = "name";
	public static final String USER_NAME = "userName";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String BIRTHDAY = "birthday";
	public static final String GENDER = "gender";
	public static final String EMAIL_ID = "emailId";
	public static final String IMAGE_URL = "imageUrl";
	String access_tocken;
	public static Fragment fragmentt;
	public static FragmentTransaction ftt;
	JSONArray ADDRESS=null;
	String UserName="",Fname="",Mname="",Lname="",Birthday="",Email="",Id="",address="";
	private ProgressDialog pdia;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// To maintain FB Login session

		super.onCreate(savedInstanceState);
		getKeyHash();


	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			final Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view=inflater.inflate(R.layout.accountsignin, container, false);
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		//img=(ImageView)view.findViewById(R.id.banner);
		btnsignup=(Button)view.findViewById(R.id.btnsignup);
//		btnfb=(Button)view.findViewById(R.id.btnfb);
		btnshowpassword=(ToggleButton)view.findViewById(R.id.btnshowpassword);
		btnsubmit=(Button)view.findViewById(R.id.btnsubmit);
		user_name=(EditText)view.findViewById(R.id.edtusername);
		password=(EditText)view.findViewById(R.id.edtpassword);
		fpassword=(TextView)view.findViewById(R.id.fpassword);
		txtmessage=(TextView)view.findViewById(R.id.textmessage);
		idmessage=(RelativeLayout)view.findViewById(R.id.idmessage);
		idtop=(RelativeLayout)view.findViewById(R.id.idtop);
		idaccountsignin=(RelativeLayout)view.findViewById(R.id.idaccountsignin);
		btnsignup.setOnClickListener(this);
		//btnfb.setOnClickListener(this);
		btnshowpassword.setOnClickListener(this);
		btnsubmit.setOnClickListener(this);
		fpassword.setOnClickListener(this);
		idmessage.setVisibility(View.GONE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean("Signinpage", true);
		MainActivity.navigationView.getMenu().getItem(0).setChecked(true);

		editor.commit();
		sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		LoginId =sharedpreferences.getInt("LoginId", 0);
		email =sharedpreferences.getString("email", "");

if(LoginId==0)
{
	idtop.setVisibility(View.VISIBLE);
	idmessage.setVisibility(View.GONE);
}
else
{
	idtop.setVisibility(View.GONE);
	idmessage.setVisibility(View.VISIBLE);
	txtmessage.setText("You are login with "+email);
}
		NumberKeyListener emailinput = new NumberKeyListener() {

			public int getInputType() {
				return InputType.TYPE_MASK_VARIATION;
			}

			@Override
			protected char[] getAcceptedChars() {
				return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
						'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '.', '_'};
			}
		};
		user_name.setKeyListener(emailinput);
		user_name.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				user_name.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		password.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				// put the code of save Database here
				password.setError(null);
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		return view;
	}


	private void getKeyHash() {
		try {
			PackageInfo info = getActivity().getPackageManager().getPackageInfo(
					getActivity().getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("Keyhash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));//XRt1Im/ckYIrqWSc2Ev4kap5I14=

			}
		} catch (PackageManager.NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Fragment fragment;
		FragmentManager fm;
		android.support.v4.app.FragmentTransaction ft;
		switch (v.getId()) {
		case R.id.btnsubmit:
			Email=user_name.getText().toString();
			Password=password.getText().toString();
			if(Email.equalsIgnoreCase(""))
			{
				user_name.requestFocus();
				user_name.setText("");
				user_name.setError("Please fill username or email");

			}
			else
			{
				if(Password.equalsIgnoreCase(""))
				{
					password.requestFocus();
					password.setText("");
					password.setError("Please fill password");

				}
				else
				{
					new Submit().execute();

				}
			}




			break;
		case R.id.btnshowpassword:
			boolean press = ((ToggleButton) v).isChecked();
			if(press)
				password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			else
				password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			break;
//		case R.id.fusername:
//			rltop.setBackgroundResource(R.color.xyz);
//			LayoutInflater inflater=getActivity().getLayoutInflater();
//			View popupView = inflater.inflate(R.layout.popup, null);  
//			final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);  
//
//			btncancel = (Button)popupView.findViewById(R.id.btncancel);
//			btnsend = (Button)popupView.findViewById(R.id.btnsend);
//			edtemail=(EditText)popupView.findViewById(R.id.edtemail);
//			text1=(TextView)popupView.findViewById(R.id.textView1);
//			text2=(TextView)popupView.findViewById(R.id.textView2);
//			error=(TextView)popupView.findViewById(R.id.txterror);
//			text1.setText("Forgot Username");
//			btncancel.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//
//					//					rltop.setAlpha(1F);
//					rltop.setBackgroundResource(R.color.white);
//					popupWindow.dismiss();
//				}});
//			btnsend.setOnClickListener(new OnClickListener(){
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Email=edtemail.getText().toString();
//					if(Email.trim().length()<1)
//					{
//						//						empty.setVisibility(View.VISIBLE);
//						//						empty.setText("Please enter file name");
//						error.setText("Please fill email address");
//						error.setTextColor(Color.RED);
//						error.setVisibility(View.VISIBLE);
//					}
//					else
//					{
//
//						//						popupWindow.dismiss();
//						new ForgotProgress().execute();
//						rltop.setAlpha(1F);
//						rltop.setBackgroundResource(R.color.white);
//						popupWindow.dismiss();
//
//					}
//					//					rltop.setAlpha(1F);
////					rltop.setBackgroundResource(R.color.white);
////					popupWindow.dismiss();
//				}});
//			popupWindow.showAtLocation(popupView, LayoutParams.WRAP_CONTENT,10,200);
//			popupWindow.setFocusable(true);
//			popupWindow.update();
//			//			rltop.setAlpha(0.2F);
//
//			break;
		case R.id.fpassword:
			idaccountsignin.setBackgroundResource(R.color.xyz);
			LayoutInflater inflaterr=getActivity().getLayoutInflater();
			View popupVieww = inflaterr.inflate(R.layout.popup, null);
			final PopupWindow popupWindoww = new PopupWindow(popupVieww, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);

			btncancel = (Button)popupVieww.findViewById(R.id.btncancel);
			btnsend = (Button)popupVieww.findViewById(R.id.btnsend);
			edtemail=(EditText)popupVieww.findViewById(R.id.edtemail);
			NumberKeyListener emailinput = new NumberKeyListener() {

				public int getInputType() {
					return InputType.TYPE_MASK_VARIATION;
				}

				@Override
				protected char[] getAcceptedChars() {
					return new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
							'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '.', '_'};
				}
			};
			edtemail.setKeyListener(emailinput);
			error=(TextView)popupVieww.findViewById(R.id.txterror);
			btncancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					//					rltop.setAlpha(1F);
					idaccountsignin.setBackgroundResource(R.color.white);
					popupWindoww.dismiss();
				}});
			btnsend.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Email=edtemail.getText().toString();
					if(Email.trim().length()<1)
					{
						//						edtemail.requestFocus();
						//						edtemail.setText("");
						//						edtemail.setError("Enter Email Address");
						error.setText("Please fill email address");
						error.setTextColor(Color.RED);
						error.setVisibility(View.VISIBLE);
					}
					else
					{

						//						popupWindow.dismiss();
						new ForgotProgress().execute();
						idaccountsignin.setAlpha(1F);
						idaccountsignin.setBackgroundResource(R.color.white);
						popupWindoww.dismiss();

					}
					//					rltop.setAlpha(1F);
					//					rltop.setBackgroundResource(R.color.white);
					//					popupWindoww.dismiss();
				}});
			popupWindoww.showAtLocation(popupVieww, LayoutParams.WRAP_CONTENT,10,200);
			popupWindoww.setFocusable(true);
			popupWindoww.update();
			break;
		case R.id.btnsignup:
//			Signup fragment = new Signup();
//			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//			fragmentTransaction.replace(R.id.content_frame, fragment);
//			fragmentTransaction.addToBackStack(null);
//			fragmentTransaction.commit();
			fragment = new Signup();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame,fragment);
			ft.commit();
			break;


		}
	}
	public class Submit extends AsyncTask<String, String, String>
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
			// getting JSON string from URL//fname,lname,email,pwd,pwd2,zipcode,month,day,subscriber      checkuserlogin=1,user_login=emailid,user_pass=password
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/login.php?checkuserlogin=1&user_login="+URLEncoder.encode(Email)+"&user_pass="+Password);
			try {
				// Getting Array of Employee
				responce = json.getJSONArray("success");
				int length= responce.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = responce.getJSONObject(i);
					userId=c.getString("login_user_id");
					// Storing each json item in variable
					//					UserId =Integer.parseInt( c.getString("login_user_id"));
					//					// Log.e("PickUpLocation",PickUpLocation);
					//					msg = c.getString("message");
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdia.dismiss();
			pdia = null;
			if(Integer.parseInt(userId)==0)
			{
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();
				alertDialog.setTitle("Fill correct email and password");

				alertDialog.setIcon(R.drawable.lounchicon);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				alertDialog.show();
			}
			else
			{
				SharedPreferences.Editor editor = sharedpreferences.edit();
				editor.putInt("LoginId",Integer.parseInt(userId));
				editor.putString("email",Email);
				editor.commit();

				sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
				page =sharedpreferences.getString("page", "");

				if(page.equalsIgnoreCase("Order"))
				{
//					Fragment fragment = new Order();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
					fragment = new Order();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.frame,fragment);
					ft.commit();
				}
				else if(page.equalsIgnoreCase("Product"))
				{
//					Fragment fragment = new Product();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
					fragment = new Product();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.frame,fragment);
					ft.commit();
				}
				else if(page.equalsIgnoreCase("productdetails"))
				{
//					Fragment fragment = new ProductDetails();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
					fragment = new ProductDetails();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.frame,fragment);
					ft.commit();
				}
				else
				{
//					Fragment fragment = new Categories();
//					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//					fragmentTransaction.replace(R.id.content_frame, fragment);
//					fragmentTransaction.addToBackStack(null);
//					fragmentTransaction.commit();
					fragment = new Categories();
					ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.frame,fragment);
					ft.commit();
				}
			}
		}
	}
	public class ForgotProgress extends AsyncTask<String, String, String>
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
			// getting JSON string from URL//checkuserforgotpwd=1,user_login=emailid
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/login.php?checkuserforgotpwd=1&user_login="+URLEncoder.encode(Email));
			try {
				// Getting Array of Employee
				responce = json.getJSONArray("success");
				int length= responce.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = responce.getJSONObject(i);
					message=c.getString("message");
					// Storing each json item in variable
					//					UserId =Integer.parseInt( c.getString("login_user_id"));
					//					// Log.e("PickUpLocation",PickUpLocation);
					//					msg = c.getString("message");
				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdia.dismiss();
			pdia = null;

			AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_LIGHT).create();
			alertDialog.setTitle(message);

			alertDialog.setIcon(R.drawable.lounchicon);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			alertDialog.show();
		}
	}


//	@Override
//	public void onResume() {
//		super.onResume();
//if(MainActivity2.fblogin==1)
//{
//	fragment = new Categories();
//	ft = getActivity().getSupportFragmentManager().beginTransaction();
//	ft.replace(R.id.frame,fragment);
//	ft.commit();
//	sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//	SharedPreferences.Editor editor = sharedpreferences.edit();
//
//	editor.putInt("fblogin", 1);
//
//	editor.commit();
//	MainActivity2.fblogin=0;
//}
//	}
	class FBProgressBar extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(getActivity());
			pdia.setMessage("Loading...");
			pdia.show();
			pdia.setCancelable(false);
			// Log.e("pre","execute");
		}
		@Override
		protected String doInBackground(String... f_url) {
			//Log.e("do","execute");
			//makeGetRequest();


			JSONParser jParser = new JSONParser();
			// getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl("https://graph.facebook.com/me?fields=id,email,name,first_name,last_name,middle_name,birthday,gender&access_token="+access_tocken);
			try {
				// Getting Array of Employee
				//								payment = json.getJSONArray("payment");
				int	length= json.length();
				// looping of List
				for (int i = 0; i < 1; i++) {
					//									JSONObject c = payment.getJSONObject(i);

					// Storing each json item in variable
					if(json.has("name"))
					{
						UserName = json.getString("name");
					}
					if(json.has("first_name"))
					{
						Fname = json.getString("first_name");
					}
					if(json.has("middle_name"))
					{
						Mname = json.getString("middle_name");
					}
					if(json.has("last_name"))
					{
						Lname = json.getString("last_name");
					}

					if(json.has("email"))
					{
						Email = json.getString("email");
					}
					if(json.has("birthday"))
					{
						Birthday = json.getString("birthday");
					}
					if(json.has("address"))
					{
						address=json.getString("address");
					}
					if(json.has("id"))
					{
						Id = json.getString("id");
					}
				}


			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String file_url) {

//			pdia.dismiss();
//			pdia = null;
			//fblogin=1;

			new FbProgress().execute();






		}
	}
	public class FbProgress extends AsyncTask<String,String,String>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			JSONParser jParser = new JSONParser();
			// getting JSON string from URL//fname,lname,email,pwd,pwd2,zipcode,month,day,subscriber
			JSONObject json = jParser.getJSONFromUrl(getResources().getString(R.string.url)+"/api/login.php?fblogin=1&userid="+Id+"&fullname="+URLEncoder.encode(UserName)+"&fname="+URLEncoder.encode(Fname)+"&lname="+URLEncoder.encode(Lname)+"&email="+URLEncoder.encode(Email)+"&dob="+Birthday);
			try {
				// Getting Array of Employee
				responce = json.getJSONArray("success");
				int length= responce.length();
				// looping of List
				for (int i = 0; i < length; i++) {
					JSONObject c = responce.getJSONObject(i);
					userId=c.getString("login_user_id");

				}
			}
			catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			pdia.dismiss();
			pdia = null;
			SharedPreferences.Editor editor = sharedpreferences.edit();
			editor.putInt("LoginId", Integer.parseInt(userId));
			editor.putString("email", Email);
			editor.commit();
			fragment = new Categories();
			ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.frame, fragment);
			ft.commit();
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		//Log.i(TAG, "OnActivityResult...");
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
