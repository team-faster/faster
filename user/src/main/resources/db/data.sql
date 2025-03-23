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