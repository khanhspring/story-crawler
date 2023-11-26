CREATE TABLE story_view
(
    id                 bigserial NOT NULL,
    story_id           bigint    NOT NULL,

    view_count         int       NOT NULL DEFAULT 0,
    stats_date         date NOT NULL,

    created_date       timestamp NOT NULL,
    last_modified_date timestamp          DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    UNIQUE (story_id, stats_date),
    CONSTRAINT fk_story_view_story_id FOREIGN KEY (story_id) REFERENCES story (id)
);
