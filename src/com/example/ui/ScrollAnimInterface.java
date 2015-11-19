package com.example.ui;

import android.widget.AbsListView;

public interface ScrollAnimInterface {
    /**
     * For ListView usage
     */
    void onScroll(AbsListView view, int firstVisibleItem, int endFadeItem);

    /**
     * For ScrollView usage
     */
    void onScroll(int y);
}
