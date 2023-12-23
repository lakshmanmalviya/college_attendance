package com.example.attendance.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.attendance.Fragments.TeacherDeleteOps;
import com.example.attendance.Fragments.TeacherInsertOps;
import com.example.attendance.Fragments.TeacherUpdateOps;

public class TeacherFragAdapter extends FragmentPagerAdapter {

    public TeacherFragAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new TeacherInsertOps();
            case 1:
                return  new TeacherDeleteOps();
            case 2:
                return  new TeacherUpdateOps();
            case 3:
                return  new TeacherDeleteOps();
            case 4:
                return  new TeacherDeleteOps();

            default:  new TeacherInsertOps();
        }
        return new TeacherInsertOps();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position){
            case 0:
                title = "Insert";
                break;
            case 1:
                title = "Delete";
                break;
            case 2:
                title = "Update";
                break;
                case 3:
                title = "NewUpdate";
                break;
                case 4:
                title = "NewUpdate";
                break;
            default: title = "Insert";
        }
        return  title;
    }
}
