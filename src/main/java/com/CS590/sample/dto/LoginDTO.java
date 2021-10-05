package com.CS590.sample.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    @NotNull
    private String userName;
    @NotNull
    private String password;
    private String email;
}
