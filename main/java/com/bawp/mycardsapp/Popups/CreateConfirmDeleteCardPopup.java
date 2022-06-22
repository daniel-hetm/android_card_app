package com.bawp.mycardsapp.Popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.bawp.mycardsapp.R;

public abstract class CreateConfirmDeleteCardPopup {
    private Context context;
    private LayoutInflater inflater;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View view;
    private Button confirmButton, cancelButton;

    public CreateConfirmDeleteCardPopup(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.delete_card_popup, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        confirmButton = view.findViewById(R.id.confirmDeleteCard);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                confirm();
            }
        });

        cancelButton = view.findViewById(R.id.cancelDeleteCard);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public abstract void confirm();

}
