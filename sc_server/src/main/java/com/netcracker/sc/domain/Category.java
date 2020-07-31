package com.netcracker.sc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id", nullable = false, unique = true)
    private Long categoryId;

    private String name;

    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Spending> spendings;
}
