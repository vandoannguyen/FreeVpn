package com.freeproxy.vpnmaster.hotspot2.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "VPN" : position == 1 ? "Points" : position == 2 ? "Mores" : null;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (!fragments.get(position).isAdded())
            return fragments.get(position);
        else return null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.size();
    }
}
