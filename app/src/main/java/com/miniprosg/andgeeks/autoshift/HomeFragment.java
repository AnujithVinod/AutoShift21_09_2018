package com.miniprosg.andgeeks.autoshift;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeFragment extends Fragment {
View myview;
//TextView t1;
//    BottomNavigationView bottomNavigationView;
//    FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       myview=inflater.inflate(R.layout.home_layout,container,false);
        return myview;
    }
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        t1=(TextView)view.findViewById(R.id.textView1);
//        fragmentManager= getFragmentManager();
//        bottomNavigationView = (BottomNavigationView)view.findViewById(R.id.navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.nav_cars:
//                                Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();
//                                fragmentManager.beginTransaction().replace(R.id.content_frame,new FirstFragment()).commit();
//                                break;
//                            case R.id.nav_bike:
//                                Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();
//                                fragmentManager.beginTransaction().replace(R.id.content_frame,new SecondFragment()).commit();
//                                break;
//                            case R.id.nav_compare:
//                                Toast.makeText(getActivity(),"3",Toast.LENGTH_LONG).show();
//                                fragmentManager.beginTransaction().replace(R.id.content_frame,new ThirdFragment()).commit();
//                                break;
//                        }
//                        return true;
//                    }
//                });
//
//    }








}
