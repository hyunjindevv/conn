INSERT INTO users (company_id,
                   department_id,
                   login_id,
                   password_hash,
                   name,
                   employee_number,
                   email,
                   status,
                   created_at,
                   created_by,
                   updated_at,
                   updated_by)
SELECT c.id,
       d.id,
       seed.login_id,
       '$2a$10$tKJTBx0u25BgjiOdpX5zheGdibaU2SBwNrh0k2/W88QdA112BD3Oa',
       seed.name,
       seed.employee_number,
       seed.email,
       'ACTIVE',
       CURRENT_TIMESTAMP(6),
       NULL,
       CURRENT_TIMESTAMP(6),
       NULL
FROM companies c
         CROSS JOIN (SELECT 'admin'            AS login_id,
                            '관리자'              AS name,
                            'ADM001'           AS employee_number,
                            'admin@conn.local' AS email,
                            'HEAD_OFFICE'      AS department_code
                     UNION ALL
                     SELECT 'employee01'            AS login_id,
                            '일반 사원'                 AS name,
                            'EMP001'                AS employee_number,
                            'employee01@conn.local' AS email,
                            'PRODUCTION'            AS department_code
                     UNION ALL
                     SELECT 'manager01'            AS login_id,
                            '생산부장'                 AS name,
                            'MGR001'               AS employee_number,
                            'manager01@conn.local' AS email,
                            'PRODUCTION'           AS department_code
                     UNION ALL
                     SELECT 'purchaser01'            AS login_id,
                            '구매 담당자'                 AS name,
                            'PUR001'                 AS employee_number,
                            'purchaser01@conn.local' AS email,
                            'PURCHASING'             AS department_code
                     UNION ALL
                     SELECT 'warehouse01'            AS login_id,
                            '창고 담당자'                 AS name,
                            'WHS001'                 AS employee_number,
                            'warehouse01@conn.local' AS email,
                            'WAREHOUSE'              AS department_code) seed
         JOIN departments d
              ON d.company_id = c.id
                  AND d.code = seed.department_code
         LEFT JOIN users u
                   ON u.company_id = c.id
                       AND u.login_id = seed.login_id
WHERE c.code = 'CONN'
  AND u.id IS NULL;
