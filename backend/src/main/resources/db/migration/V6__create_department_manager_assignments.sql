CREATE TABLE department_manager_assignments
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    company_id      BIGINT NOT NULL,
    department_id   BIGINT NOT NULL,
    manager_user_id BIGINT NOT NULL,
    effective_from  DATE   NOT NULL,
    effective_to    DATE,
    created_at      DATETIME(6) NOT NULL,
    created_by      BIGINT,
    updated_at      DATETIME(6) NOT NULL,
    updated_by      BIGINT,
    CONSTRAINT pk_department_manager_assignments PRIMARY KEY (id),
    CONSTRAINT uk_department_manager_assignments_start UNIQUE (company_id, department_id, effective_from),
    CONSTRAINT chk_department_manager_assignments_period CHECK (
        effective_to IS NULL
            OR effective_to >= effective_from
        ),
    CONSTRAINT fk_department_manager_assignments_department FOREIGN KEY (company_id, department_id) REFERENCES departments (company_id, id) ON DELETE RESTRICT,
    CONSTRAINT fk_department_manager_assignments_manager FOREIGN KEY (company_id, manager_user_id) REFERENCES users (company_id, id) ON DELETE RESTRICT
);
