package com.google.sites.lostandfoundapp.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.google.sites.lostandfoundapp.R;
import com.google.sites.lostandfoundapp.adapter.ExpandableListAdapter;
import com.google.sites.lostandfoundapp.database.AppDatabase;
import com.google.sites.lostandfoundapp.database.entities.ImageEntity;
import com.google.sites.lostandfoundapp.log.ToastLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Isaac on 3/6/2018.
 */

public class LocateFragment extends Fragment {
    private static LocateFragment INSTANCE;

    private static final ToastLog TOAST_LOG = ToastLog.getInstance();

    private ExpandableListAdapter expListAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private Map<String, List<ImageEntity>> listDataChild;
    private int lastDataHeaderOpen = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locate_layout, container, false);

        expListView = view.findViewById(R.id.locate_exp_list_view);

        prepareListData(view);
        expListAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(), listDataHeader, listDataChild);

        expListView.setAdapter(expListAdapter);
        expListView.setOnGroupExpandListener(listenerOnGroupExpendListener);
        expListView.setOnItemLongClickListener(listenerOnItemLongClickListener);

        return view;
    }

    public static LocateFragment getInstance() {
        if(null == INSTANCE) {
            INSTANCE = new LocateFragment();
        }
        return INSTANCE;
    }

    private ExpandableListView.OnGroupExpandListener listenerOnGroupExpendListener
            = new ExpandableListView.OnGroupExpandListener() {
        @Override
        public void onGroupExpand(int groupPosition) {
            if (lastDataHeaderOpen != -1 && groupPosition != lastDataHeaderOpen) {
                expListView.collapseGroup(lastDataHeaderOpen);
            }
            lastDataHeaderOpen = groupPosition;
        }
    };

    private ExpandableListView.OnItemLongClickListener listenerOnItemLongClickListener
            = new ExpandableListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                final int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                final int childPosition = ExpandableListView.getPackedPositionChild(id);
                final ImageEntity imageEntity = (ImageEntity) expListAdapter.getChild(groupPosition, childPosition);

                final Context context = view.getContext().getApplicationContext();
                final AppDatabase appDatabase = AppDatabase.getINSTANCE(context);

                expListAdapter.removeChild(groupPosition, childPosition);
                expListAdapter.notifyDataSetChanged();

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.imageDao().deleteImage(imageEntity);
                    }
                });

                return true;
            }
            return false;
        }
    };

    private void prepareListData(final View view) {
        final Context context = view.getContext().getApplicationContext();
        final AppDatabase appDatabase = AppDatabase.getINSTANCE(context);

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final List<ImageEntity> other = appDatabase.imageDao().getImages();

                listDataHeader.add("Other");

                listDataChild.put(listDataHeader.get(0), other);
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
