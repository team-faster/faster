CREATE OR REPLACE PROCEDURE generate_order_data()
LANGUAGE plpgsql
AS $$
DECLARE
    i INTEGER;
    j INTEGER;
    user_id UUID;
    receiving_company_id UUID;
    supplier_id UUID;
    product_id UUID;
    order_id UUID;
   	hub_id UUID;
    P_price INTEGER;
    orderer_info_id UUID;
BEGIN
    -- 1. 유저 생성 및 주문 생성
    FOR i IN 200..300 LOOP
        -- 유저 생성
        INSERT INTO p_user (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by, name, password, role, slack_id, username)
        VALUES (i, now(), i, NULL, NULL, now(), i, CONCAT('유저', i), '1234', 'ROLE_COMPANY', CONCAT('user', i, '@slack.com'), CONCAT('user', i));

        -- 수령업체 생성
        receiving_company_id := gen_random_uuid();  -- 랜덤 id 생성
        hub_id := '00000000-0000-0000-0000-0000000000' || LPAD(FLOOR(RANDOM() * 17 + 1)::TEXT, 2, '0');
        INSERT INTO p_company (type, company_manager_id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by, hub_id, id, address, contact, name)
        VALUES ('RECEIVER', i, now(), i, NULL, NULL, now(), i, hub_id, receiving_company_id, 
                CONCAT('서울시 영등포구 영등포길 ', i), CONCAT('010-', LPAD(FLOOR(RANDOM() * 1000000000)::TEXT, 8, '0')), 
                CONCAT('수령업체', i));

        -- 유저별로 100개의 주문 생성
        FOR j IN 1..100 LOOP
            -- 랜덤 공급업체 및 상품 선택
            SELECT id INTO supplier_id FROM p_company WHERE type = 'SUPPLIER' ORDER BY RANDOM() LIMIT 1;
            SELECT id, price INTO product_id, P_price FROM p_product WHERE company_id = supplier_id ORDER BY RANDOM() LIMIT 1;

            -- p_order 삽입
            order_id := gen_random_uuid();
            INSERT INTO p_order (total_price, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by, 
                                 delivery_id, id, receiving_company_id, supplier_company_id, "name", 
                                 supplier_company_name, request, status)
            VALUES (P_price * 10, now(), i, NULL, NULL, now(), i, NULL, order_id, 
                    receiving_company_id, supplier_id, '대파 10단 외 3건', '공급업체명', 
                    '배송 요청 사항', 'ACCEPTED');

            -- p_order_item 삽입
            INSERT INTO p_order_item (price, quantity, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by, 
                                      id, order_id, product_id, "name")
            VALUES (P_price, 10, now(), i, NULL, NULL, now(), i, gen_random_uuid(), order_id, product_id, '상품명');

            -- p_orderer_info 삽입
            INSERT INTO p_orderer_info (created_at, created_by, deleted_at, deleted_by, updated_at, updated_by, id, order_id, 
                                        receiving_company_contact, receiving_company_name, receiving_company_address)
            VALUES (now(), i, NULL, NULL, now(), i, gen_random_uuid(), order_id, 
                    CONCAT('010-', LPAD(FLOOR(RANDOM() * 1000000000)::TEXT, 8, '0')), 
                    CONCAT('수령업체', FLOOR(RANDOM() * 100)), 
                    CONCAT('서울시 영등포구 영등포길 ', FLOOR(RANDOM() * 100)));
        END LOOP;
    END LOOP;
END;
$$;;