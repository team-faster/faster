-- p_order
INSERT INTO p_order
    (total_price, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
     delivery_id, id, receiving_company_id, supplier_company_id, "name",
     supplier_company_name, request, status)
VALUES
    (10000, now(), 1, null, null, now(), 1,
     null, '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000002',
     '대파 10단 외 3건', '공급업체명',
     '17일 오전 10시까지 배송 부탁드립니다.','ACCEPTED');

-- p_order_item
INSERT INTO faster.p_order_item
    (price, quantity, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
 id, order_id, product_id, "name")
VALUES
    (1000, 10, now(), 1, null, null,
       now(), 1, '00000000-0000-0000-0000-000000000001',
       '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001',
       '대파 10단'),
    (1000, 10, now(), 1, null, null,
     now(), 1, '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000002',
     '상추 10묶음'),
    (1000, 10, now(), 1, null, null,
     now(), 1, '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000003',
     '배추 10포기'),
    (1000, 10, now(), 1, null, null,
     now(), 1, '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000004',
     '미나리 10단');

-- p_orderer_info
INSERT INTO faster.p_orderer_info
    (created_at, created_by, deleted_at, deleted_by, updated_at, updated_by, id, order_id,
 receiving_company_contact, receiving_company_name, receiving_company_address)
VALUES
    (now(), 1, null, null, now(), 1,
       '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001',
       '010-0000-0000', '수령업체명',
       '서울시 영등포구 영등포길 14');

