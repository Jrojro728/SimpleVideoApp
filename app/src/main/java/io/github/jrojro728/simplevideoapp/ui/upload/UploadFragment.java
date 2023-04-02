package io.github.jrojro728.simplevideoapp.ui.upload;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

import io.github.jrojro728.simplevideoapp.R;
import io.github.jrojro728.simplevideoapp.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {

    private FragmentUploadBinding binding;
    private int mTag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UploadViewModel uploadViewModel =
                new ViewModelProvider(this).get(UploadViewModel.class);

        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textUpload;
        uploadViewModel.getText(getContext()).observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
