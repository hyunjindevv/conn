-- 본사 시드
INSERT INTO departments (company_id, parent_department_id,code,name,status,sort_order,created_at, created_by, updated_at, updated_by)
SELECT
    c.id,
    NULL,
    'HEAD_OFFICE',
    '본사',
    'ACTIVE',
    10,
    CURRENT_TIMESTAMP(6),
    NULL,
    CURRENT_TIMESTAMP(6),
    NULL
FROM companies c
WHERE c.code = 'CONN'
  AND NOT EXISTS (
    SELECT 1
    FROM departments d
    WHERE d.company_id = c.id
      AND d.code = 'HEAD_OFFICE'
);
-- 생산부 시드
INSERT INTO departments (company_id, parent_department_id,code,name,status,sort_order,created_at, created_by, updated_at, updated_by)
SELECT
    c.id,
    p.id,
    'PRODUCTION',
    '생산부',
    'ACTIVE',
    10,
    CURRENT_TIMESTAMP(6),
    NULL,
    CURRENT_TIMESTAMP(6),
    NULL
FROM companies c
JOIN departments p ON p.company_id = c.id AND p.code = 'HEAD_OFFICE'
WHERE c.code = 'CONN'
  AND NOT EXISTS (
    SELECT 1
    FROM departments d
    WHERE d.company_id = c.id
      AND d.code = 'PRODUCTION'
);
-- 구매팀 시드
INSERT INTO departments (company_id, parent_department_id,code,name,status,sort_order,created_at, created_by, updated_at, updated_by)
SELECT
    c.id,
    p.id,
    'PURCHASING',
    '구매팀',
    'ACTIVE',
    20,
    CURRENT_TIMESTAMP(6),
    NULL,
    CURRENT_TIMESTAMP(6),
    NULL
FROM companies c
         JOIN departments p ON p.company_id = c.id AND p.code = 'HEAD_OFFICE'
WHERE c.code = 'CONN'
  AND NOT EXISTS (
    SELECT 1
    FROM departments d
    WHERE d.company_id = c.id
      AND d.code = 'PURCHASING'
);
-- 창고팀 시드
INSERT INTO departments (company_id, parent_department_id,code,name,status,sort_order,created_at, created_by, updated_at, updated_by)
SELECT
    c.id,
    p.id,
    'WAREHOUSE',
    '창고팀',
    'ACTIVE',
    30,
    CURRENT_TIMESTAMP(6),
    NULL,
    CURRENT_TIMESTAMP(6),
    NULL
FROM companies c
         JOIN departments p ON p.company_id = c.id AND p.code = 'HEAD_OFFICE'
WHERE c.code = 'CONN'
  AND NOT EXISTS (
    SELECT 1
    FROM departments d
    WHERE d.company_id = c.id
      AND d.code = 'WAREHOUSE'
);