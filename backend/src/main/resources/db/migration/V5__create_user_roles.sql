CREATE TABLE user_roles
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    company_id  BIGINT NOT NULL,
    user_id     BIGINT NOT NULL,
    role_id     BIGINT NOT NULL,
    assigned_at DATETIME(6) NOT NULL,
    assigned_by BIGINT,
    CONSTRAINT pk_user_roles PRIMARY KEY (id),
    CONSTRAINT uk_user_roles_company_user_role UNIQUE (company_id, user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (company_id, user_id) REFERENCES users (company_id, id) ON DELETE RESTRICT,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (company_id, role_id) REFERENCES roles (company_id, id) ON DELETE RESTRICT
);
