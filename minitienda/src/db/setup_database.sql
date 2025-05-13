-- Step 1: Create the user if it does not exist
DO
$do$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_roles WHERE rolname = 'minitienda'
    ) THEN
        CREATE ROLE minitienda LOGIN CREATEDB PASSWORD 'minitienda';
    END IF;
END
$do$;

-- Step 2: Create the database if it does not exist
-- Note: This must be outside a DO block or transaction, hence the workaround
SELECT 'CREATE DATABASE minitienda OWNER minitienda'
WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'minitienda'
)\gexec

-- Step 3: Connect to the database
\connect minitienda minitienda

-- Step 4: Create tables
DO
$do$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.tables WHERE table_name = 'users'
    ) THEN
        CREATE TABLE Users (
            username VARCHAR(30) PRIMARY KEY,
            password CHAR(64),
            card VARCHAR(20)
        );
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.tables WHERE table_name = 'purchases'
    ) THEN
        CREATE TABLE Purchases (
            buyer VARCHAR REFERENCES Users(username) ON DELETE CASCADE,
            date  TIMESTAMP,
            cost  REAL,
            PRIMARY KEY (buyer, date)
        );
    END IF;
END
$do$;
