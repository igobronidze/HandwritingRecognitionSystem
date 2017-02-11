CREATE TABLE normalized_data (
    id SERIAL PRIMARY KEY,
    width INT NOT NULL,
    height INT NOT NULL,
    letter VARCHAR(1),
    first_symbol VARCHAR(1),
    last_symbol VARCHAR(1),
    generation VARCHAR(100),
    data REAL[]
    );