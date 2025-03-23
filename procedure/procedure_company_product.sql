CREATE OR REPLACE PROCEDURE faster.generate_company_product_data(IN user_count integer, IN company_count integer, IN product_count integer)
 LANGUAGE plpgsql
AS $procedure$
DECLARE
    user_id INT;
    company_id UUID;
    product_id UUID;
   	hub_id UUID;
    i INT;
    j INT;
    k INT;
BEGIN
    -- 유저 생성
    FOR i IN 10..10+user_count LOOP
        INSERT INTO faster.p_user
            (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
             name, password, role, slack_id, username)
        VALUES
            (i, now(), null, null, null, now(), i,
             '업체담당자' || i, '1234', 'ROLE_COMPANY',
             'company' || i || '@slack.com', 'company' || i);

        user_id := i;

        -- 업체 생성
        company_id := gen_random_uuid();
        hub_id := '00000000-0000-0000-0000-0000000000' || LPAD(FLOOR(RANDOM() * 17 + 1)::TEXT, 2, '0');
        INSERT INTO faster.p_company
            (type, company_manager_id, created_at, created_by, deleted_at, deleted_by,
              updated_at, updated_by, hub_id, id, address, contact, name)
        VALUES
            ('SUPPLIER', user_id, now(), i, null, null,
              now(), i, hub_id,
              company_id, '업체 주소지 ' || i, '010-0000-' || LPAD(i::TEXT, 4, '0'),
              '업체 ' || i);

        -- 상품 생성
        FOR k IN 1..product_count LOOP
            product_id := gen_random_uuid();

            INSERT INTO faster.p_product
                (price, quantity, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                  company_id, hub_id, id, company_name, description, "name")
            VALUES
                (FLOOR(RANDOM() * 99000 + 1000),  -- 1,000원 ~ 100,000원
                FLOOR(RANDOM() * 491 + 10),      -- 10개 ~ 500개
                  now(), i, null, null,
                  now(), i,
                  company_id,
                  hub_id,
                  product_id,
                  '업체 ' || i,
                  '상품 설명 ' || k,
                  '상품명 ' || k);
        END LOOP;

    END LOOP;
END;
$procedure$
;
