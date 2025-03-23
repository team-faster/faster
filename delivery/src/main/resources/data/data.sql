-- p_delivery
insert into faster.p_delivery
    (id, created_at, created_by, deleted_at, deleted_by, updated_at, updated_by,
                        company_delivery_manager_id, destination_hub_id, order_id,
                        receipt_company_address, receipt_company_id, recipient_name,
                        recipient_slack_id, source_hub_id, status)
values
    ('00000000-0000-0000-0000-000000000001', now(), 1,
     null, null, now(), 1,
     '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001', '서울시 관악구 저기 어딘가',
     '00000000-0000-0000-0000-000000000001', '받는 사람이름',
     'recipient@slack.com', '00000000-0000-0000-0000-000000000001', 'READY');


-- p_delivery_manager
insert into faster.p_delivery_manager
    (id, created_at, created_by, deleted_at, deleted_by, updated_at,
                                updated_by, delivery_sequence_number, hub_id, type, user_id,
                                user_name)
values
    ('00000000-0000-0000-0000-000000000001', now(), 1,
     null, null, now(), 1, 1,
     '00000000-0000-0000-0000-000000000001', 'COMPANY_DELIVERY', 3, 'company_delivery'),
    ('00000000-0000-0000-0000-000000000002', now(), 1,
     null, null, now(), 1, 1,
     '00000000-0000-0000-0000-000000000001', 'HUB_DELIVERY', 4, 'hub_delivery');