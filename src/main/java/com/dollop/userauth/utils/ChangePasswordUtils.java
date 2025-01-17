package com.dollop.userauth.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordUtils {

	private String oldPassword;
	private String newPassword;
}
