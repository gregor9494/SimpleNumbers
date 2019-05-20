package com.gg.simplenumbers.base.assertions;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import org.hamcrest.Matcher;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by ggaworowski on 18.05.2019.
 */
public class RecyclerViewItemCountAssertion implements ViewAssertion {

    private final Matcher<Integer> matcher;

    public RecyclerViewItemCountAssertion(int expectedCount) {
        this.matcher = is(expectedCount);
    }

    public RecyclerViewItemCountAssertion(Matcher<Integer> matcher) {
        this.matcher = matcher;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assert adapter != null;
        assertThat(adapter.getItemCount(), matcher);
    }
}
