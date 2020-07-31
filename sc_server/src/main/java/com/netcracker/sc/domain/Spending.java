package com.netcracker.sc.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="spending")
@Data
//@EqualsAndHashCode(exclude = {"group", "category"})
public class Spending implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "spending_id", nullable = false, unique = true)
    private Long spendingId;

    private BigDecimal amount;

    private Date date;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
