package com.netcracker.sc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "group_id", nullable = false, unique = true)
    private Long groupId;

    private String name;

    private BigDecimal maxAmount;

    private BigDecimal currentAmount;

    private String description;

    @Column(name = "img_name")
    private String imgName;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    private List<Spending> spendings;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupInvitation> groupInvitations;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserToGroup> userToGroupSet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    private List<Category> categories;
}
