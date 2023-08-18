package com.example.demo.customer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String newPassword;
    // private String newAddress;
}
