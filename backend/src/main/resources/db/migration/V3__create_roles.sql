CREATE TABLE roles
(
    id          bigint       NOT NULL AUTO_INCREMENT,
    company_id  bigint       NOT NULL,
    code        VARCHAR(30)  NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    role_type   VARCHAR(20)  NOT NULL,
    status      VARCHAR(20)  NOT NULL,
    sort_order  INT          NOT NULL DEFAULT 0,
    created_at  DATETIME(6) NOT NULL,
    created_by  BIGINT,
    updated_at  DATETIME(6) NOT NULL,
    updated_by  BIGINT,
    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uk_roles_company_code UNIQUE (company_id, code),
    CONSTRAINT uk_roles_company_id_id UNIQUE (company_id, id),
    CONSTRAINT chk_roles_code_format CHECK (code REGEXP '^[A-Z0-9_]{2,30}$') ,
    CONSTRAINT chk_roles_role_type CHECK (role_type IN ('BUILT_IN', 'CUSTOM')),
    CONSTRAINT chk_roles_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'DELETED')),
    CONSTRAINT chk_roles_sort_order CHECK (sort_order >= 0),
    CONSTRAINT fk_roles_company FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE RESTRICT
);