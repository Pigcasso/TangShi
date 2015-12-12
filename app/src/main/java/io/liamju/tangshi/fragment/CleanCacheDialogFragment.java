package io.liamju.tangshi.fragment;

import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;

import io.liamju.tangshi.R;
import io.liamju.tangshi.utils.DataCleanManager;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/7
 */
public class CleanCacheDialogFragment extends AppCompatDialogFragment {

    private OnCacheDataListener onCacheDataListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.certain_clean_data)
                .setPositiveButton(R.string.btn_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params) {
                                DataCleanManager.cleanApplicationData(getContext());
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                if (onCacheDataListener != null) {
                                    onCacheDataListener.onSuccess();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.btn_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    public void setOnCacheDataListener(OnCacheDataListener onCacheDataListener) {
        this.onCacheDataListener = onCacheDataListener;
    }

    public interface OnCacheDataListener {
        void onSuccess();

        void onFailed();
    }
}
