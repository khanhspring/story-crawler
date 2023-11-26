INSERT INTO source (id, code, "name", url, status, created_date, last_modified_date, "version")
VALUES (1, 'Metruyencv', 'metruyencv.com', 'https://metruyencv.com', 'Disabled', CURRENT_TIMESTAMP, NULL, 0);

INSERT INTO source_url (id, source_id, url, created_date, last_modified_date, "version")
VALUES (1, 1, 'https://metruyencv.com/truyen/?sort_by=created_at&status=-1&props=-1&limit=20&page={pageNumber}', CURRENT_TIMESTAMP, NULL, 0);
