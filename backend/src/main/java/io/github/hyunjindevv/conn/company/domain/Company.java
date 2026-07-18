package io.github.hyunjindevv.conn.company.domain;

import io.github.hyunjindevv.conn.common.persistence.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CompanyStatus status;



    protected Company() {

    }


}
