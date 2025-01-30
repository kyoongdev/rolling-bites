START TRANSACTION;

SET FOREIGN_KEY_CHECKS = 0; -- 외래 키 제약 조건 해제

TRUNCATE TABLE food_truck_category;
TRUNCATE TABLE food_truck;
TRUNCATE TABLE food_truck_region;
TRUNCATE TABLE category;
TRUNCATE TABLE small_region;
TRUNCATE TABLE large_region;

SET FOREIGN_KEY_CHECKS = 1; -- 외래 키 제약 조건 복구

COMMIT;