package org.gunsugunaydin.SpringCarUser.services;

import java.util.Optional;

import org.gunsugunaydin.SpringCarUser.models.Authority;
import org.gunsugunaydin.SpringCarUser.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthorityServiceImpl implements IAuthorityService {
   
    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository){
        this.authorityRepository = authorityRepository;
    }

    public Authority save(Authority authority){
       return authorityRepository.save(authority);
    }
    public Optional<Authority> findById(Long id){
        return authorityRepository.findById(id);
    }
}
