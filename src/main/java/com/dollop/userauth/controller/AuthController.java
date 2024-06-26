package com.dollop.userauth.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.userauth.entity.AuthResponse;
import com.dollop.userauth.entity.User;
import com.dollop.userauth.entity.UserRegistration;
import com.dollop.userauth.entity.payload.SocialLoginRequest;
import com.dollop.userauth.entity.payload.UserCreateRequest;
import com.dollop.userauth.entityrequest.UserRequest;
import com.dollop.userauth.service.IAuthenticationService;
import com.dollop.userauth.service.IUserServices;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private IAuthenticationService authenticationService;

	@Autowired
	private IUserServices userServices;

	@PostMapping("signUp")
	public ResponseEntity<?> createUser(@Valid UserRegistration userRegistration) {
//		System.err.println(userImage.getOriginalFilename());
		
		Map<String, UserRegistration> res = new HashMap<>();
		res.put("data", this.authenticationService.regirterUser(userRegistration));
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}

	@PutMapping("verifyOtp")
	public ResponseEntity<?> verifiyRegisteredUser(String email, String otp) {
		UserRegistration ur = this.authenticationService.verifyRegirterUser(email, otp);
		UserCreateRequest u = new UserCreateRequest();
		u.setPassword(ur.getPassword());
		u.setFirstName(ur.getFirstName());
		u.setLastName(ur.getLastName());
		u.setEmail(ur.getEmail());
		u.setRoleName(ur.getRoleName());
		u.setProfilePic(ur.getProfilePic());
		this.userServices.createUser(u);
		Map<String, String> res = new HashMap<>();
		res.put("data", ur != null ? "you Have Registered Succesfully" : "There Is An Error In Registration");
		return ResponseEntity.status(HttpStatus.OK).body(res);
//		return new ResponseEntity<String>(
//				ur != null ? "you Have Registered Succesfully" : "There Is An Error In Registration", HttpStatus.OK);
	}

	@PostMapping("signIn")
	public ResponseEntity<?> login(UserRequest authRequest) {

		Map<String, AuthResponse> res = new HashMap<>();
		res.put("data", this.authenticationService.login(authRequest, true));
		return ResponseEntity.status(HttpStatus.OK).body(res);

//		return new ResponseEntity<AuthResponse>(this.authenticationService.login(authRequest, true), HttpStatus.OK);
	}

	@PostMapping("signInGoogle")
	public ResponseEntity<?> loginwithGoogle(SocialLoginRequest authRequest) {

//		authRequest.setUserToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjMyM2IyMTRhZTY5NzVhMGYwMzRlYTc3MzU0ZGMwYzI1ZDAzNjQyZGMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4OTY0ODIwODc1MzAtbjYyYWRjcTN1cG5pOWtza2JsYzRqc200a25xYmRkdmguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4OTY0ODIwODc1MzAtbjYyYWRjcTN1cG5pOWtza2JsYzRqc200a25xYmRkdmguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTQ1OTExMzY4NDY1NDUzMDczODQiLCJlbWFpbCI6Im5pa2hpbHNocmF2YW5la2FyODBAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJXLTBEU0tMNnhmcUZ4SEZ1X1VyNnBRIiwibm9uY2UiOiJnUGJ6TV9QUDJEd0V3eXpoSG5jelBmVV9KWG5jQXBEbllUazNyU2NTSEJFIiwibmFtZSI6Ik5pa2hpbCBTaHJhdmFuZWthciIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NJc1FVVU1FN1dmUEMwcFFQSEpoVXBZaDRHR1FCeVk3VGZyTWdLYVRxM3dBVXJCeWRpdT1zOTYtYyIsImdpdmVuX25hbWUiOiJOaWtoaWwiLCJmYW1pbHlfbmFtZSI6IlNocmF2YW5la2FyIiwiaWF0IjoxNzE2MjY5MjE2LCJleHAiOjE3MTYyNzI4MTZ9.qpuhAOZGlJMcqslfG9ZqZXYRSXu4N1miDLtrPn_hbl9IM-SH772Zo4Y_Mx3olZL5eOoz_Ozq1NPHYlv32sVAXQopofOEL4Nms1yYoDEXAcvbQBE50RnOtYL_fwO46li-WRz0b2zczdg304BOXDy-72jlvnUcWg2vOTN4B5ftvnOGFNCqJW9BNCmA311JlV4Qv0e-iQT2kSBqIX-3QzjHep2w0scdRclvQJPelv3Xo4rs4zsBYj38m582cky-eVXmmaimMyyUO-bTG9_USX9uwNdFiZjTmnUF21AdVjc6DDjqOY6kdzUkFtKxU6vIjiQld8OgT2dkou11HljW-YFVhw");;
//		authRequest.setClientId("896482087530-n62adcq3upni9kskblc4jsm4knqbddvh.apps.googleusercontent.com");
		Map<String, AuthResponse> res = new HashMap<>();
		res.put("data", this.authenticationService.checkSocialLoginUser(authRequest));
		return ResponseEntity.status(HttpStatus.OK).body(res);

//		return new ResponseEntity<AuthResponse>(this.authenticationService.login(authRequest, true), HttpStatus.OK);
	}

	@PostMapping("withOtp")
	public ResponseEntity<?> login(UserRequest authRequest, String otp) {

		Map<String, AuthResponse> res = new HashMap<>();
		res.put("data", this.authenticationService.loginWithOtp(otp, authRequest));
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@GetMapping("current-user")
	public ResponseEntity<?> getCurrentUser(Principal p) {
		Map<String, User> res = new HashMap<>();
		res.put("data", this.authenticationService.getCurrentUser(p.getName()));
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@PutMapping("forget")
	public ResponseEntity<?> changeForgetPassword(UserRequest authRequest, String otp) {
		Map<String, String> res = new HashMap<>();
		res.put("data", this.authenticationService.changeForgetPassword(authRequest, otp) ? "Password Changed "
				: "Somethign Went Wrong ");
		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

	@GetMapping("sendOtp")
	public ResponseEntity<?> sendOtpForForgetPassword(
			@Valid @NotBlank(message = "email must not be blank") String regirterEmail) {
		Map<String, String> res = new HashMap<>();
		res.put("data",
				this.authenticationService.requestToGetOtpForForgetPassword(regirterEmail)
						? "otp send Check Your Mail Box"
						: "Something Wen Wrong");
		return ResponseEntity.status(HttpStatus.OK).body(res);

	}

}
