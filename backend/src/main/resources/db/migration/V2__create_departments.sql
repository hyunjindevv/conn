CREATE TABLE departments
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    company_id           BIGINT       NOT NULL,
    parent_department_id BIGINT,
    code                 VARCHAR(30)  NOT NULL,
    name                 VARCHAR(100) NOT NULL,
    status               VARCHAR(20)  NOT NULL,
    sort_order           INT          NOT NULL DEFAULT 0,
    created_at           DATETIME(6) NOT NULL,
    created_by           BIGINT,
    updated_at           DATETIME(6) NOT NULL,
    updated_by           BIGINT,
    CONSTRAINT pk_departments PRIMARY KEY (id),
    CONSTRAINT uk_departments_company_code UNIQUE (company_id, code),
    CONSTRAINT uk_departments_company_id_id UNIQUE (company_id, id),
    CONSTRAINT chk_departments_code_format CHECK (code REGEXP '^[A-Z0-9_]{2,30}$') ,
    CONSTRAINT chk_departments_status CHECK (status IN ('ACTIVE','INACTIVE','DELETED')),
    CONSTRAINT chk_departments_sort_order CHECK(sort_order >= 0),
    CONSTRAINT fk_departments_company FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE RESTRICT,
    CONSTRAINT fk_departments_parent FOREIGN KEY (company_id, parent_department_id) REFERENCES departments(company_id, id) ON DELETE RESTRICT
);

