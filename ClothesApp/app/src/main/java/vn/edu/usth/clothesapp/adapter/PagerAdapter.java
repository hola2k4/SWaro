package vn.edu.usth.clothesapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import vn.edu.usth.clothesapp.fragment.ChatFragment;

import vn.edu.usth.clothesapp.Chat.ChatFragment;

import vn.edu.usth.clothesapp.fragment.MyClosetFragment;
import vn.edu.usth.clothesapp.fragment.StylistFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new StylistFragment();
            case 1:
                return new ChatFragment();
            case 2:
                return new MyClosetFragment();
            default:

                break;
        }
        return null;

                throw new IllegalArgumentException("Invalid position: " + position);
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }
}
