package com.abhinav.stockexchangeapplication.service;

import com.abhinav.stockexchangeapplication.model.AppUser;
import javassist.NotFoundException;

public interface IUserService {
    AppUser saveUser(AppUser user);
    AppUser updateUser(AppUser user, String email) throws NotFoundException;
    AppUser getUserById(Long id) throws Exception;
    AppUser getUser(String email, String password);
    AppUser getUserByMail(String mail);
}
