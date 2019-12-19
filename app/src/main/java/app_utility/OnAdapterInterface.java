package app_utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface OnAdapterInterface {
    public void onAdapterCall(int nCall, boolean isAddition, float fData, ArrayList<DentsRVData> alDentsData);
    public void onAdd(LinkedHashMap<Integer, DentsRVData> lhmDents);
    public void onDelete(int pos);
}
