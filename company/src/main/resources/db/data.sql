-- p_company
insert into faster.p_company
(type, company_manager_id, created_at, created_by, deleted_at, deleted_by,
 updated_at, updated_by, hub_id, id, address, contact, name)
values

    ('SUPPLIER', 6, now(), 1, null, null,
     now(), 1, '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001', '야채도매상 주소지', '010-0000-0000', '야채도매상'),
    ('RECEIVER', 5, now(), 1, null, null,
     now(), 1, '00000000-0000-0000-0000-000000000010',
     '00000000-0000-0000-0000-000000000002', '야채소매상 주소지', '010-0000-0000', '야채소매상');
