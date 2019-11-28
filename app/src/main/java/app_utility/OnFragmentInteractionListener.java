package app_utility;

import android.net.Uri;

public interface OnFragmentInteractionListener {
    void onFragmentChange(String sCase);
    void onActivityToFragment(String sCase, Uri uri);
}
