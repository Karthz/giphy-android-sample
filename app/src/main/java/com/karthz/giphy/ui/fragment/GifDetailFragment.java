package com.karthz.giphy.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.karthz.giphy.R;
import com.karthz.giphy.ui.util.GlideApp;

public class GifDetailFragment extends DialogFragment {

    private ImageView imageView;
    private TextView textView;
    private String gifUrl;

    public static final String GIF_URL = "GIF_URL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Style_Dialog_FullScreen);

        Bundle bundle = getArguments();

        if (bundle == null) {
            dismiss();
        } else {
            gifUrl = bundle.getString(GIF_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gif_detail, container, false);
        imageView = view.findViewById(R.id.gif_image);
        textView = view.findViewById(R.id.gif_url);
        textView.setText(gifUrl);

        GlideApp.with(getActivity())
                .asGif()
                .placeholder(R.drawable.placeholder_gif)
                .load(gifUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        return view;
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }

        super.onDestroyView();
    }
}
