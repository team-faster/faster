CREATE OR REPLACE PROCEDURE faster.generate_company_product_data()
 LANGUAGE plpgsql
AS $procedure$
DECLARE
    company_id UUID;
   	hub_id UUID;
    receiving_company_id UUID;
    supplier_id UUID;
    product_id UUID;
    order_id UUID;
    P_price INTEGER;
    orderer_info_id UUID;
    hub_idx INTEGER;
    user_idx INTEGER;
    user_id INTEGER;
    delivery_id UUID;
    i INT;
    j INT;
    k INT;
BEGIN
    -- user 생성
    insert into faster.p_user
        (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                        name, password, role, slack_id, username)
    values
        (1, now(), null, null, null, now(), 1,
        '마스터', '1234', 'ROLE_MASTER', 'master@slack.com', 'master'),
        (2, now(), null, null, null, now(), 2,
        '허브', '1234', 'ROLE_HUB', 'hub@slack.com', 'hub'),
        (3, now(), null, null, null, now(), 3,
        '업체배송', '1234', 'ROLE_DELIVERY', 'company_delivery@slack.com', 'company_delivery'),
        (4, now(), null, null, null, now(), 4,
        '허브배송', '1234', 'ROLE_DELIVERY', 'hub_delivery@slack.com', 'hub_delivery'),
        (5, now(), null, null, null, now(), 5,
        '업체담당자', '1234', 'ROLE_COMPANY', 'company@slack.com', 'company'),
        (6, now(), null, null, null, now(), 6,
        '업체담당자', '1234', 'ROLE_COMPANY', 'company2@slack.com', 'company2');

    -- hub 생성
    INSERT INTO faster.p_hub (id, name, address, latitude, longitude, hub_manager_id)
    VALUES
        ('00000000-0000-0000-0000-000000000001', '서울특별시 센터', '서울특별시 송파구 송파대로 55', '37.4742027808565', '127.123621185562', 1),
        ('00000000-0000-0000-0000-000000000002', '경기 북부 센터', '경기도 고양시 덕양구 권율대로 570', '37.64028567162047', '126.86959905394286', 1),
        ('00000000-0000-0000-0000-000000000003', '경기 남부 센터', '경기도 이천시 덕평로 257-21', '37.1903545669702', '127.37219605393632', 1),
        ('00000000-0000-0000-0000-000000000004', '부산광역시 센터', '부산 동구 중앙대로 206', '35.117882906777275', '129.04060975393702', 1),
        ('00000000-0000-0000-0000-000000000005', '대구광역시 센터', '대구 북구 태평로 161', '35.87604275518246', '128.5913013539383', 1),
        ('00000000-0000-0000-0000-000000000006', '인천광역시 센터', '인천 남동구 정각로 29', '37.455798556190466', '126.70199025393632', 1),
        ('00000000-0000-0000-0000-000000000007', '광주광역시 센터', '광주 서구 내방로 111', '35.15979355448972', '126.84733490393337', 1),
        ('00000000-0000-0000-0000-000000000008', '대전광역시 센터', '대전 서구 둔산로 100', '36.35017085666188', '127.38065435393654', 1),
        ('00000000-0000-0000-0000-000000000009', '울산광역시 센터', '울산 남구 중앙로 201', '35.5390270962011', '129.311356392207', 1),
        ('00000000-0000-0000-0000-000000000010', '세종특별자치시 센터', '세종특별자치시 한누리대로 2130', '36.47992990960081', '127.28450360393376', 1),
        ('00000000-0000-0000-0000-000000000011', '강원특별자치도 센터', '강원특별자치도 춘천시 중앙로 1', '37.88577256309683', '127.71323750067093', 1),
        ('00000000-0000-0000-0000-000000000012', '충청북도 센터', '충북 청주시 상당구 상당로 82', '36.636226300857096', '127.48298575788046', 1),
        ('00000000-0000-0000-0000-000000000013', '충청남도 센터', '충남 홍성군 홍북읍 충남대로 21', '36.636226300857096', '127.48298575788046', 1),
        ('00000000-0000-0000-0000-000000000014', '전북특별자치도 센터', '전북특별자치도 전주시 완산구 효자로 225', '35.820072504770515', '127.10074410787131', 1),
        ('00000000-0000-0000-0000-000000000015', '전라남도 센터', '전남 무안군 삼향읍 오룡길 1', '34.81656542200064', '126.45551730787197', 1),
        ('00000000-0000-0000-0000-000000000016', '경상북도 센터', '경북 안동시 풍천면 도청대로 455', '36.57539042764432', '128.49708680787035', 1),
        ('00000000-0000-0000-0000-000000000017', '경상남도 센터', '경남 창원시 의창구 중앙대로 300', '35.23694202049536', '128.68268570787762', 1);

    INSERT INTO faster.p_hub_route (id, source_hub_id, destination_hub_id, distance_m, duration_min) VALUES
    -- Pair A-B 경기 남부 센터3:경기 북부 센터2
    ('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000002', 89881, 5047917 / 60000),
    ('00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000002', '00000000-0000-0000-0000-000000000003', 89881, 5047917 / 60000),

    -- Pair A-C 경기 남부 센터3:서울1
    ('00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000001', 52256, 3018717 / 60000),
    ('00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000003', 52256, 3018717 / 60000),

    -- Pair A-D 경기 남부 센터3:인천광역시 센터6
    ('00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000006', 81423, 4292958 / 60000),
    ('00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000006', '00000000-0000-0000-0000-000000000003', 81423, 4292958 / 60000),

    -- Pair A-E 경기 남부 센터3:강원특별자치도 센터11
    ('00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000011', 139953, 6176524 / 60000),
    ('00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000011', '00000000-0000-0000-0000-000000000003', 139953, 6176524 / 60000),

    -- Pair A-F 경기 남부 센터3:경상북도 센터16
    ('00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000016', 184826, 8484918 / 60000),
    ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000016', '00000000-0000-0000-0000-000000000003', 184826, 8484918 / 60000),

    -- Pair A-G 경기 남부 센터3:대전광역시 센터8
    ('00000000-0000-0000-0000-000000000011', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000008', 110342, 5814465 / 60000),
    ('00000000-0000-0000-0000-000000000012', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000003', 110342, 5814465 / 60000),

    -- Pair A-H 경기 남부 센터3:대구광역시 센터5
    ('00000000-0000-0000-0000-000000000013', '00000000-0000-0000-0000-000000000003', '00000000-0000-0000-0000-000000000005', 248146, 10376459 / 60000),
    ('00000000-0000-0000-0000-000000000014', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000003', 248146, 10376459 / 60000),

    -- Pair G-I 대전광역시 센터8:충청남도 센터13
    ('00000000-0000-0000-0000-000000000015', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000013', 38738, 3111418 / 60000),
    ('00000000-0000-0000-0000-000000000016', '00000000-0000-0000-0000-000000000013', '00000000-0000-0000-0000-000000000008', 38738, 3111418 / 60000),

    -- Pair G-J 대전광역시 센터8:충청북도 센터12
    ('00000000-0000-0000-0000-000000000017', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000012', 38738, 3099784 / 60000),
    ('00000000-0000-0000-0000-000000000018', '00000000-0000-0000-0000-000000000012', '00000000-0000-0000-0000-000000000008', 38738, 3099784 / 60000),

    -- Pair G-K 대전광역시 센터8:충청북도 센터10
    ('00000000-0000-0000-0000-000000000019', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000010', 22006, 2057534 / 60000),
    ('00000000-0000-0000-0000-000000000020', '00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000008', 22006, 2057534 / 60000),

    -- Pair G-L 대전광역시 센터8:전북특별자치도 센터14
    ('00000000-0000-0000-0000-000000000021', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000014', 88034, 4339996 / 60000),
    ('00000000-0000-0000-0000-000000000022', '00000000-0000-0000-0000-000000000014', '00000000-0000-0000-0000-000000000008', 88034, 4339996 / 60000),

    -- Pair G-M 대전광역시 센터8:광주광역시 센터7
    ('00000000-0000-0000-0000-000000000023', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000007', 168380, 6918637 / 60000),
    ('00000000-0000-0000-0000-000000000024', '00000000-0000-0000-0000-000000000007', '00000000-0000-0000-0000-000000000008', 168380, 6918637 / 60000),

    -- Pair G-N 대전광역시 센터8:전라남도 센터15
    ('00000000-0000-0000-0000-000000000025', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000015', 225517, 9165710 / 60000),
    ('00000000-0000-0000-0000-000000000026', '00000000-0000-0000-0000-000000000015', '00000000-0000-0000-0000-000000000008', 225517, 9165710 / 60000),

    -- Pair G-H 대전광역시 센터8:대구광역시 센터5
    ('00000000-0000-0000-0000-000000000027', '00000000-0000-0000-0000-000000000008', '00000000-0000-0000-0000-000000000005', 152227, 6662550 / 60000),
    ('00000000-0000-0000-0000-000000000028', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000008', 152227, 6662550 / 60000),

    -- Pair H-F 대구광역시 센터5:경상북도 센터16
    ('00000000-0000-0000-0000-000000000029', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000016', 106843, 4750659 / 60000),
    ('00000000-0000-0000-0000-000000000030', '00000000-0000-0000-0000-000000000016', '00000000-0000-0000-0000-000000000005', 106843, 4750659 / 60000),

    -- Pair H-O 대구광역시 센터5:경상남도 센터17
    ('00000000-0000-0000-0000-000000000031', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000017', 98252, 5150048 / 60000),
    ('00000000-0000-0000-0000-000000000032', '00000000-0000-0000-0000-000000000017', '00000000-0000-0000-0000-000000000005', 98252, 5150048 / 60000),

    -- Pair H-P 대구광역시 센터5:부산광역시 센터4
    ('00000000-0000-0000-0000-000000000033', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000004', 112611, 5293177 / 60000),
    ('00000000-0000-0000-0000-000000000034', '00000000-0000-0000-0000-000000000004', '00000000-0000-0000-0000-000000000005', 112611, 5293177 / 60000),

    -- Pair H-Q 대구광역시 센터5:울산광역시 센터9
    ('00000000-0000-0000-0000-000000000035', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000009', 107382, 5752856 / 60000),
    ('00000000-0000-0000-0000-000000000036', '00000000-0000-0000-0000-000000000009', '00000000-0000-0000-0000-000000000005', 107382, 5752856 / 60000),

    -- Pair F-H 대구광역시 센터5:경상북도 센터16
    ('00000000-0000-0000-0000-000000000037', '00000000-0000-0000-0000-000000000016', '00000000-0000-0000-0000-000000000005', 108347, 4711725 / 60000),
    ('00000000-0000-0000-0000-000000000038', '00000000-0000-0000-0000-000000000005', '00000000-0000-0000-0000-000000000016', 108347, 4711725 / 60000);
    
    -- 공급업체 유저 맟 업체, 상품품 생성
    FOR i IN 10..110 LOOP
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
        FOR k IN 1..100 LOOP
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

    -- 허브
    FOR i IN 1..17 LOOP
        user_id := i+1000;
        -- 유저 생성
        INSERT INTO faster.p_user
            (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
             name, password, role, slack_id, username)
        VALUES
            (user_id, now(), user_id, null, null, now(), user_id,
             '허브담당자' || i, '1234', 'ROLE_HUB',
             'hub' || i || '@slack.com', 'hub' || i);

        hub_id := ('00000000-0000-0000-0000-0000000000' || LPAD(i::TEXT, 2, '0'))::UUID;

        -- 허브 매니저 ID 업데이트
        UPDATE faster.p_hub
        SET hub_manager_id = user_id
        WHERE id = hub_id::uuid;
    END LOOP;

    -- 수령업체 유저 및 수령업체 생성, 주문 생성
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

    -- COMPANY_DELIVERY 유저 및 매니저저 생성
    user_id := 2000;
    FOR hub_idx IN 1..17 LOOP
        FOR user_idx IN 1..10 LOOP
            -- COMPANY_DELIVERY 유저 생성
            INSERT INTO faster.p_user
                (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                 name, password, role, slack_id, username)
            VALUES
                (user_id, now(), null, null, null, now(), user_id,
                 '업체배송_' || hub_idx || '_' || user_idx, 
                 '1234', 'ROLE_DELIVERY', 
                 'company_delivery_' || hub_idx || '_' || user_idx || '@slack.com', 
                 'company_delivery_' || hub_idx || '_' || user_idx);

            -- COMPANY_DELIVERY 매니저 생성
            INSERT INTO faster.p_delivery_manager
                (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                 delivery_sequence_number, hub_id, type, user_name)
            VALUES
                (user_id, now(), 1, null, null, now(), 1,
                 user_idx, 
                 ('00000000-0000-0000-0000-0000000000' || LPAD(hub_idx::TEXT, 2, '0'))::UUID,
                 'COMPANY_DELIVERY',
                 'company_delivery_' || hub_idx || '_' || user_idx);
            
            user_id := user_id + 1;
        END LOOP;
    END LOOP;

    -- HUB_DELIVERY 유저 및 매니저 생성
    FOR i IN 1..10 LOOP
        user_id := i + 3000;
        INSERT INTO faster.p_user
            (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
             name, password, role, slack_id, username)
        VALUES
            (user_id, now(), user_id, null, null, now(), user_id,
             '허브배송_' || i, 
             '1234', 'ROLE_DELIVERY', 
             'hub_delivery_' || i || '@slack.com', 
             'hub_delivery_' || i);

        -- HUB_DELIVERY 매니저 생성
        INSERT INTO faster.p_delivery_manager
            (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
             delivery_sequence_number, hub_id, type, user_name)
        VALUES
            (user_id, now(), user_id, null, null, now(), user_id,
             i, null, 'HUB_DELIVERY',
             'hub_delivery_' || i);
    END LOOP;
END;
$procedure$
;
