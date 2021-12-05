package app.service;

import app.payload.request.UserRequest;

public interface UserService {
    void saveUser(UserRequest request);
}
