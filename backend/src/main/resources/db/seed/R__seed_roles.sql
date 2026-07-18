INSERT INTO roles (company_id, code, name, description, role_type, status, sort_order, created_at, created_by,
                   updated_at, updated_by)
SELECT c.id,
       seed.code,
       seed.name,
       seed.description,
       'BUILT_IN',
       'ACTIVE',
       seed.sort_order,
       CURRENT_TIMESTAMP(6),
       NULL,
       CURRENT_TIMESTAMP(6),
       NULL
FROM companies c
         CROSS JOIN (SELECT 'SYSTEM_ADMIN'     AS code,
                            '시스템 관리자'          AS name,
                            '회사 설정 및 전체 기능 관리' AS description,
                            10                 AS sort_order
                     UNION ALL
                     SELECT 'EMPLOYEE' AS code, '일반 사원' AS name, '구매요청 작성 및 본인 문서 조회' AS description, 20 AS sort_order
                     UNION ALL
                     SELECT 'DEPARTMENT_MANAGER' AS code,
                            '부서장'                AS name,
                            '소속 부서 구매요청 결재'      AS description,
                            30                   AS sort_order
                     UNION ALL
                     SELECT 'PURCHASER' AS code, '구매 담당자' AS name, '승인된 구매요청 발주 처리' AS description, 40 AS sort_order
                     UNION ALL
                     SELECT 'WAREHOUSE_MANAGER' AS code,
                            '창고 담당자'            AS name,
                            '입고 처리 및 재고 확인'     AS description,
                            50                  AS sort_order) seed
         LEFT JOIN roles r
                   ON r.company_id = c.id
                       AND r.code = seed.code
WHERE c.code = 'CONN'
  AND r.id IS NULL;
