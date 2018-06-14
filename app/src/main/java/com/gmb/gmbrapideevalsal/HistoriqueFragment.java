package com.gmb.gmbrapideevalsal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmb.gmbrapideevalsal.tools.BothYearResult;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HistoriqueFragment extends Fragment {

    public static final String TYPE_HISTO_HISTO = "histoCalcul";
    public static final String TYPE_HISTO_SAVED = "savedCalcul";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_TYPE_HISTO = "typeHisto";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String typeHisto;
    private OnListFragmentInteractionListener mListener;

    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoriqueFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HistoriqueFragment newInstance(String typeHisto,int columnCount) {
        HistoriqueFragment fragment = new HistoriqueFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_TYPE_HISTO,typeHisto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            typeHisto=getArguments().getString(ARG_TYPE_HISTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getContext());



        String lstStr=shared.getString(typeHisto,"");

        Log.e("Historique"," Historique je suis dans le onCreateView cas 1 avec result->"+lstStr);

        if(lstStr==null || lstStr.equalsIgnoreCase("")){


            Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.empty_list),
                    Snackbar.LENGTH_SHORT).show();
            mListener.launchFragment(null,false,0);
            return view;
        }

        ArrayList<BothYearResult> lst=BothYearResult.getListFromString(lstStr);
        // Set the adapter
        /*if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(getContext(),lst, mListener));
        }*/


        recyclerView=(RecyclerView) view.findViewById(R.id.list);

        if(recyclerView!=null){

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(getContext(),lst, mListener));
        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(BothYearResult item);

        public void launchFragment(Fragment newFragment, boolean addToBackStack,int tabNum);
    }
}
