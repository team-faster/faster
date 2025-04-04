-- delivery
INSERT INTO p_delivery
(id,company_delivery_manager_id,created_at,created_by,deleted_at,deleted_by,updated_at,updated_by,destination_hub_id,order_id,receipt_company_id,source_hub_id,receip_company_address,recipient_name,recipient_slack_id,status
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    '2',
    now(),
    1,
    null,
    null,
    now(),
    1,
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '수령업체주소',
    '수령업체이름',
    '수령업체SlackID',
    'READY'
);
INSERT INTO p_delivery
(id,company_delivery_manager_id,created_at,created_by,deleted_at,deleted_by,updated_at,updated_by,
 destination_hub_id,order_id,receipt_company_id,source_hub_id,receip_company_address,recipient_name,recipient_slack_id,status
) VALUES
(
    '00000000-0000-0000-0000-000000000002',
    '2',
    now(),
    2,
    null,
    null,
    now(),
    2,
    '00000000-0000-0000-0000-000000000002',
    '00000000-0000-0000-0000-000000000002',
    '00000000-0000-0000-0000-000000000002',
    '00000000-0000-0000-0000-000000000003',
    '수령업체주소',
    '수령업체이름',
    '수령업체SlackID',
    'READY'
);

-- deliveryRoute -----------------------------------------------------------------------------------

INSERT INTO p_delivery_route(id,sequence,created_at,created_by,deleted_at,deleted_by,delivery_manager_id,expected_distance_m,expected_time_min,real_distance_m,real_time_min,updated_at,updated_by,destination_hub_id,p_delivery_id,source_hub_id,delivery_manager_name,status,type
) VALUES
(
    '00000000-0000-0000-0000-000000000001',
    1,
    now(),
    2,
    null,
    null,
    1,
    123,
    1234,
    null,
    null,
    now(),
    1,
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '김배송허브매니저',
    'PENDING',
    'TO_HUB'
);

INSERT INTO p_delivery_route(id,sequence,created_at,created_by,deleted_at,deleted_by,delivery_manager_id,expected_distance_m,expected_time_min,real_distance_m,real_time_min,updated_at,updated_by,destination_hub_id,p_delivery_id,source_hub_id,delivery_manager_name,status,type
) VALUES
(
    '00000000-0000-0000-0000-000000000002',
    1,
    now(),
    2,
    null,
    null,
    1,
    123,
    1234,
    null,
    null,
    now(),
    1,
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '00000000-0000-0000-0000-000000000001',
    '김배송허브매니저',
    'PENDING',
    'TO_HUB'
);

-- deliveryManager----------------------------------------------------------------------------------

INSERT INTO p_delivery_manager (
    id,
    delivery_sequence_number,
    created_at,
    created_by,
    deleted_at,
    deleted_by,
    updated_at,
    updated_by,
    hub_id,
    type,
    user_name
) VALUES (
    1,
    1,
    now(),
    1,
    null,
    null,
    now(),
    1,
    '00000000-0000-0000-0000-000000000001',
    'HUB_DELIVERY',
    '김배송허브매니저'
);

INSERT INTO p_delivery_manager (
    id,
    delivery_sequence_number,
    created_at,
    created_by,
    deleted_at,
    deleted_by,
    updated_at,
    updated_by,
    hub_id,
    type,
    user_name
) VALUES (
     2,
     1,
     now(),
     2,
     null,
     null,
     now(),
     2,
     null,
     'COMPANY_DELIVERY',
     '김배송업체매니저'
 );

INSERT INTO p_user
(id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
 name, password, role, slack_id, username)
VALUES
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
