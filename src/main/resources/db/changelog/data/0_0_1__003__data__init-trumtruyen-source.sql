INSERT INTO source (id, code, "name", url, status, created_date, last_modified_date, "version")
VALUES (3, 'Trumtruyen', 'trumtruyen.vn', 'https://trumtruyen.vn', 'Disabled', CURRENT_TIMESTAMP, NULL, 0);

INSERT INTO source_url (id, source_id, url, created_date, last_modified_date, "version")
VALUES (3, 3, 'https://trumtruyen.vn/danh-sach/truyen-moi/{pageNumber}', CURRENT_TIMESTAMP, NULL, 0);
