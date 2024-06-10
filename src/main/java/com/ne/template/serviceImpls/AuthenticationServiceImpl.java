package com.ne.template.serviceImpls;

import com.ne.template.dtos.requests.ResetPasswordDto;
import com.ne.template.dtos.requests.SignInDto;
import com.ne.template.dtos.responses.LoginResponseDto;
import com.ne.template.dtos.responses.ProfileResponseDto;
import com.ne.template.exceptions.ResourceNotFoundException;
import com.ne.template.models.User;
import com.ne.template.services.IAuthenticationService;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationServiceImpl implements IAuthenticationService {

    @Override
    public LoginResponseDto login(SignInDto dto) {
        return null;
    }

    @Override
    public boolean verifyAccount(String email, String code) throws ResourceNotFoundException {
        return false;
    }

    @Override
    public boolean verifyResetCode(String email, String code) throws ResourceNotFoundException {
        return false;
    }

    @Override
    public User resendVerificationCode(String email) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public User resetPassword(ResetPasswordDto dto) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public User initiatePasswordReset(String email) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public ProfileResponseDto getUserProfile() throws ResourceNotFoundException {
        return null;
    }
}
