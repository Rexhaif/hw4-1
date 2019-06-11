package org.hse.petrov.hw4.objects;

public class User {

    private String ipAddress;
    private String browser;
    private Boolean sex;
    private Integer age;

    public User(String ipAddress, String browser, String sex, Integer age) {
        this.ipAddress = ipAddress;
        this.browser = browser;
        this.sex = sex.equals("male");
        this.age = age;
    }

    public User(String ipAddress, String browser, Boolean sex, Integer age) {
        this.ipAddress = ipAddress;
        this.browser = browser;
        this.sex = sex.equals("male");
        this.age = age;
    }

    public static User parseLine(String line) {
        String[] arr = line.split("\\s+");
        return new User(
                arr[0],
                arr[1],
                arr[2],
                Integer.valueOf(arr[3])
        );
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "ipAddress='" + ipAddress + '\'' +
                ", browser='" + browser + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!ipAddress.equals(user.ipAddress)) return false;
        if (!browser.equals(user.browser)) return false;
        if (!sex.equals(user.sex)) return false;
        return age.equals(user.age);

    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + browser.hashCode();
        result = 31 * result + sex.hashCode();
        result = 31 * result + age.hashCode();
        return result;
    }
}
