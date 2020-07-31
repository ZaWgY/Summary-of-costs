package com.netcracker.sc.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="group_invitation")
@Data
public class GroupInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "group_invitation_id", nullable = false, unique = true)
    private Long groupInvitationId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
