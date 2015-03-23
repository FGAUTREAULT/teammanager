package com.perso.fab.teammanager.application.main;


        import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.perso.fab.teammanager.R;
import com.perso.fab.teammanager.adapters.MySimpleArrayAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends android.support.v4.app.Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * The argument datas
     */
    private static final String ARG_SECTION_DATA = "section_data";
    private ManagerListActivity mListener;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListFragment newInstance(int sectionNumber, ArrayList<String> datas) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putStringArrayList(ARG_SECTION_DATA, datas);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ArrayList<String> list = (ArrayList<String>) getArguments().get(ARG_SECTION_DATA);

        ListView listview = (ListView) rootView.findViewById(R.id.list_view);
        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), list);
        listview.setAdapter(adapter);

        addListeners(listview);

        return rootView;
    }

    private void addListeners(ListView listview) {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    final int position, long id) {
                mListener.onMainListItemSelected(position,(int) getArguments().get(ARG_SECTION_NUMBER));
            }

        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ManagerListActivity) activity;
            mListener.onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
