package com.fsc.TeamMikasaProject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by brian on 4/26/14.
 */
public class ControllerFragment extends Fragment {
    public static final String PREF_KEY_IP_ADDRESS = "pref_key_host_ip";
    public static final String PREF_KEY_HOST_PORT = "pref_key_host_port";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        String ipAddress = prefs.getString(PREF_KEY_IP_ADDRESS, "");
        String port = prefs.getString(PREF_KEY_HOST_PORT, "");
        if (ipAddress.equals("") || port.equals("")) {
            Intent intent = new Intent(activity, PreferencesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        UDPSender.setPort(Integer.parseInt(prefs.getString(PREF_KEY_HOST_PORT, "2390")));
        UDPSender.setIpAddress(prefs.getString(PREF_KEY_IP_ADDRESS, "127.0.0.0"));

        View viewTreeRoot = inflater.inflate(R.layout.controller_fragment, container, false);


        Button powerButton = (Button) viewTreeRoot.findViewById(R.id.imageButton);
        powerButton.setActivated(false);
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View powerButton) {
                if (UDPSender.isRunning()) {
                    UDPSender.setRunningState(false);
                    powerButton.setBackgroundResource(R.drawable.attack_on_titan_weapon5_2);

                }
                else {
                    UDPSender.setRunningState(true);
                    powerButton.setBackgroundResource(R.drawable.attack_on_titan_weapon5_2);
                    UDPSender.beginUdpLoop();
                    UDPSender.authenticate = true;
                    UDPSender.authenticate = false;
                }
            }
        });

        Button upButton = (Button) viewTreeRoot.findViewById(R.id.imageButton2);
        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.stop = false;
                        UDPSender.forward = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.forward = false;
                        UDPSender.stop = true;
                        break;
                }
                return true;
            }

        });

        Button downButton = (Button) viewTreeRoot.findViewById(R.id.down);
        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.stop = false;
                        UDPSender.reverse = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.reverse = false;
                        UDPSender.stop = true;
                        break;
                }
                return true;
            }

        });

        Button leftButton = (Button) viewTreeRoot.findViewById(R.id.left);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.realign = false;
                        UDPSender.left = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.left = false;
                        UDPSender.realign = true;
                        break;
                }
                return true;
            }

        });

        Button rightButton = (Button) viewTreeRoot.findViewById(R.id.imageButton4);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        UDPSender.realign = false;
                        UDPSender.right = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        UDPSender.right = false;
                        UDPSender.realign = true;
                        break;
                }
                return true;
            }

        });

        return viewTreeRoot;
    }
}
