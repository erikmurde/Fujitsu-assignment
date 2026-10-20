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


-- Insert extra fees related to weather conditions

-- Setup variables for business rules
SET @TEMP_LOW = 'airTemperature < -10';
SET @TEMP_MEDIUM = 'airTemperature >= -10 && airTemperature <= 0';
SET @WIND_MEDIUM = 'windSpeed >= 10 && windSpeed <= 20';
SET @WIND_HIGH = 'windSpeed > 20';
SET @SNOW_SLEET = 'phenomenon.contains("snow") || phenomenon.contains("sleet")';
SET @RAIN = 'phenomenon.contains("rain") || phenomenon.contains("shower") && !phenomenon.contains("snow")';
SET @GLAZE_HAIL = 'phenomenon.equals("Glaze") || phenomenon.equals("Hail")';

-- Message for rules that throw error
SET @VEHICLE_ERROR_MSG = 'Usage of selected vehicle type is forbidden';

-- Insert extra fees based on air temperature
MERGE INTO extra_fee
    USING (VALUES 1) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (1, 2, @TEMP_LOW, 1, '');

MERGE INTO extra_fee
    USING (VALUES 2) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (2, 3, @TEMP_LOW, 1, '');

MERGE INTO extra_fee
    USING (VALUES 3) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (3, 2, @TEMP_MEDIUM, 0.5, '');

MERGE INTO extra_fee
    USING (VALUES 4) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (4, 3, @TEMP_MEDIUM, 0.5, '');

-- Insert extra fees based on wind speed
MERGE INTO extra_fee
    USING (VALUES 5) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (5, 3, @WIND_MEDIUM, 0.5, '');

MERGE INTO extra_fee
    USING (VALUES 6) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (6, 3, @WIND_HIGH, 0, @VEHICLE_ERROR_MSG);

-- Insert extra fees based on weather phenomenon
MERGE INTO extra_fee
    USING (VALUES 7) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (7, 2, @SNOW_SLEET, 1, '');

MERGE INTO extra_fee
    USING (VALUES 8) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (8, 3, @SNOW_SLEET, 1, '');

MERGE INTO extra_fee
    USING (VALUES 9) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (9, 2, @RAIN, 0.5, '');

MERGE INTO extra_fee
    USING (VALUES 10) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (10, 3, @RAIN, 0.5, '');

MERGE INTO extra_fee
    USING (VALUES 11) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (11, 2, @GLAZE_HAIL, 0, @VEHICLE_ERROR_MSG);

MERGE INTO extra_fee
    USING (VALUES 12) data (id) ON (extra_fee.id = data.id)
    WHEN NOT MATCHED THEN INSERT VALUES (12, 3, @GLAZE_HAIL, 0, @VEHICLE_ERROR_MSG);