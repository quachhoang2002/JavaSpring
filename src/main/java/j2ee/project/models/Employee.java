package j2ee.project.models;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class Employee {
    private String fullname;
    private Date birthday;
    private boolean gender;
    private String country;
    private boolean married;
    private String[] hobbies;
    private String notes;
    private String hobbiesStr;
    public Employee() {
    }
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Employee(String fullname, Date birthday, boolean gender, String country, boolean married, String[] hobbies, String notes, String hobbiesStr, String imageUrl) {
        this.fullname = fullname;
        this.birthday = birthday;
        this.gender = gender;
        this.country = country;
        this.married = married;
        this.hobbies = hobbies;
        this.notes = notes;
        this.hobbiesStr = hobbiesStr;
        this.imageUrl = imageUrl;
    }

    public Employee(String fullname, Date birthday, boolean gender, String country, boolean married, String[] hobbies, String notes) {
        this.fullname = fullname;
        this.birthday = birthday;
        this.gender = gender;
        this.country = country;
        this.married = married;
        this.hobbies = hobbies;
        this.notes = notes;
    }

    public String getFullname() {
        return fullname;
    }

    public String getHobbiesStr() {
        return hobbiesStr;
    }

    public Employee(String fullname, Date birthday, boolean gender, String country, boolean married, String[] hobbies, String notes, String hobbiesStr) {
        this.fullname = fullname;
        this.birthday = birthday;
        this.gender = gender;
        this.country = country;
        this.married = married;
        this.hobbies = hobbies;
        this.notes = notes;
        this.hobbiesStr = hobbiesStr;
    }

    public void setHobbiesStr(String hobbiesStr) {
        this.hobbiesStr = hobbiesStr;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
        this.hobbiesStr = String.join(", ", hobbies);
    }

    // Trong phương thức getHobbies, bạn có thể thực hiện chuyển đổi hobbiesStr thành mảng chuỗi
    public String[] getHobbies() {
        if (hobbiesStr != null) {
            return hobbiesStr.split(", ");
        } else {
            return null;
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
