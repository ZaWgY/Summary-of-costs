package com.netcracker.sc.domain;

import com.netcracker.sc.enums.GroupRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="usr_to_group")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserToGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "utg_id", nullable = false, unique = true)
    private Long utgId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_role")
    private GroupRole groupRole;
}
