INSERT INTO source (id, code, "name", url, status, created_date, last_modified_date, "version")
VALUES (2, 'Truyenfull', 'truyenfull.vn', 'https://truyenfull.vn', 'Disabled', CURRENT_TIMESTAMP, NULL, 0);

INSERT INTO source_url (id, source_id, url, created_date, last_modified_date, "version")
VALUES (2, 2, 'https://truyenfull.vn/danh-sach/truyen-moi/{pageNumber}', CURRENT_TIMESTAMP, NULL, 0);
