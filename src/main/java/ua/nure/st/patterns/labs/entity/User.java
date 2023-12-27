package ua.nure.st.patterns.labs.entity;

public class User {

    private Long id;
    private Long roleId;
    private String login;
    private String password;

    public User() {
    }

    public User(Long id, Long roleId, String login, String password) {
        this.id = id;
        this.roleId = roleId;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
}