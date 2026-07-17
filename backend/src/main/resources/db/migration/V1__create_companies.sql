CREATE TABLE companies (
  id BIGINT NOT NULL AUTO_INCREMENT,
  code VARCHAR(30) NOT NULL ,
  name VARCHAR(100) NOT NULL,
  status VARCHAR(20)  NOT NULL,
  created_at DATETIME(6) NOT NULL,
  created_by BIGINT ,
  updated_at DATETIME(6) NOT NULL,
  updated_by BIGINT,
  CONSTRAINT pk_companies PRIMARY KEY (id),
  CONSTRAINT uk_companies_code UNIQUE (code),
  CONSTRAINT chk_companies_code_format CHECK (code REGEXP '^[A-Z0-9_]{2,30}$'),
  CONSTRAINT chk_companies_status CHECK (status IN ('PENDING','ACTIVE','INACTIVE','DELETED'))
);

