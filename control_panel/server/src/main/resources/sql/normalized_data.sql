CREATE TABLE normalized_data (
    id SERIAL PRIMARY KEY,
    width INT NOT NULL,
    height INT NOT NULL,
    letter VARCHAR(1),
    generation VARCHAR(100),
    data REAL[]
    );

CREATE OR REPLACE FUNCTION increaseCount()
  RETURNS trigger AS '
BEGIN
  UPDATE grouped_normalized_data SET count = count + 1 WHERE id = NEW.grouped_normalized_data_id;
  RETURN NEW;
END' LANGUAGE 'plpgsql';
DROP TRIGGER IF EXISTS increaseOnInsertTrigger on normalized_data;
CREATE TRIGGER increaseOnInsertTrigger AFTER INSERT ON normalized_data FOR EACH ROW EXECUTE PROCEDURE increaseCount();

CREATE OR REPLACE FUNCTION changeCount()
  RETURNS trigger AS '
BEGIN
  UPDATE grouped_normalized_data SET count = count + 1 WHERE id = NEW.grouped_normalized_data_id;
  UPDATE grouped_normalized_data SET count = count - 1 WHERE id = OLD.grouped_normalized_data_id;
  RETURN NEW;
END' LANGUAGE 'plpgsql';
DROP TRIGGER IF EXISTS changeCountOnInsertTrigger on normalized_data;
CREATE TRIGGER changeCountOnInsertTrigger AFTER UPDATE ON normalized_data FOR EACH ROW EXECUTE PROCEDURE changeCount();

CREATE OR REPLACE FUNCTION decreaseCount()
  RETURNS trigger AS '
BEGIN
  UPDATE grouped_normalized_data SET count = count - 1 WHERE id = OLD.grouped_normalized_data_id;
  RETURN NEW;
END' LANGUAGE 'plpgsql';
DROP TRIGGER IF EXISTS decreaseOnInsertTrigger on normalized_data;
CREATE TRIGGER decreaseOnInsertTrigger AFTER DELETE ON normalized_data FOR EACH ROW EXECUTE PROCEDURE decreaseCount();

CREATE INDEX grouped_normalized_data_id_index ON normalized_data (grouped_normalized_data_id);