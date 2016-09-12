/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.script.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Щифровальщик паролей.
 * 
 * @author nova
 */
@Component("appPwdEncoder")
public class SecretPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder cryptPasswordEncoder;
    private final String seed;

    @Autowired
    public SecretPasswordEncoder(
            @Value(value = "${security.seed}") String seed,
            @Value(value = "${security.power}") int power
    ) {
        this.seed = seed;
        cryptPasswordEncoder = new BCryptPasswordEncoder(power);
    }

    
    @Override
    public String encode(CharSequence rawPassword) {
        return cryptPasswordEncoder.encode(seed + rawPassword + seed);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return cryptPasswordEncoder.matches(seed + rawPassword + seed, encodedPassword);
    }
}
