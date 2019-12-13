package app_utility;

import android.net.Uri;

public interface OnFragmentInteractionListener {
    void onFragmentChange(int nCase, String sCase, boolean isVisible, Uri uri);
    void onActivityToFragment(int nCase, String sCase, Uri uri);
}
