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