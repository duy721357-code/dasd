package model.entity;

public abstract class Staff {
    protected String staffId;
    protected String fullName;
    protected String role;
    protected String pass;

    public Staff(String staffId, String fullName, String role,String pass) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.role = role;
        this.pass = pass;
    }

    public String getStaffId() { return staffId; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }
    public String getPass() { return pass; }
    
}