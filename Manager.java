package model.entity;

import java.util.List;

public class Manager extends Staff {
    

    public Manager(String staffId, String fullName, String role, String pass) {
		super(staffId, fullName, role, pass);
		// TODO Auto-generated constructor stub
	}

	public void displayStaffList(List<Staff> staffList) {
        System.out.println("=== DANH SÁCH NHÂN VIÊN ===");
        staffList.forEach(s -> System.out.println(s.getStaffId() + " - " + s.getFullName() + " (" + s.getRole() + ")")); 
    }
}