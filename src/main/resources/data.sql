-- SQL file for inserting initial data.
-- Using merge statements to make sure the data is only inserted once.

-- Insert initial city data
MERGE INTO city
    USING (VALUES 1) data (id) ON (city.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (1, 'TALLINN');

MERGE INTO city
    USING (VALUES 2) data (id) ON (city.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (2, 'TARTU');

MERGE INTO city
    USING (VALUES 3) data (id) ON (city.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (3, 'PÄRNU');


-- Insert initial vehicle type data
MERGE INTO vehicle_type
    USING (VALUES 1) data (id) ON (vehicle_type.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (1, 'CAR');

MERGE INTO vehicle_type
    USING (VALUES 2) data (id) ON (vehicle_type.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (2, 'SCOOTER');

MERGE INTO vehicle_type
    USING (VALUES 3) data (id) ON (vehicle_type.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (3, 'BIKE');


-- Insert regional fees for Tallinn
MERGE INTO regional_base_fee
    USING (VALUES 1) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (1, 1, 1, 4);

MERGE INTO regional_base_fee
    USING (VALUES 2) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (2, 1, 2, 3.5);

MERGE INTO regional_base_fee
    USING (VALUES 3) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (3, 1, 3, 3);

-- Insert regional fees for Tartu
MERGE INTO regional_base_fee
    USING (VALUES 4) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (4, 2, 1, 3.5);

MERGE INTO regional_base_fee
    USING (VALUES 5) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (5, 2, 2, 3);

MERGE INTO regional_base_fee
    USING (VALUES 6) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (6, 2, 3, 2.5);

-- Insert regional fees for Pärnu
MERGE INTO regional_base_fee
    USING (VALUES 7) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (7, 3, 1, 3);

MERGE INTO regional_base_fee
    USING (VALUES 8) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (8, 3, 2, 2.5);

MERGE INTO regional_base_fee
    USING (VALUES 9) data (id) ON (regional_base_fee.id = data.id)
WHEN NOT MATCHED THEN INSERT VALUES (9, 3, 3, 2);