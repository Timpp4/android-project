package com.example.projectx;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class readAndWrite {

    private Context context;
    public readAndWrite(Context context) { this.context=context; }
    //public readAndWrite(RegisterFragment context) { this.context=context; }

    // Parse through userInfo.txt to validate given username and password for app access
    public boolean readFile(String username, String password) {
        try {
            File path = context.getFilesDir();
            File file = new File(path, "userInfo.txt");
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            String contents = new String(bytes);

            String[] line_parsed;
            line_parsed = contents.split("\n"); // rivi
            int arrayLength;
            int i = 0;
            arrayLength = line_parsed.length;

            while (i < arrayLength) {
                String user = line_parsed[i];
                String[] user_parsed;
                user_parsed = user.split(";");
                //System.out.println(user_parsed[0] + " ja " + user_parsed[1]);
                if (user_parsed[0].equals(username)) {
                    if (user_parsed[1].equals(password)) {
                        System.out.println("You have successfully logged in!");
                        return true;
                    }
                }
                i++;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public boolean checkIfUserExist(String username) {
        try {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, "userInfo.txt");
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            String contents = new String(bytes);

            String[] line_parsed;
            line_parsed = contents.split("\n"); // rivi
            int arrayLength;
            int i = 0;
            arrayLength = line_parsed.length;

            while (i < arrayLength) {
                String user = line_parsed[i];
                String[] user_parsed;
                user_parsed = user.split(";");
                //System.out.println(user_parsed[0] + " ja " + user_parsed[1]);
                if (user_parsed[0].equals(username)) {
                    System.out.println("kyseinen käyttäjä löytyi....");
                    return true;
                }
                i++;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }



    // Save newly created username and password for login
    public boolean writeFile(String username, String password) {
        //if (checkIfUserExist(username) == true) {
        //    System.out.println("checkifuserexist returned true, dont continue");
        //    return false;
        //}
        String row = username + ";" + password + "\n";
        try {
            File path = context.getExternalFilesDir(null);
            System.out.println("path is ... " + path);
            File file = new File(path, "userInfo.txt");
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(row.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("writefilussa");
        return true;
    }

    public boolean createNewUser(String username, String password, Double height, Double weight, Integer yearBorn, String sex) {
        // Save username and password to a separate file for login
        if (writeFile(username, password) == false) {
            System.out.println("ei voitu kirjoitella kyättistä ja salasanaa, exit and forget");
            return false;
        }
        try {
            String row = username + ";" + height.toString() + ";" + weight.toString() + ";" + yearBorn.toString() + ";" + sex.toLowerCase() + "\n";
            File path = context.getExternalCacheDir();
            File file = new File(path, username + ".txt");
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(row.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("käyttäjä luotiin onnistuneesti");
        return true;
    }























}
