package com.example.projectx.backend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class readAndWrite {


    private Context context;
    public readAndWrite(Context context) { this.context=context; }
    //public readAndWrite(RegisterFragment context) { this.context=context; }

    // Parse through userInfo.txt to validate given username and password for app access
    public boolean readFile(String username, String password) {
        try {
            File path = context.getExternalFilesDir(null);
            System.out.println("path is ... " + path);
            File file = new File(path, "userInfo.txt");
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            String contents = new String(bytes);

            String[] line_parsed;
            line_parsed = contents.split("\n"); // Row
            int arrayLength;
            int i = 0;
            arrayLength = line_parsed.length;

            while (i < arrayLength) {
                String user = line_parsed[i];
                String[] user_parsed;
                user_parsed = user.split(";");
                if (user_parsed[0].equals(username)) {
                    if (user_parsed[1].equals(password)) {
                        try {
                            File file_tmp = new File(path, "tmp.txt");
                            FileOutputStream stream = new FileOutputStream(file_tmp);
                            stream.write(username.getBytes());
                            stream.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        System.out.println("You have successfully logged in! (readandwrite.java)");
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
        if (username.trim().length() == 0 || password.length() < 12){
            return false;
        }
        if (checkIfUserExist(username) == true) {
            System.out.println("checkifuserexist returned true, dont continue");
            return false;
        }
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
        if (height < 0 || weight < 0 || yearBorn < 0){
            System.out.println("Tiedot epätäydellisiä");
            return false;
        }
        // Save username and password to a separate file for login
        if (writeFile(username, password) == false) {
            System.out.println("ei voitu kirjoitella kyättistä ja salasanaa, exit and forget");
            return false;
        }
        try {
            String row = username + ";" + height.toString() + ";" + weight.toString() + ";" + yearBorn.toString() + ";" + sex.toLowerCase() + "\n";
            String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
            String row_dateWeight = date + ";" + weight.toString() + "\n";

            File path = context.getExternalFilesDir(null);
            File file = new File(path, username + ".txt");
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(row.getBytes());
            stream.write(row_dateWeight.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("käyttäjä luotiin onnistuneesti");
        return true;
    }
    public ArrayList<DataObject> readUserData() {
        //TODO: Vaihda username
        String username = "juho";
        ArrayList<DataObject> dataObject = new ArrayList<DataObject>();
        try {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, username + ".txt");
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
                if (i != 0){
                    String user = line_parsed[i];
                    String[] user_parsed;
                    user_parsed = user.split(";");
                    @SuppressLint("SimpleDateFormat") Date date =new SimpleDateFormat("dd.MM.yyyy")
                            .parse(user_parsed[0]);
                    double weight = Double.parseDouble(user_parsed[1]);
                    dataObject.add(new DataObject(date, weight));
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Collections.sort(dataObject);

        return dataObject;
    }

    public void insertWeight(String date, Double weight) {
        try {
            //Find where to write
            File path = context.getExternalFilesDir(null);
            File file = new File(path, "tmp.txt");
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            String contents = new String(bytes); //Username = contents

            String row = date + ";" + weight + "\n";

            File file_insertWeight = new File(path, contents + ".txt");
            FileOutputStream stream = new FileOutputStream(file_insertWeight, true);
            stream.write(row.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public List<String> profileInfo() {
        List<String> infoList = new ArrayList<String>();
        try {
            File path = context.getExternalFilesDir(null);
            File file_tmp = new File(path, "tmp.txt");
            int length_tmp = (int) file_tmp.length();
            byte[] bytes_tmp = new byte[length_tmp];
            FileInputStream in_tmp = new FileInputStream(file_tmp);
            in_tmp.read(bytes_tmp);
            in_tmp.close();
            String username = new String(bytes_tmp);

            File file = new File(path, username + ".txt");
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
            //Kommentti
            System.out.println(arrayLength);
            while (i < arrayLength) {
                String row = line_parsed[i];
                String[] row_parsed;
                row_parsed = row.split(";");
                if (i == 0) {
                    infoList.add(row_parsed[0]);
                    infoList.add(row_parsed[1]);
                    infoList.add(row_parsed[3]);
                    infoList.add(row_parsed[4]);
                }
                else if (i == arrayLength-1) {
                    infoList.add(row_parsed[1]);
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("profiili error " + e);
        }
        return infoList;
    }






}
