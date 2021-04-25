package com.example.projectx.backend;

public class PasswordValidator {

    public static boolean isValid(String password) {
        // Check password length
        if ((password.length() < 12)) {
            System.out.println("Salasana on liian lyhyt");
            return false;
        }

        if (password.contains(" ")) {
            return false;
        }
        // Check if includes numbers 0 to 9
        if (true) {
            int count = 0;

            for (int i = 0; i <= 9; i++) {
                String str1 = Integer.toString(i);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                System.out.println("Salasana ei sisällä numeroita");
                return false;
            }
        }
        // Check if includes characters
        if (!(password.contains("@") || password.contains("#")
                || password.contains("!") || password.contains("~")
                || password.contains("$") || password.contains("%")
                || password.contains("^") || password.contains("&")
                || password.contains("*") || password.contains("(")
                || password.contains(")") || password.contains("-")
                || password.contains("+") || password.contains("/")
                || password.contains(":") || password.contains(".")
                || password.contains(", ") || password.contains("<")
                || password.contains(">") || password.contains("?")
                || password.contains("|"))) {
            System.out.println("Salasana ei sisällä erikoismerkkejä");
            return false;
        }
        // Check if includes capital letters
        if (true) {
            int count = 0;

            for (int i = 65; i <= 90; i++) {
                char c = (char) i;

                String str1 = Character.toString(c);
                if (password.contains(str1)) {
                    count = 1;
                }
            }

            if (count == 0) {
                System.out.println("Salasana ei sisällä isoja kirjaimia");
                return false;
            }
        }
        // Check if includes small letters
        if (true) {
            int count = 0;


            for (int i = 90; i <= 122; i++) {


                char c = (char) i;
                String str1 = Character.toString(c);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                System.out.println("Salasana ei sisällä pieniä kirjaimia");
                return false;
            }
        }

        return true;
    }

}
