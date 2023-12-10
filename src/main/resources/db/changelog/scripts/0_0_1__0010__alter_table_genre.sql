ALTER TABLE genre ADD COLUMN type varchar(100) NULL DEFAULT NULL;
ALTER TABLE genre ADD COLUMN category varchar(100) NULL DEFAULT NULL;

UPDATE genre SET type = 'Genre' WHERE 1 = 1;

INSERT INTO genre(code, title, type, category, created_date, last_modified_date)
    SELECT code, title, 'SubGenre' as type, category, created_date, last_modified_date FROM tag WHERE code IS NOT NULL;

INSERT INTO _story_genre(story_id, genre_id)
    SELECT st.story_id, g.id FROM _story_tag st
        INNER JOIN tag t ON t.id = st.tag_id
        INNER JOIN genre g on g.code = t.code;
