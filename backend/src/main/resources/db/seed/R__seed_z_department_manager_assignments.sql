INSERT INTO department_manager_assignments (company_id,
                                            department_id,
                                            manager_user_id,
                                            effective_from,
                                            effective_to,
                                            created_at,
                                            created_by,
                                            updated_at,
                                            updated_by)
SELECT c.id,
       d.id,
       u.id,
       '2026-01-01',
       NULL,
       CURRENT_TIMESTAMP(6),
       NULL,
       CURRENT_TIMESTAMP(6),
       NULL
FROM companies c
         JOIN departments d ON d.company_id = c.id AND d.code = 'PRODUCTION'
         JOIN users u ON u.company_id = c.id AND u.department_id = d.id AND u.login_id = 'manager01'
         LEFT JOIN department_manager_assignments dma
                   ON dma.company_id = c.id AND dma.department_id = d.id AND dma.effective_from = '2026-01-01'
WHERE c.code = 'CONN'
  AND dma.id IS NULL;
