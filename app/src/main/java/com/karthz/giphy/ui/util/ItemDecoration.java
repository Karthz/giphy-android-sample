package com.karthz.giphy.ui.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    private ItemDecoration(final int offset) {
        this.offset = offset;
    }

    public ItemDecoration(@NonNull final Context context, @DimenRes final int resourceId) {
        this(context.getResources().getDimensionPixelSize(resourceId));
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(offset, offset, offset, offset);
    }
}
