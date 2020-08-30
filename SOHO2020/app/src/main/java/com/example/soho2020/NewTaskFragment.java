package com.example.soho2020;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTaskFragment extends Fragment {

    public NewTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_new_task, container, false);
        Button addshit = rootview.findViewById(R.id.add_new_task);
        addshit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // return inflater.inflate(R.layout.fragment_new_task, container, false);
    }

    /**
     * Instantiating the fragment and returning the fragment to the activity.
     */
    public static NewTaskFragment newInstance() { return new NewTaskFragment();
    }




}