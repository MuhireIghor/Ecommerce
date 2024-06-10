package com.ne.template.services;

import com.ne.template.dtos.requests.ResetPasswordDto;
import com.ne.template.dtos.requests.SignInDto;
import com.ne.template.dtos.responses.LoginResponseDto;
import com.ne.template.dtos.responses.ProfileResponseDto;
import com.ne.template.exceptions.ResourceNotFoundException;
import com.ne.template.models.User;



public interface IAuthenticationService {
    public LoginResponseDto login (SignInDto dto);
    public boolean verifyAccount(String email, String code) throws ResourceNotFoundException;
    public boolean verifyResetCode(String email, String code) throws ResourceNotFoundException;
    public User resendVerificationCode(String email) throws ResourceNotFoundException;
    public User resetPassword(ResetPasswordDto dto) throws ResourceNotFoundException;
    public User initiatePasswordReset(String email) throws ResourceNotFoundException;
    // other methods
    public ProfileResponseDto getUserProfile() throws ResourceNotFoundException;

}
