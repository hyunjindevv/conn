INSERT INTO user_roles (company_id,
                        user_id,
                        role_id,
                        assigned_at,
                        assigned_by)
SELECT c.id,
       u.id,
       r.id,
       CURRENT_TIMESTAMP(6),
       NULL
FROM companies c
         CROSS JOIN (SELECT 'admin' login_id, 'SYSTEM_ADMIN' role_code
                     UNION ALL
                     SELECT 'employee01' login_id, 'EMPLOYEE' role_code
                     UNION ALL
                     SELECT 'manager01' login_id, 'EMPLOYEE' role_code
                     UNION ALL
                     SELECT 'manager01' login_id, 'DEPARTMENT_MANAGER' role_code
                     UNION ALL
                     SELECT 'purchaser01' login_id, 'EMPLOYEE' role_code
                     UNION ALL
                     SELECT 'purchaser01' login_id, 'PURCHASER' role_code
                     UNION ALL
                     SELECT 'warehouse01' login_id, 'EMPLOYEE' role_code
                     UNION ALL
                     SELECT 'warehouse01' login_id, 'WAREHOUSE_MANAGER' role_code) seed
         JOIN users u
              ON u.company_id = c.id
                  AND u.login_id = seed.login_id
         JOIN roles r
              ON r.company_id = c.id
                  AND r.code = seed.role_code
         LEFT JOIN user_roles ur
                   ON ur.company_id = c.id
                       AND ur.user_id = u.id
                       AND ur.role_id = r.id
WHERE c.code = 'CONN'
  AND ur.id IS NULL;
