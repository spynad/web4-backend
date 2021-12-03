package app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

public class JwtResponse {
    private String accessToken;
    private long userId;
    private String username;

    public JwtResponse(String accessToken, long userId, String username) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
