package com.lcwd.user.service.UserService.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="micro_user")
public class User {

    @Id
    @Column(name="ID")
    private String userId;

    private  String name;

    private String email;
    @Transient
    private List<Rating> ratings;

}
