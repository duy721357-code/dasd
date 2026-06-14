package model.entity;

import model.observer.IObserver;

public class Customer implements IObserver {
    private String email;
    private String password;
    private String fullName;
    private String phone;

    public Customer(String email, String password, String fullName, String phone) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
    }

    @Override
    public void update(String message) {
        System.out.printf("[🔔 THÔNG BÁO gửi đến %s]: %s%n", this.fullName, message);
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
}