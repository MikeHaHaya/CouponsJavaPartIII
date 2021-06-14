package app.core.security;

public class UserDetails {

    private String id;
    private String email;
    private String password;
    private UserType userType;
    private String token;

    public UserDetails(String id, String email, String password, UserType userType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}