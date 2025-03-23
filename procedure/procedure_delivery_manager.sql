CREATE OR REPLACE PROCEDURE generate_delivery_managers()
LANGUAGE plpgsql
AS $$
DECLARE
    hub_idx INTEGER;
    user_idx INTEGER;
    user_id INTEGER;
    delivery_id UUID;
BEGIN
    -- USER 생성
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
$$;
;