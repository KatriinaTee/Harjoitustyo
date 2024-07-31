package com.example.harjoitusty;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.harjoitusty.fragments.FragmentA;
import com.example.harjoitusty.fragments.FragmentB;
import com.example.harjoitusty.fragments.FragmentC;


public class TabPagerAdapter extends FragmentStateAdapter {
    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentA();
            case 1:
                return new FragmentB();
            case 2:
            default:
                return new FragmentC();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
