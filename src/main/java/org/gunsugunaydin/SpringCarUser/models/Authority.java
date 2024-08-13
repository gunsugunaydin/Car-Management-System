package org.gunsugunaydin.SpringCarUser.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="authorities")
@Getter
@Setter
@NoArgsConstructor
public class Authority {
    
@Id    
@Column(name="authority_id")
private Long id;

@Column(name="authority_name")
private String name;
}