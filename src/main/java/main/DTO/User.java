package main.DTO;

public class User {
    private Integer userId;
    private String name;
    private String login;
    private String password;
    private String role;

    public User(Integer userId, String name, String login, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
