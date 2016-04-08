package maddiscovery.greenwich.com.maddiscovery.Adapter;

import android.widget.CheckBox;
import android.widget.TextView;

public class ViewHolder {
    private TextView textViewTitle;
    private TextView textViewTime;
    private TextView textViewContent;
    private CheckBox checkBoxEdit;

    public TextView getTextViewContent() {
        return textViewContent;
    }

    public void setTextViewContent(TextView textViewContent) {
        this.textViewContent = textViewContent;
    }

    public TextView getTextViewTitle() {
        return textViewTitle;
    }

    public void setTextViewTitle(TextView textViewTitle) {
        this.textViewTitle = textViewTitle;
    }

    public TextView getTextViewTime() {
        return textViewTime;
    }

    public void setTextViewTime(TextView textViewTime) {
        this.textViewTime = textViewTime;
    }

    public CheckBox getCheckBoxEdit() {
        return checkBoxEdit;
    }

    public void setCheckBoxEdit(CheckBox checkBoxEdit) {
        this.checkBoxEdit = checkBoxEdit;
    }
}
