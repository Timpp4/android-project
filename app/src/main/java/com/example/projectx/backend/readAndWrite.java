package com.example.projectx.backend;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class readAndWrite {

    private final Context context;
    public readAndWrite(Context context) { this.context=context; }

    /**
     * This function is used to log user in and
     * creates a temporary file to identify user.
     */
    public boolean readFile(String username, String password) {
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
                            e.printStackTrace();
                        }
                        return true;
                    }
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This function checks if username is taken.
     * It is called when a new user is created.
     */
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
                if (user_parsed[0].equals(username)) {
                    return true;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This function saves newly created username
     * and password for login.
     */
    public boolean writeFile(String username, String password) {
        if (username.trim().length() == 0 || password.length() < 12){
            return false;
        }
        if (checkIfUserExist(username)) {
            return false;
        }
        String row = username + ";" + password + "\n";
        try {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, "userInfo.txt");
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(row.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This function creates a new user. It takes parameters from
     * the user and check if they are valid.
     */
    public boolean createNewUser(String username, String password, Double height, Double weight, Integer yearBorn, String sex) {
        if (height < 0 || weight < 0 || yearBorn < 0){
            return false;
        }
        if (!writeFile(username, password)) {
            return false;
        }
        try {
            String row = username + ";" + height.toString() + ";" + weight.toString() + ";" + yearBorn.toString() + ";" + sex.toLowerCase() + "\n";
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
            String row_dateWeight = date + ";" + weight.toString() + "\n";
            File path = context.getExternalFilesDir(null);
            File file = new File(path, username + ".txt");
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(row.getBytes());
            stream.write(row_dateWeight.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This function reads user's personal data for BMI calculation
     * and graphical data.
     */
    public ArrayList<DataObject> readUserData() {
        String username = getUsername();
        ArrayList<DataObject> dataObject = new ArrayList<>();
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
            e.printStackTrace();
        }
        Collections.sort(dataObject);
        return dataObject;
    }

    /**
     * This function reads user's input from home view and
     * saves it to a file.
     */
    public void insertWeight(String date, Double weight) {
        try {
            String contents = getUsername();
            String row = date + ";" + weight + "\n";
            File path = context.getExternalFilesDir(null);
            File file_insertWeight = new File(path, contents + ".txt");
            FileOutputStream stream = new FileOutputStream(file_insertWeight, true);
            stream.write(row.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function reads user's personal data for profile view.
     */
    public List<String> profileInfo() {
        List<String> infoList = new ArrayList<>();
        try {
            String username = getUsername();
            File path = context.getExternalFilesDir(null);
            File file = new File(path, username + ".txt");
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            String contents = new String(bytes);
            String[] line_parsed;
            line_parsed = contents.split("\n"); // row
            int arrayLength;
            int i = 0;
            arrayLength = line_parsed.length;
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
            e.printStackTrace();
        }
        return infoList;
    }

    /**
     * This function finds user in-use at the time from tmp.txt file
     * file uses tmp.txt file which include user in-use
     * @return username in string
     */
    public String getUsername () {
        String username="";
        try {
            File path = context.getExternalFilesDir(null);
            File file = new File(path, "tmp.txt");
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            username = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * This function reads user's personal data
     * and displays it in the info view.
     */
    public String displayData() {
        String username = getUsername();
        StringBuilder profile_data= new StringBuilder();
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
            line_parsed = contents.split("\n"); // Row
            int arrayLength;
            int i = 0;
            arrayLength = line_parsed.length;
            while (i < arrayLength) {
                //profile_data = profile_data + i + " --- " + line_parsed[i] + "\n";
                profile_data.append(i).append(" --- ").append(line_parsed[i]).append("\n");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile_data.toString();
    }

    /**
     * This function removes personal data with corresponding id.
     * It then displays the new data in the info view and
     * updates user's personal data file.
     */
    public String removeData(Integer id) {
        String username = getUsername();
        StringBuilder profile_data= new StringBuilder();
        StringBuilder profile_data_toFile= new StringBuilder();
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
            line_parsed = contents.split("\n"); // Row
            int arrayLength;
            int i = 0;
            arrayLength = line_parsed.length;
            boolean idBoolean = false;
            while (i < arrayLength) {
                if (i==0) {
                    profile_data.append(i).append(" --- ").append(line_parsed[i]).append("\n");
                    profile_data_toFile.append(line_parsed[i]).append("\n");
                } else if (id==i) {
                    idBoolean = true;
                } else if (id != i) {
                    profile_data_toFile.append(line_parsed[i]).append("\n");
                    if (!idBoolean) {
                        profile_data.append(i).append(" --- ").append(line_parsed[i]).append("\n");
                    } else {
                        profile_data.append(i - 1).append(" --- ").append(line_parsed[i]).append("\n");
                    }
                }
                i++;
            }
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(profile_data_toFile.toString().getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile_data.toString();
    }
}
