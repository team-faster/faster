INSERT INTO p_product
    (price, quantity, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
     company_id, hub_id, id, company_name, description, "name")
VALUES
    (10000, 100, now(), 1, null, null,
     now(), 1, '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001',
     '대파도매상', '맛있는 대파', '대파 10단');
