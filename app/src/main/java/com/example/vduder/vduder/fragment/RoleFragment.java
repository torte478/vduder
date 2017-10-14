package com.example.vduder.vduder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.vduder.vduder.Activity.UserListActivity;
import com.example.vduder.vduder.Model.Role;
import com.example.vduder.vduder.Model.User;
import com.example.vduder.vduder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dmitry on 14.10.17.
 */


public class RoleFragment extends Fragment {

    private static final int LAYOUT = R.layout.fragment_role;


    private LoginFragment loginFrag;
    private FragmentManager fragmentManager;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

    private Button dudeBtn;
    private Button noDudeBtn;
    private Button logOutBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        dudeBtn = (Button) view.findViewById(R.id.btn_dude);
        noDudeBtn = (Button) view.findViewById(R.id.btn_noDude);
        logOutBtn = (Button) view.findViewById(R.id.btn_logOut);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        dudeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Вы ДУДЬ",
                            Toast.LENGTH_SHORT).show();
                   writeRoleDude(user.getUid());
                }
            });

        noDudeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Вы НЕ ДУДЬ",
                            Toast.LENGTH_SHORT).show();
                    writeRoleNoDude(user.getUid());
                }
            });
        logOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logOut();
                }
            });
        return view;
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        loginFrag = new LoginFragment();
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frgmCont, loginFrag);
        fragmentTransaction.commit();
    }
    private void writeRoleDude(String id) {
        Role role = new Role(Role.VdudRole ,id);
        mDatabase.child("role").child("dude").child(id).setValue(role);
        routingActivity();
    }
    private void writeRoleNoDude(String id) {
        Role role = new Role(Role.GuestRole ,id);
        mDatabase.child("role").child("noDude").child(id).setValue(role);
        routingActivity();
    }
    private void routingActivity() {
        Intent myIntent = new Intent(getActivity(), UserListActivity.class);
        getActivity().startActivity(myIntent);
    }
}