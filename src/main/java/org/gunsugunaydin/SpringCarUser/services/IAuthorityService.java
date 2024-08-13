package org.gunsugunaydin.SpringCarUser.services;

import java.util.Optional;

import org.gunsugunaydin.SpringCarUser.models.Authority;


public interface IAuthorityService {
    
    public Authority save(Authority authority);
    public Optional<Authority> findById(Long id);
}
