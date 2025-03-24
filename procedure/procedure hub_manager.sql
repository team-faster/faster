CREATE OR REPLACE PROCEDURE faster.generate_hub_manager_data()
 LANGUAGE plpgsql
AS $$
DECLARE
    hub_id TEXT;
    user_id INTEGER;
BEGIN
    
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
END;
$$;;
