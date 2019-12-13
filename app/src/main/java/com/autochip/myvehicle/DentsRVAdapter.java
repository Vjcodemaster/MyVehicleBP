package com.autochip.myvehicle;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.HashMap;

import app_utility.OnAdapterInterface;
import app_utility.StaticReferenceClass;

import static app_utility.StaticReferenceClass.HIDE_FAB;
import static app_utility.StaticReferenceClass.SHOW_FAB;

public class DentsRVAdapter extends RecyclerView.Adapter<DentsRVAdapter.MenuItemTabHolder> implements OnAdapterInterface {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> alSubMenuNames;
    ArrayAdapter<String> adapterMake;
    int length;
    static boolean isInDeleteMode = false;
    private HashMap<Integer, HashMap<String, String>> hmDents = new HashMap<>();

    private static ArrayList<Integer> nALSelectedItemIndex = new ArrayList<>();

    public static OnAdapterInterface onAdapterInterface;
    MenuItemTabHolder holder;
    //TextView tvPrevious;
    //private FragmentManager fragmentManager;
    //private String sMainMenuName;

    public DentsRVAdapter(Context context, RecyclerView recyclerView, int length) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.length = length;
        onAdapterInterface = this;
        //this.alSubMenuNames = alSubMenuNames;
        //this.fragmentManager = fragmentManager;
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
        //holder.spinnerLength.setSelection(0);
        holder.mtvTotalDents.setText(String.valueOf(position+1));
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
    public void onAdapterCall(int nCall) {

    }

    @Override
    public void onAdd() {
        //recyclerView.getAdapter().getItemCount();
        if(holder.etLength.getEditText().getText().toString().trim().length()==0){
            Toast.makeText(context, "Please enter Length", Toast.LENGTH_SHORT).show();
            return;
        }
        if(holder.etWidth.getEditText().getText().toString().trim().length()==0){
            Toast.makeText(context, "Please enter Width", Toast.LENGTH_SHORT).show();
            return;
        }
        if(holder.etDepth.getEditText().getText().toString().trim().length()==0){
            Toast.makeText(context, "Please enter Depth", Toast.LENGTH_SHORT).show();
            return;
        }
        if(holder.etTime.getEditText().getText().toString().trim().length()==0){
            Toast.makeText(context, "Please enter Time", Toast.LENGTH_SHORT).show();
            return;
        }
        if(holder.etCost.getEditText().getText().toString().trim().length()==0){
            Toast.makeText(context, "Please enter Cost", Toast.LENGTH_SHORT).show();
            return;
        }
        length++;
        notifyItemInserted(length);
        recyclerView.smoothScrollToPosition(length);
    }

    @Override
    public void onDelete() {

    }

    class MenuItemTabHolder extends RecyclerView.ViewHolder {
        /*Spinner spinnerLength;
        Spinner spinnerWidth;
        Spinner spinnerDepth;*/
        MaterialTextView mtvTotalDents;
        TextInputLayout etLength;
        TextInputLayout etWidth;
        TextInputLayout etDepth;
        TextInputLayout etTime;
        TextInputLayout etCost;

        MenuItemTabHolder(View itemView) {
            super(itemView);
            mtvTotalDents = itemView.findViewById(R.id.tv_total_dent);
            etLength = itemView.findViewById(R.id.et_length);
            etWidth = itemView.findViewById(R.id.et_width);
            etDepth = itemView.findViewById(R.id.et_depth);
            etTime = itemView.findViewById(R.id.et_time);
            etCost = itemView.findViewById(R.id.et_cost);
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
                    //gets the index of recyclerView
                    int index = recycleView.getChildAdapterPosition(child);
                    /*
                    below if statement checks for 2 condition.
                    below code executes when there is atleast one value in hashset and the index shouldn't be already added to the hashset
                    it sets the background to grey(which means selected) and the same will be added to hashset
                    else statement executes when the first if statement doesn't meet the conditions
                    which sets the background to white (which means unselected)
                     */
                    if(isInDeleteMode) {
                        if (nALSelectedItemIndex.size() >= 1 && !nALSelectedItemIndex.contains(index)) {
                            child.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGrey));
                            nALSelectedItemIndex.add(index);
                            DentInfoFragment.onAdapterInterface.onAdapterCall(SHOW_FAB);
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
                                DentInfoFragment.onAdapterInterface.onAdapterCall(HIDE_FAB);
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
                        if(nALSelectedItemIndex.size()>0){
                            DentInfoFragment.onAdapterInterface.onAdapterCall(SHOW_FAB);
                            isInDeleteMode = true;
                        } else {
                            DentInfoFragment.onAdapterInterface.onAdapterCall(HIDE_FAB);
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