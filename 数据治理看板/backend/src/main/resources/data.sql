INSERT INTO data_record (source_type, sample_date, online_value, offline_value, status, note, created_at) VALUES
('sensor_A', '2026-01-05', 42.10, 41.90, 'normal', 'baseline', CURRENT_TIMESTAMP),
('sensor_A', '2026-02-06', 44.70, 43.80, 'warning', 'difference grows', CURRENT_TIMESTAMP),
('sensor_B', '2026-01-12', 31.20, 31.10, 'normal', 'stable', CURRENT_TIMESTAMP),
('sensor_B', '2026-03-02', 36.00, 34.50, 'warning', 'check cable', CURRENT_TIMESTAMP),
('sensor_C', '2026-02-18', 55.20, 52.10, 'critical', 'possible drift', CURRENT_TIMESTAMP);
