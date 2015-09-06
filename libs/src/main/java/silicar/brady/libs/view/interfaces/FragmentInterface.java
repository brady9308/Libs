package silicar.brady.libs.view.interfaces;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Work on 2015/7/20.
 */
public class FragmentInterface {
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
    public interface OnFragmentInteractionListener {
        //  Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public interface OnFragmentInitListener
    {
        public void initFragment(Fragment fragment, View view);
    }
}
