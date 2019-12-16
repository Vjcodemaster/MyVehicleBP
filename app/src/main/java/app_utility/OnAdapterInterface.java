package app_utility;

import java.util.LinkedHashMap;

public interface OnAdapterInterface {
    public void onAdapterCall(int nCall);
    public void onAdd(LinkedHashMap<Integer, DentsRVData> lhmDents);
    public void onDelete(int pos);
}
