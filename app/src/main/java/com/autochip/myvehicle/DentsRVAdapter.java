package com.autochip.myvehicle;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import app_utility.Constants;
import app_utility.DentsRVData;
import app_utility.OnAdapterInterface;

import static app_utility.StaticReferenceClass.HIDE_FAB;
import static app_utility.StaticReferenceClass.SHOW_FAB;
import static app_utility.StaticReferenceClass.UPDATE_TOTAL_COST;
import static app_utility.StaticReferenceClass.UPDATE_TOTAL_TIME;

public class DentsRVAdapter extends RecyclerView.Adapter<DentsRVAdapter.MenuItemTabHolder> implements OnAdapterInterface {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> alSubMenuNames;
    ArrayAdapter<String> adapterMake;
    int length;
    int count;
    static boolean isInDeleteMode = false;
    private LinkedHashMap<Integer, DentsRVData> lhmDents = new LinkedHashMap<>();
    ArrayList<DentsRVData> alDentsData;
    float fBefore, fAfter;

    private static ArrayList<Integer> nALSelectedItemIndex = new ArrayList<>();

    public static OnAdapterInterface onAdapterInterface;
    MenuItemTabHolder holder;
    //TextView tvPrevious;
    //private FragmentManager fragmentManager;
    //private String sMainMenuName;

    public DentsRVAdapter(Context context, RecyclerView recyclerView, ArrayList<DentsRVData> alDentsData, int length) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.alDentsData = alDentsData;
        this.length = length;
        onAdapterInterface = this;
    }

    @NonNull
    @Override
    public MenuItemTabHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_dents_info, parent, false);

        return new MenuItemTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuItemTabHolder holder, final int position) {
        this.holder = holder;
        count++;

        if (alDentsData.size() > position) {
            holder.etLength.getEditText().setText(alDentsData.get(position).getLength());
            holder.etWidth.getEditText().setText(alDentsData.get(position).getWidth());
            holder.etDepth.getEditText().setText(alDentsData.get(position).getDepth());
            holder.etTime.getEditText().setText(alDentsData.get(position).getTimeInHours());
            holder.etCost.getEditText().setText(alDentsData.get(position).getCost());
            lhmDents.put(count, alDentsData.get(position));
            //hmDents.put(count, alDentsData.get(position));
        }
        holder.mcvParentID.setId(count);


        holder.etTime.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String s1 = s.toString().trim();
                if(s1.isEmpty())
                    fBefore = 0;
                 else
                    fBefore = Float.valueOf(s1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = holder.etTime.getEditText().getText().toString().trim();
                float fData = -fBefore;
                if (s1.length()>0) {
                    fAfter = Float.valueOf(s1);
                    fData = fAfter - fBefore;
                }
                DentInfoFragment.onAdapterInterface.onAdapterCall(UPDATE_TOTAL_TIME, true, fData);
                //float fData = Float.valueOf(holder.etTime.getEditText().getText().toString().trim());
                //DentInfoFragment.onAdapterInterface.onAdapterCall(UPDATE_TOTAL_TIME, false, fData);
                //Toast.makeText(context, s.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.etCost.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String s1 = s.toString().trim();
                if(s1.isEmpty())
                    fBefore = 0;
                else
                    fBefore = Float.valueOf(s1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = holder.etCost.getEditText().getText().toString().trim();
                float fData = -fBefore;
                if (s1.length()>0) {
                    fAfter = Float.valueOf(s1);
                    fData = fAfter - fBefore;
                }
                DentInfoFragment.onAdapterInterface.onAdapterCall(UPDATE_TOTAL_COST, true, fData);
                //float fData = Float.valueOf(holder.etCost.getEditText().getText().toString().trim());
                //DentInfoFragment.onAdapterInterface.onAdapterCall(UPDATE_TOTAL_COST, false, fData);
            }
        });
        //count++;
        //holder.mtvParentID.setId(count);
        //Toast.makeText(context, count+"", Toast.LENGTH_SHORT).show();
        //holder.spinnerLength.setSelection(0);
        holder.mtvDentCount.setText(String.valueOf(position + 1));
        //hmDents.put()
    }

    @Override
    public int getItemCount() {
        return length; //alBeaconInfo.size()
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onAdapterCall(int nCall, boolean isAddition, float fData) {
        Constants constants = Constants.values()[nCall];
        switch (constants) {
            case ADD_ALL_DATA:
                addAllDataToList();
                break;
        }
    }

    @Override
    public void onAdd(LinkedHashMap<Integer, DentsRVData> lhmDents) {
        //recyclerView.getAdapter().getItemCount();
        String sLength = holder.etLength.getEditText().getText().toString().trim();
        if (sLength.length() == 0) {
            Toast.makeText(context, "Please enter Length", Toast.LENGTH_SHORT).show();
            return;
        }
        String sWidth = holder.etWidth.getEditText().getText().toString().trim();
        if (sWidth.length() == 0) {
            Toast.makeText(context, "Please enter Width", Toast.LENGTH_SHORT).show();
            return;
        }
        String sDepth = holder.etDepth.getEditText().getText().toString().trim();
        if (sDepth.length() == 0) {
            Toast.makeText(context, "Please enter Depth", Toast.LENGTH_SHORT).show();
            return;
        }
        String sTime = holder.etTime.getEditText().getText().toString().trim();
        if (sTime.length() == 0) {
            Toast.makeText(context, "Please enter Time", Toast.LENGTH_SHORT).show();
            return;
        }
        String sCost = holder.etCost.getEditText().getText().toString().trim();
        if (sCost.length() == 0) {
            Toast.makeText(context, "Please enter Cost", Toast.LENGTH_SHORT).show();
            return;
        }
        length++;
        notifyItemInserted(length);
        recyclerView.smoothScrollToPosition(length);
        //this.alDentsData.add(dentsRVData);
        //DentsRVData dentsRVData = new DentsRVData(sLength, sWidth, sDepth, sTime, sCost);
        //hmDents.put(count, dentsRVData);
        //DentInfoFragment.onAdapterInterface.onAdd(alDentsData);
        //addAllDataToList();
    }

   /* private void addLastItem(){
        View itemView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(recyclerView.getChildCount())).itemView;
        TextInputLayout et = itemView.findViewById(R.id.et_length);
        String sLength = et.getEditText().getText().toString().trim();

        et = itemView.findViewById(R.id.et_width);
        String sWidth = et.getEditText().getText().toString().trim();

        et = itemView.findViewById(R.id.et_depth);
        String sDepth = et.getEditText().getText().toString().trim();

        et = itemView.findViewById(R.id.et_time);
        String sTime = et.getEditText().getText().toString().trim();

        et = itemView.findViewById(R.id.et_cost);
        String sCost = et.getEditText().getText().toString().trim();

        DentsRVData dentsRVData = new DentsRVData(sLength, sWidth, sDepth, sTime, sCost);
        alDentsData.add(dentsRVData);
        //hmDents.put(count, dentsRVData);
        DentInfoFragment.onAdapterInterface.onAdd(alDentsData);
    }*/

    private void addAllDataToList() {
        for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            //View itemView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(i)).itemView;
            TextInputLayout et = itemView.findViewById(R.id.et_length);
            String sLength = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_width);
            String sWidth = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_depth);
            String sDepth = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_time);
            String sTime = et.getEditText().getText().toString().trim();

            et = itemView.findViewById(R.id.et_cost);
            String sCost = et.getEditText().getText().toString().trim();

            DentsRVData dentsRVData = new DentsRVData(sLength, sWidth, sDepth, sTime, sCost);
            alDentsData.add(dentsRVData);
            lhmDents.put(itemView.getId(), dentsRVData);
            //hmDents.put(count, dentsRVData);
        }
        DentInfoFragment.onAdapterInterface.onAdd(lhmDents);
    }

    @Override
    public void onDelete(int pos) {
        /*for (int i = 0; i < nALSelectedItemIndex.size(); i++) {
            int ID = recyclerView.getChildAt(nALSelectedItemIndex.get(i)).getId();
            hmDents.remove(ID);
            length--;
            notifyItemRemoved(nALSelectedItemIndex.get(i));
        }
        nALSelectedItemIndex.clear();*/
        //hmDents.remove(count);

        for (int i = 0; i < nALSelectedItemIndex.size(); i++) {
            int indexToDelete = nALSelectedItemIndex.get(i);
            if (alDentsData.size() > indexToDelete)
                alDentsData.remove(indexToDelete);
            int ID = recyclerView.getChildAt(indexToDelete).getId();
            lhmDents.remove(ID);
            length--;
            notifyItemRemoved(nALSelectedItemIndex.get(i));
        }
        DentInfoFragment.onAdapterInterface.onAdd(lhmDents);
        nALSelectedItemIndex.clear();
        if(lhmDents.size()==0)
            count=0;
    }

    class MenuItemTabHolder extends RecyclerView.ViewHolder {
        /*Spinner spinnerLength;
        Spinner spinnerWidth;
        Spinner spinnerDepth;*/
        MaterialTextView mtvDentCount;
        TextInputLayout etLength;
        TextInputLayout etWidth;
        TextInputLayout etDepth;
        TextInputLayout etTime;
        TextInputLayout etCost;
        MaterialCardView mcvParentID;

        MenuItemTabHolder(View itemView) {
            super(itemView);
            mtvDentCount = itemView.findViewById(R.id.tv_dent_count);
            etLength = itemView.findViewById(R.id.et_length);
            etWidth = itemView.findViewById(R.id.et_width);
            etDepth = itemView.findViewById(R.id.et_depth);
            etTime = itemView.findViewById(R.id.et_time);
            etCost = itemView.findViewById(R.id.et_cost);
            mcvParentID = itemView.findViewById(R.id.mcv_parent_id);
            /*spinnerLength = itemView.findViewById(R.id.spinner_length);
            spinnerWidth = itemView.findViewById(R.id.spinner_width);
            spinnerDepth = itemView.findViewById(R.id.spinner_depth);
            //adapterMake = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, alSubMenuNames);
            adapterMake = new ArrayAdapter<>(context, R.layout.spinner_item, alSubMenuNames);
            adapterMake.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLength.setAdapter(adapterMake);
            spinnerWidth.setAdapter(adapterMake);
            spinnerDepth.setAdapter(adapterMake);*/
        }
    }

    interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


     /*
    below RecyclerTouchListener class is created to listen to longPress and singleTap
     */

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;


        RecyclerTouchListener(final Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    /*
                    below code works same as whatsApp list selection
                    below code works only when at least one onLongPress is triggered and the position is added to the hashset from onLongPress listener
                     */
                    //adds the view of the recyclerView
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    //int index = child.getId();
                    //int id = child.findViewById(R.id.mcv_parent_id).getId();
                    //gets the index of recyclerView
                    int index = recycleView.getChildAdapterPosition(child);
                    /*
                    below if statement checks for 2 condition.
                    below code executes when there is atleast one value in hashset and the index shouldn't be already added to the hashset
                    it sets the background to grey(which means selected) and the same will be added to hashset
                    else statement executes when the first if statement doesn't meet the conditions
                    which sets the background to white (which means unselected)
                     */
                    if (isInDeleteMode) {
                        if (nALSelectedItemIndex.size() >= 1 && !nALSelectedItemIndex.contains(index)) {
                            child.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGrey));
                            nALSelectedItemIndex.add(index);
                            DentInfoFragment.onAdapterInterface.onAdapterCall(SHOW_FAB, false,0);
                        } else {
                            child.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
                            if (nALSelectedItemIndex.size() >= 1) {
                            /*
                            index only holds the current position. so before we remove anything from arraylist. We will first check if we have the index number
                            in the arraylist and incase if it is present, we will add the index of the selected position into nRemoveIndex and then delete
                            that position
                             */
                                int nRemoveIndex = nALSelectedItemIndex.indexOf(index);
                                nALSelectedItemIndex.remove(nRemoveIndex);
                                //DentInfoFragment.onAdapterInterface.onAdapterCall(SHOW_FAB);
                            }
                            if (nALSelectedItemIndex.size() == 0)
                                DentInfoFragment.onAdapterInterface.onAdapterCall(HIDE_FAB, false,0);
                        }
                    }
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                        int index = recycleView.getChildAdapterPosition(child);
                        //int index = child.getId();
                        /*
                        below if statement checks for one condition.
                        if hashset doesn't contain the position already the background is set to grey(which means selected) as well as added to the hashset
                        else
                        it removes the position from the hashset and sets the background to white(which means unselected)
                         */
                        if (!nALSelectedItemIndex.contains(index)) {
                            nALSelectedItemIndex.add(index);
                            child.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGrey));
                        } else {
                            //nALSelectedItemIndex.size();
                            if (nALSelectedItemIndex.contains(index)) {
                                child.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
                                nALSelectedItemIndex.remove((Integer) index);
                            }
                        }
                        if (nALSelectedItemIndex.size() > 0) {
                            DentInfoFragment.onAdapterInterface.onAdapterCall(SHOW_FAB, false,0);
                            isInDeleteMode = true;
                        } else {
                            DentInfoFragment.onAdapterInterface.onAdapterCall(HIDE_FAB, false,0);
                            isInDeleteMode = false;
                        }
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    /*
    using interface ClickListener, we will be able to perform tasks like single click and long press selection
    of Recyclerview elements.
     */
    /*interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);

        void onAdd();

        void onDelete();
    }*/

}