package org.gunsugunaydin.SpringCarUser.repositories;

import org.gunsugunaydin.SpringCarUser.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    
}
