package io.ginko.yousign.client.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private String firstName;
    private String lastName;
    private String mail;
    private String phone;
}
