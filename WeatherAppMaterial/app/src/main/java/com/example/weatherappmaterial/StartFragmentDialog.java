package com.example.weatherappmaterial;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class StartFragmentDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final FragmentDialogResult dialRes = (FragmentDialogResult) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.title_dialog)
                .setMessage(R.string.message_dialog)
                .setPositiveButton(R.string.ok_button_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        dialRes.onDialogResult(getString(R. string.ok_button_dialog)) ;
                    }
                });

        return builder.create();
    }
}
