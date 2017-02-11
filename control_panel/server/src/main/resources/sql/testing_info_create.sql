CREATE TABLE testing_info (
    id SERIAL PRIMARY KEY NOT NULL,
    generation VARCHAR(50) NOT NULL,
    number_of_test INT NOT NULL,
    squared_error REAL NOT NULL,
    percentage_of_corrects REAL NOT NULL,
    diff_between_ans_and_best REAL NOT NULL,
    normalized_general_error REAL NOT NULL,
    network_id INT REFERENCES network_info(id)
    );