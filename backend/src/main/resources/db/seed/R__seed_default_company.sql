INSERT INTO companies (code, name, status, created_at, created_by, updated_at, updated_by)
SELECT
    'CONN',
    'CONN 데모 회사',
    'ACTIVE',
    CURRENT_TIMESTAMP(6),
    NULL,
    CURRENT_TIMESTAMP(6),
    NULL
WHERE NOT EXISTS (
  SELECT 1
  FROM companies
  WHERE code = 'CONN'
);