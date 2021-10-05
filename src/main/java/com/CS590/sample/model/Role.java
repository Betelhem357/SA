package com.CS590.sample.model;

import com.CS590.sample.Enum.RoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Role {
     @Id
     @GeneratedValue
     private Long id;
     @Enumerated(EnumType.STRING)
     private RoleName roleName;
     private String description;
}
