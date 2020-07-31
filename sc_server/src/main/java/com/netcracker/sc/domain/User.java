package com.netcracker.sc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(unique = true)
    private String login;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "img_name")
    private String imgName;
    //@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserToGroup> userToGroupSet;

    //@JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<GroupInvitation> groupInvitations;

    //@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user"/*, cascade = CascadeType.ALL*/)
    private List<Spending> spendings;
}
