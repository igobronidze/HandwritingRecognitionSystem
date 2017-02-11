CREATE TABLE network_info (
    id SERIAL PRIMARY KEY NOT NULL,
    width INT NOT NULL,
    height INT NOT NULL,
    generation VARCHAR(50) NOT NULL,
    number_of_data INT NOT NULL,
    training_duration BIGINT NOT NULL,
    weight_min_value REAL NOT NULL,
    weight_max_value REAL NOT NULL,
    bias_min_value REAL NOT NULL,
    bias_max_value REAL NOT NULL,
    transfer_function_type VARCHAR(50) NOT NULL,
    learning_rate REAL NOT NULL,
    min_error REAL NOT NULL,
    training_max_iteration BIGINT NOT NULL,
    number_of_training_data_in_one_iteration BIGINT NOT NULL,
    char_sequence VARCHAR(3) NOT NULL,
    hidden_layer VARCHAR(100) NOT NULL
    );