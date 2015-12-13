package silicar.brady.libs.view.interfaces;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Work on 2015/10/11.
 */
public interface DialogInitInterface extends DialogInterface {

    public interface OnDialogInitListener {
        void initDialog(Dialog dialog, View contentView);
    }
}
