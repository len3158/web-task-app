package com.interfaces;

import java.util.UUID;

public interface IUser {
    UUID getId();
    void setId(Long id);

    String firstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getEmail();
    void setEmail(String email);

    String getPassword();
    void setPassword(String password);
}
