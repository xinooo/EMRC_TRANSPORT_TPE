package com.emrc_transport_tpe;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by An on 2018/9/20.
 */

public class MainSignOutput extends Thread{
    Bitmap bmp = null;

    public MainSignOutput(Bitmap bmp) {
        this.bmp = bmp;
    }

    public void run() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            DataInputStream din = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
            DataOutputStream dos = new DataOutputStream(MainActivity.skt.getOutputStream());
            int size = baos.toByteArray().length;
            byte[] data = new byte[size];
            din.read(data);
            dos.writeInt(size);
            dos.write(data);
            din.close();
        } catch (IOException e) {
            // System.err.println("CheckedIOTest: " + e);
        }
    }
}
