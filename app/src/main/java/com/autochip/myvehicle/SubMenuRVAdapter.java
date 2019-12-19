package com.autochip.myvehicle;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import app_utility.CircleImageView;
import app_utility.DentsRVData;
import app_utility.OnAdapterInterface;
import app_utility.StaticReferenceClass;
import app_utility.SubMenuRVData;

public class SubMenuRVAdapter extends RecyclerView.Adapter<SubMenuRVAdapter.MenuItemTabHolder> implements OnAdapterInterface {

    private Context context;
    private RecyclerView recyclerView;
    private ArrayList<String> alSubMenuNames;
    private ArrayList<String> alImagePath;
    private String sMainCategory;
    private HashMap<String, SubMenuRVData> hmSCWithImagePath = new HashMap<>();
    //TextView tvPrevious;
    //private FragmentManager fragmentManager;
    //private String sMainMenuName;

    /*public SubMenuRVAdapter(Context context, RecyclerView recyclerView, String sMainCategory, ArrayList<String> alSubMenuNames,
                            ArrayList<String> alImagePath) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.sMainCategory = sMainCategory;
        this.alSubMenuNames = alSubMenuNames;
        this.alImagePath = alImagePath;
        //this.fragmentManager = fragmentManager;
    }*/

    public SubMenuRVAdapter(Context context, RecyclerView recyclerView, String sMainCategory, ArrayList<String> alSubMenuNames,
                            HashMap<String, SubMenuRVData> hmSCWithImagePath) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.sMainCategory = sMainCategory;
        this.alSubMenuNames = alSubMenuNames;
        this.hmSCWithImagePath = hmSCWithImagePath;
        //alSubMenuNames.addAll(hmSCWithImagePath.keySet());
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
                MainActivity.onFragmentInteractionListener.onFragmentChange(StaticReferenceClass.OPEN_DENT_FRAGMENT, sMainCategory + "," + alSubMenuNames.get(position), false, null);
            }
        });

        /*final Runnable r = new Runnable() {
            public void run() {
                holder.civAvatar.setImageURI(Uri.fromFile(new File(alImagePath.get(position))));
            }
        };
        r.run();*/
        if (hmSCWithImagePath.containsKey(alSubMenuNames.get(position))) {
            //holder.civAvatar.setImageURI(Uri.fromFile(new File(Objects.requireNonNull(hmSCWithImagePath.get(alSubMenuNames.get(position))))));
            if (hmSCWithImagePath.get(alSubMenuNames.get(position)).getImagePath().contains(","))
                holder.civAvatar.setImageURI(Uri.fromFile(new File(Objects.requireNonNull(hmSCWithImagePath.get(alSubMenuNames.get(position))).getImagePath().split(",")[0])));
            else
                holder.civAvatar.setImageURI(Uri.fromFile(new File(Objects.requireNonNull(hmSCWithImagePath.get(alSubMenuNames.get(position))).getImagePath())));

            holder.mtvTime.setText(Objects.requireNonNull(hmSCWithImagePath.get(alSubMenuNames.get(position))).getTotalTime());
            holder.mtvCount.setText(String.valueOf(Objects.requireNonNull(hmSCWithImagePath.get(alSubMenuNames.get(position))).getTotalCount()));
            holder.mtvCost.setText(Objects.requireNonNull(hmSCWithImagePath.get(alSubMenuNames.get(position))).getTotalCost());
        }

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

    @Override
    public void onAdapterCall(int nCall, boolean isAddition, float fData, ArrayList<DentsRVData> alDentsData) {

    }

    @Override
    public void onAdd(LinkedHashMap<Integer, DentsRVData> lhmDents) {

    }

    @Override
    public void onDelete(int pos) {

    }

    static class MenuItemTabHolder extends RecyclerView.ViewHolder {
        MaterialCardView cvDynamicParentBG;
        MaterialTextView tvSubMenuName;
        MaterialTextView mtvTime;
        MaterialTextView mtvCount;
        MaterialTextView mtvCost;
        CircleImageView civAvatar;

        MenuItemTabHolder(View itemView) {
            super(itemView);
            cvDynamicParentBG = itemView.findViewById(R.id.cv_dynamic_color_bg);
            tvSubMenuName = itemView.findViewById(R.id.tv_submenu_name);
            civAvatar = itemView.findViewById(R.id.civ_avatar);
            mtvTime = itemView.findViewById(R.id.mtv_rv_time);
            mtvCount = itemView.findViewById(R.id.mtv_rv_count);
            mtvCost = itemView.findViewById(R.id.mtv_rv_cost);
        }
    }

}