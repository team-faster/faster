-- 배송매니저 sequence 할당 동시성 문제 해결을 위해
-- PostgresSQL 의 Sequence 기능 사용
-- CACHE 1 에 의해 캐싱기능 미사용
--      성능이 중요하고 약간의 갭이 허용된다면 → CACHE 10 이상을 고려해보세요.
--      운영 환경에서 시퀀스 누락이 치명적인 경우 → CACHE 1 또는 CACHE 0로 사용.
-- 일반적으로 sql 파일은 운영에서는 사용 안한다.
CREATE SEQUENCE IF NOT EXISTS delivery_manager_sequence_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;