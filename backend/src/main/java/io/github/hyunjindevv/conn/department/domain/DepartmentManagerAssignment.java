package io.github.hyunjindevv.conn.department.domain;

import io.github.hyunjindevv.conn.common.persistence.BaseEntity;
import io.github.hyunjindevv.conn.company.domain.Company;
import io.github.hyunjindevv.conn.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "department_manager_assignments")
public class DepartmentManagerAssignment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_user_id", nullable = false)
    private User manager;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    protected DepartmentManagerAssignment() {
    }
}
