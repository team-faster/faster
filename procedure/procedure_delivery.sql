CREATE OR REPLACE PROCEDURE call_api_for_accepted_orders()
LANGUAGE plpgsql
AS $$
    order_record RECORD;
    api_url TEXT;
    api_response TEXT;
BEGIN
    -- ACCEPTED 상태의 주문을 조회
    FOR order_record IN
        SELECT id
        FROM p_order
        WHERE status = 'ACCEPTED'
    LOOP
        -- 외부 API 호출
        -- API 호출은 예시이며, 실제로는 HTTP 호출을 위한 외부 도구나 라이브러리 필요
        -- API의 응답을 받아오기 위해서는 적절한 HTTP 클라이언트를 PostgreSQL에서 사용해야 하며, 이는 외부 도구를 통해 이루어짐
        
        -- API 호출 후 응답 처리 (예시: 응답을 로그로 저장)
        -- 실제로는 API의 응답을 데이터베이스에 기록하거나 후속 작업을 수행
        -- log table에 응답 기록하기
        api_url := 'http://localhost:8080/api/orders/' || order_record.id || '/confirm';

        -- PATCH 요청 보내기
        api_response := http_patch(
            api_url,  -- API URL
            NULL,        -- 요청 본문 (필요 시 JSON 형태로 추가 가능)
            'application/json'  -- Content-Type 지정
        );

    END LOOP;
END;
$$;;


-- CREATE EXTENSION IF NOT EXISTS http; 설치 필요