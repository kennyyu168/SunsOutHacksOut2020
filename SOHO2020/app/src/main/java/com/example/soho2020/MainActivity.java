package com.example.soho2020;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingAddButton; // Floating button for adding new task
    private boolean isNewTaskFragmentDisplayed = false; // Checks for fragment displaying

    private RecyclerView recyclerView; // Recycler View to display the files
    private RecyclerView.Adapter mAdapter; // Attach and adapter for data to be displayed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the recycler view adapter is there or not
        if (mAdapter == null) {
            // Get fragment and RecyclerView from the layout and connect them
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.task_list);
            recyclerView = (RecyclerView) currentFragment.getView();
            mAdapter = ((RecyclerView) currentFragment.getView()).getAdapter();
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(recyclerView.getContext(),
                            DividerItemDecoration.VERTICAL));
            // new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        }

        mFloatingAddButton = findViewById(R.id.new_task);
        mFloatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the fragment if it's not already showing
                if (!isNewTaskFragmentDisplayed) {
                    // fade background
                    View fadeBackground = findViewById(R.id.fade_background);
                    fadeBackground.setVisibility (View.VISIBLE);
                    fadeBackground.animate().alpha(0.5f);

                    // open the fragment
                    displayFragment();
                } else {
                    // jank close it for now
                    closeFragment();

                    // take away fading background
                    View fadeBackground = findViewById(R.id.fade_background);
                    fadeBackground.setVisibility(GONE);
                }
            }
        });
    }

    /**
     * Displays the fragment using a FragmentManager.
     */
    public void displayFragment() {
        // Create a new task fragment
        NewTaskFragment newTaskFragment = NewTaskFragment.newInstance();

        // Get Fragment manager and start a transaction to add the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the NewTaskFragment
        fragmentTransaction.add(R.id.fragment_container,
                newTaskFragment).addToBackStack(null).commit();

        // Set boolean flag to indicate fragment is open
        isNewTaskFragmentDisplayed = true;
    }

    /**
     * Closes the fragment.
     */
    public void closeFragment() {
        // Get the fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Check to see if the fragment is already showing
        NewTaskFragment newTaskFragment =
                (NewTaskFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (newTaskFragment != null) {
            // Create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(newTaskFragment).commit();
        }

        // Set boolean flag to indicate fragment is closed
        isNewTaskFragmentDisplayed = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}