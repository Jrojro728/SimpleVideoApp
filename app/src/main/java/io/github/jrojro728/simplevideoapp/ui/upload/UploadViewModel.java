package io.github.jrojro728.simplevideoapp.ui.upload;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.github.jrojro728.simplevideoapp.R;

public class UploadViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public UploadViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText(Context context) {
        mText.setValue(context.getString(R.string.welcome_upload));
        return mText;
    }
}
