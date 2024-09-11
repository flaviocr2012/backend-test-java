CREATE TABLE vehicle_parking_record (
    id BIG SERIAL PRIMARY KEY,
    vehicle_id BIGINT NOT NULL REFERENCES vehicle(id),
    entry_time TIMESTAMP NOT NULL,
    exit_time TIMESTAMP,
    status VARCHAR(10) NOT NULL -- 'IN' para veículo dentro, 'OUT' para veículo fora
);
