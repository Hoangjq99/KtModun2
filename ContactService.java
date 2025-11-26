package com.codegym.hoang1_modun2.help_method;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();
    private final String FILE_PATH = "data/contacts.csv";

    private final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");

    public List<Contact> getContacts() { return contacts; }

    public Contact findByPhone(String phone) {
        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) return c;
        }
        return null;
    }

    public boolean isValidPhone(String s) {
        return PHONE_PATTERN.matcher(s).matches();
    }

    public boolean isValidEmail(String s) {
        return EMAIL_PATTERN.matcher(s).matches();
    }

    public boolean add(Contact c) {
        if (findByPhone(c.getPhone()) != null) return false;
        contacts.add(c);
        return true;
    }

    public boolean delete(String phone) {
        Contact c = findByPhone(phone);
        if (c == null) return false;
        contacts.remove(c);
        return true;
    }

    public List<Contact> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getPhone().contains(keyword) || c.getName().toLowerCase().contains(keyword)) {
                result.add(c);
            }
        }
        return result;
    }

    public boolean loadFromCSV() {
        try {
            contacts.clear();
            File file = new File(FILE_PATH);
            if (!file.exists()) return true;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] a = line.split(",", -1);
                if (a.length < 7) continue;
                contacts.add(new Contact(a[0], a[1], a[2], a[3], a[4], a[5], a[6]));
            }
            br.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveToCSV() {
        try {
            File file = new File("data");
            if (!file.exists()) file.mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Contact c : contacts) {
                bw.write(c.toString());
                bw.newLine();
            }
            bw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
