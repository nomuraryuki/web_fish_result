package com.example.springfishproject.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddUserForm {
    @NotEmpty(message = "{User.Id.NotEmpty}")
    private String addUserLoginId;

    @NotEmpty(message = "{User.Password.NotEmpty}")
    private String addUserPassword;

    @NotEmpty(message = "{User.Name.NotEmpty}")
    private  String addUserName;
}
