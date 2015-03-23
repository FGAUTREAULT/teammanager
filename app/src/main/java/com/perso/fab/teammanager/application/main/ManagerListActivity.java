package com.perso.fab.teammanager.application.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.perso.fab.teammanager.R;
import com.perso.fab.teammanager.application.player.PlayerActivity;
import com.perso.fab.teammanager.application.team.TeamActivity;
import com.perso.fab.teammanager.interfaces.NavigationDrawerCallbacks;
import com.perso.fab.teammanager.interfaces.OnFragmentInteractionListener;
import com.perso.fab.teammanager.application.game.GameActivity;

import java.util.ArrayList;


public class ManagerListActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks, OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //TODO get the data from database
        ArrayList<String> list = prepareListObjects(position+1);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance(position + 1, list))
                .commit();
    }

    /**
     * TODO Create DATABASE
     * @return
     */
    private ArrayList<String> prepareListObjects(int position) {

        ArrayList<String> a = new ArrayList<String>();

        switch (position) {
            case 1:
                a.add("CELAD Vs Barca");
                a.add("Barca Vs CELAD");
                break;

            case 2:
                a.add("Amaury");
                a.add("Bastien");
                break;

            case 3:
                a.add("Barca");
                a.add("CELAD");
                break;

            default:
        }

        return a;
    }

    public void onSectionAttached(int number) {
        try {
            mTitle = getResources().getStringArray(R.array.drawer_array)[number-1];
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e1){
            e1.printStackTrace();
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.game_list, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMainListItemSelected(int position, int kind) {
        Intent intent;

        switch(kind){
            case 1:
                intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(this, PlayerActivity.class);
                startActivity(intent);
                break;

            case 3:
                intent = new Intent(this, TeamActivity.class);
                startActivity(intent);
                break;

            default:
                Toast.makeText(this, "Sorry an error occur.", Toast.LENGTH_SHORT).show();

        }
    }
}
