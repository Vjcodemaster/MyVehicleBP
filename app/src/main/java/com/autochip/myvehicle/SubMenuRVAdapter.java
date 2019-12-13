package com.autochip.myvehicle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import app_utility.StaticReferenceClass;

public class SubMenuRVAdapter extends RecyclerView.Adapter<SubMenuRVAdapter.MenuItemTabHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> alSubMenuNames;
    private String sMainCategory;
    //TextView tvPrevious;
    //private FragmentManager fragmentManager;
    //private String sMainMenuName;

    public SubMenuRVAdapter(Context context, RecyclerView recyclerView, String sMainCategory, ArrayList<String> alSubMenuNames) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.sMainCategory = sMainCategory;
        this.alSubMenuNames = alSubMenuNames;
        //this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MenuItemTabHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_sub_menu, parent, false);

        return new MenuItemTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuItemTabHolder holder, final int position) {

        int color = ((int) (Math.random() * 16777215)) | (0xFF << 24);

        holder.cvDynamicParentBG.setCardBackgroundColor(color);

        holder.tvSubMenuName.setText(alSubMenuNames.get(position));

        holder.cvDynamicParentBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.onFragmentInteractionListener.onFragmentChange(StaticReferenceClass.OPEN_DENT_FRAGMENT, sMainCategory + "," + alSubMenuNames.get(position), false,null);
            }
        });

       /* holder.tvNumber.setText(String.valueOf(position+1));

        holder.tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvPrevious!=null)
                    tvPrevious.setBackgroundColor(context.getResources().getColor((R.color.colorPrimaryDark)));
                holder.tvNumber.setBackgroundColor(context.getResources().getColor((R.color.colorGold)));
                String sValue = holder.tvNumber.getText().toString();
                LoginActivity.onAsyncInterfaceListener.onResultReceived("NUMBER_RECEIVED", 1, sValue, null);
                tvPrevious = holder.tvNumber;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return alSubMenuNames.size(); //alBeaconInfo.size()
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class MenuItemTabHolder extends RecyclerView.ViewHolder {
        MaterialCardView cvDynamicParentBG;
        MaterialTextView tvSubMenuName;

        MenuItemTabHolder(View itemView) {
            super(itemView);
            cvDynamicParentBG = itemView.findViewById(R.id.cv_dynamic_color_bg);
            tvSubMenuName = itemView.findViewById(R.id.tv_submenu_name);
        }
    }

}