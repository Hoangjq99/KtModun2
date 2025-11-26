package com.codegym.hoang1_modun2.help_method;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static ContactService service = new ContactService();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Đọc từ file");
            System.out.println("7. Lưu vào file");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String c = sc.nextLine();

            switch (c) {
                case "1" -> showList();
                case "2" -> addContact();
                case "3" -> updateContact();
                case "4" -> deleteContact();
                case "5" -> searchContact();
                case "6" -> readFile();
                case "7" -> saveFile();
                case "0" -> System.exit(0);
                default -> System.out.println("Sai lựa chọn!");
            }
        }
    }

    // ---------------- HIỂN THỊ --------------------
    private static void showList() {
        List<Contact> list = service.getContacts();
        if (list.isEmpty()) {
            System.out.println("Danh bạ trống!");
            return;
        }
        int count = 0;
        for (Contact c : list) {
            System.out.println(c.display());
            count++;
            if (count % 5 == 0) {
                System.out.println("Nhấn Enter để xem tiếp...");
                sc.nextLine();
            }
        }
    }

    // ---------------- THÊM MỚI --------------------
    private static void addContact() {
        System.out.println("--- Thêm mới ---");

        String phone, group, name, gender, address, dob, email;

        while (true) {
            System.out.print("SĐT (0xxxxxxxxx): ");
            phone = sc.nextLine();
            if (!service.isValidPhone(phone)) {
                System.out.println("SĐT không hợp lệ!");
                continue;
            }
            break;
        }

        System.out.print("Nhóm: ");
        group = sc.nextLine();

        System.out.print("Họ tên: ");
        name = sc.nextLine();

        System.out.print("Giới tính: ");
        gender = sc.nextLine();

        System.out.print("Địa chỉ: ");
        address = sc.nextLine();

        System.out.print("Ngày sinh: ");
        dob = sc.nextLine();

        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine();
            if (!service.isValidEmail(email)) {
                System.out.println("Email không hợp lệ!");
                continue;
            }
            break;
        }

        Contact c = new Contact(phone, group, name, gender, address, dob, email);
        if (service.add(c)) System.out.println("Thêm thành công!");
        else System.out.println("SĐT đã tồn tại!");
    }

    // ---------------- CẬP NHẬT --------------------
    private static void updateContact() {
        System.out.println("--- Cập nhật ---");
        while (true) {
            System.out.print("Nhập SĐT cần sửa: ");
            String phone = sc.nextLine();

            if (phone.isEmpty()) return;

            Contact c = service.findByPhone(phone);
            if (c == null) {
                System.out.println("Không tìm thấy! Nhập lại hoặc Enter để thoát.");
                continue;
            }

            System.out.print("Nhóm (" + c.getGroup() + "): ");
            String group = sc.nextLine();
            if (!group.isEmpty()) c.setGroup(group);

            System.out.print("Họ tên (" + c.getName() + "): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) c.setName(name);

            System.out.print("Giới tính (" + c.getGender() + "): ");
            String gender = sc.nextLine();
            if (!gender.isEmpty()) c.setGender(gender);

            System.out.print("Địa chỉ (" + c.getAddress() + "): ");
            String address = sc.nextLine();
            if (!address.isEmpty()) c.setAddress(address);

            System.out.print("Ngày sinh (" + c.getDob() + "): ");
            String dob = sc.nextLine();
            if (!dob.isEmpty()) c.setDob(dob);

            while (true) {
                System.out.print("Email (" + c.getEmail() + "): ");
                String email = sc.nextLine();
                if (email.isEmpty()) break;

                if (!service.isValidEmail(email)) {
                    System.out.println("Email không hợp lệ!");
                    continue;
                }
                c.setEmail(email);
                break;
            }

            System.out.println("Cập nhật thành công!");
            return;
        }
    }

    // ---------------- XÓA --------------------
    private static void deleteContact() {
        System.out.print("Nhập SĐT cần xóa: ");
        String phone = sc.nextLine();

        Contact c = service.findByPhone(phone);
        if (c == null) {
            System.out.println("Không tìm thấy!");
            return;
        }

        System.out.print("Bạn có chắc muốn xóa? (Y/N): ");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            service.delete(phone);
            System.out.println("Đã xóa!");
        } else System.out.println("Hủy xóa.");
    }

    // ---------------- TÌM KIẾM --------------------
    private static void searchContact() {
        System.out.print("Nhập từ khóa: ");
        String kw = sc.nextLine();

        List<Contact> list = service.search(kw);
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy!");
            return;
        }

        for (Contact c : list) System.out.println(c.display());
    }

    // ---------------- ĐỌC FILE --------------------
    private static void readFile() {
        System.out.print("Đọc file và ghi đè bộ nhớ? (Y/N): ");
        if (!sc.nextLine().equalsIgnoreCase("Y")) return;

        if (service.loadFromCSV()) System.out.println("Đã tải dữ liệu!");
        else System.out.println("Lỗi đọc file!");
    }

    // ---------------- LƯU FILE --------------------
    private static void saveFile() {
        System.out.print("Ghi bộ nhớ vào file? (Y/N): ");
        if (!sc.nextLine().equalsIgnoreCase("Y")) return;

        if (service.saveToCSV()) System.out.println("Đã lưu!");
        else System.out.println("Lỗi ghi file!");
    }
}
