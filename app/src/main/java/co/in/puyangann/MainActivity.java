package co.in.puyangann;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Defining Variables
    public static Toolbar toolbar;
    public static NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public static int add = 0;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    int LoginId;
    Boolean check;
    String page;
    boolean doubleBackToExitPressedOnce = false;
    public static MenuItem m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//		toolbar.setVisibility(View.GONE);
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

//		Menu m = navigationView.getMenu();
////		SubMenu topChannelMenu = m.addSubMenu("Top Channels");
////		m.add("Foo");
////		m.add("Bar");
////		m.add("Baz");
//         m.getItem(8).setVisible(false);
//		MainActivity.navigationView.get


//        Signin fragment1 = new Signin();
//        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction1.replace(R.id.frame, fragment1);
//        fragmentTransaction1.commit();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        LoginId = sharedpreferences.getInt("LoginId", 0);
        if(LoginId!=0)
        {
            All fragment7 = new All();
            android.support.v4.app.FragmentTransaction fragmentTransaction7 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction7.replace(R.id.frame, fragment7);
            fragmentTransaction7.commit();
        }
        else
        {
            Signin fragment1 = new Signin();
            android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.frame, fragment1);
            fragmentTransaction1.commit();
        }
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
//				final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                LoginId = sharedpreferences.getInt("LoginId", 0);
                check = sharedpreferences.getBoolean("Signinpage", false);
                page = sharedpreferences.getString("page", "");

                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
//						Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
                        Signin fragment1 = new Signin();
                        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.frame, fragment1);
                        fragmentTransaction1.commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.starred:
//						Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                        Cart fragment2 = new Cart();
                        android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frame, fragment2);
                        fragmentTransaction2.commit();
                        return true;
                    case R.id.sent_mail:
//						Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        Order fragment3 = new Order();
                        android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.frame, fragment3);
                        fragmentTransaction3.commit();
                        return true;
                    case R.id.drafts:
//						Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                        Categories fragment4 = new Categories();
                        android.support.v4.app.FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction4.replace(R.id.frame, fragment4);
                        fragmentTransaction4.commit();
                        return true;
                    case R.id.allmail:
//						Toast.makeText(getApplicationContext(),"All Mail Selected",Toast.LENGTH_SHORT).show();
                        Recents fragment5 = new Recents();
                        android.support.v4.app.FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction5.replace(R.id.frame, fragment5);
                        fragmentTransaction5.commit();
                        return true;
                    case R.id.trash:
//						Toast.makeText(getApplicationContext(),"Trash Selected",Toast.LENGTH_SHORT).show();
                        Features fragment6 = new Features();
                        android.support.v4.app.FragmentTransaction fragmentTransaction6 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction6.replace(R.id.frame, fragment6);
                        fragmentTransaction6.commit();
                        return true;
                    case R.id.spam:
//						Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
                        All fragment7 = new All();
                        android.support.v4.app.FragmentTransaction fragmentTransaction7 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction7.replace(R.id.frame, fragment7);
                        fragmentTransaction7.commit();
                        return true;
                    case R.id.setting:
//						Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
                        Setting fragment8 = new Setting();
                        android.support.v4.app.FragmentTransaction fragmentTransaction8 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction8.replace(R.id.frame, fragment8);
                        fragmentTransaction8.commit();
                        return true;
                    case R.id.logout:
//						Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("LoginId", 0);
                        editor.putInt("fblogin", 0);
                        editor.commit();


                        Signin fragment9 = new Signin();
                        android.support.v4.app.FragmentTransaction fragmentTransaction9 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction9.replace(R.id.frame, fragment9);
                        fragmentTransaction9.commit();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                LoginId = sharedpreferences.getInt("LoginId", 0);
                Menu m = navigationView.getMenu();
                if (LoginId == 0) {
                    m.getItem(8).setVisible(false);
                } else {
                    m.getItem(8).setVisible(true);
                }


            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {

        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            manager.popBackStack();
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            page = sharedpreferences.getString("page", "");
            if (page.equalsIgnoreCase("Categories")) {
                Categories.txtstore.setText(Categories.store);
            } else if (page.equalsIgnoreCase("Recents")) {
                Recents.txtstore.setText(Categories.store);
            } else if (page.equalsIgnoreCase("Features")) {
                Features.txtstore.setText(Categories.store);
            } else if (page.equalsIgnoreCase("All")) {
                All.txtstore.setText(Categories.store);
            } else {
            }

        } else {
            // Otherwise, ask user if he wants to leave :)
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }


//        super.onBackPressed();
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        page = sharedpreferences.getString("page", "");
//        if (page.equalsIgnoreCase("Categories")) {
//            Categories.txtstore.setText(Categories.store);
//        } else if (page.equalsIgnoreCase("Recents")) {
//            Recents.txtstore.setText(Categories.store);
//        } else if (page.equalsIgnoreCase("Features")) {
//            Features.txtstore.setText(Categories.store);
//        } else if (page.equalsIgnoreCase("All")) {
//            All.txtstore.setText(Categories.store);
//        } else {
//
//        }
    }
}
