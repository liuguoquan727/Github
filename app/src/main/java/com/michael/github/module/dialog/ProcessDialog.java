package com.michael.github.module.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.michael.github.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

/**
 */

public class ProcessDialog {
  private final DialogPlus mDialog;
  private final ProgressBar mProgressBar;
  private final TextView mContent;
  private final TextView mDescription;

  public ProcessDialog(DialogPlus dialog) {
    this.mDialog = dialog;
    this.mProgressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);
    this.mContent = (TextView) dialog.findViewById(R.id.content);
    this.mDescription = (TextView) dialog.findViewById(R.id.description);
  }

  public static ProcessDialog create(Context context) {
    return create(context, false);
  }

  public static ProcessDialog create(Context context, boolean cancelable) {
    return new ProcessDialog(DialogPlus.newDialog(context)
        .setContentBackgroundResource(R.drawable.bg_border_corners5_black)
        .setContentHolder(new ViewHolder(R.layout.dialog_loading_content_block))
        .setCancelable(cancelable)
        .setGravity(Gravity.CENTER)
        .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
        .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
        .create());
  }

  public ProcessDialog hideProgressBar() {
    this.mProgressBar.setVisibility(View.GONE);
    return this;
  }

  public ProcessDialog content(CharSequence text) {
    this.mContent.setVisibility(View.VISIBLE);
    this.mContent.setText(text);
    return this;
  }

  public ProcessDialog description(CharSequence text) {
    this.mDescription.setVisibility(View.VISIBLE);
    this.mDescription.setText(text);
    return this;
  }

  public ProcessDialog show() {
    this.mDialog.show();
    return this;
  }

  public void dismiss() {
    this.mDialog.dismiss();
  }

  public DialogPlus dialog() {
    return this.mDialog;
  }
}
