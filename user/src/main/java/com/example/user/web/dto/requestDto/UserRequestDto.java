package com.example.user.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * Request Data Transfer Object for User.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "User Name cannot be Empty.")
    private String userName;

    @NotBlank(message = "First Name cannot be Empty.")
    private String firstName;

    @NotBlank(message = "Last Name cannot be Empty.")
    private String lastName;
}
