CREATE TABLE source
(
    id                 bigserial    NOT NULL,
    code               varchar(30)  NOT NULL,
    name               varchar(250) NOT NULL,
    url                varchar(250) NOT NULL,
    status             varchar(30)  NOT NULL,

    created_date       timestamp    NOT NULL,
    last_modified_date timestamp DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE author
(
    id                 bigserial    NOT NULL,
    code               varchar(500) NOT NULL,
    full_name          varchar(500) NOT NULL,

    created_date       timestamp    NOT NULL,
    last_modified_date timestamp DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    UNIQUE (code),
    UNIQUE (full_name)
);

CREATE INDEX author_idx ON author (code);

CREATE TABLE story
(
    id                 bigserial     NOT NULL,
    slug               varchar(1000) NOT NULL,
    title              varchar(1000) NOT NULL,
    summary            text NULL,
    status             varchar(30)   NOT NULL,
    thumbnail_url      varchar(2000) NULL,

    completed          boolean       NOT NULL DEFAULT FALSE,
    total_view         int           NOT NULL DEFAULT 0,
    rating             numeric(2, 1) NULL,
    total_rating       int NULL,

    author_id          bigint NULL,
    source_id          bigint NULL,

    external_url       varchar(2500) NOT NULL,

    position           int           NOT NULL,

    created_date       timestamp     NOT NULL,
    last_modified_date timestamp              DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    UNIQUE (slug),
    CONSTRAINT fk_story_author_id FOREIGN KEY (author_id) REFERENCES author (id),
    CONSTRAINT fk_story_source_id FOREIGN KEY (source_id) REFERENCES source (id)
);

CREATE INDEX story_slug_idx ON story (slug);

CREATE TABLE chapter
(
    id                 bigserial     NOT NULL,
    story_id           bigint        NOT NULL,

    index              int           NOT NULL,
    title              varchar(2500) NOT NULL,
    content            text NULL,

    external_url       varchar(2500) NOT NULL,

    created_date       timestamp     NOT NULL,
    last_modified_date timestamp DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    UNIQUE (story_id, index),
    CONSTRAINT fk_chapter_story_id FOREIGN KEY (story_id) REFERENCES story (id)
);

CREATE INDEX chapter_story_index_idx ON chapter (story_id, index);

CREATE TABLE genre
(
    id                 bigserial    NOT NULL,
    code               varchar(500) NOT NULL,
    title              varchar(500) NOT NULL,

    created_date       timestamp    NOT NULL,
    last_modified_date timestamp DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    UNIQUE (code),
    UNIQUE (title)
);

CREATE INDEX genre_idx ON genre (code);

CREATE TABLE story_genre
(
    story_id bigint NOT NULL,
    genre_id bigint NOT NULL,

    PRIMARY KEY (story_id, genre_id),
    CONSTRAINT fk_story_genre_story_id FOREIGN KEY (story_id) REFERENCES story (id),
    CONSTRAINT fk_story_genre_genre_id FOREIGN KEY (genre_id) REFERENCES genre (id)
);

CREATE TABLE tag
(
    id                 bigserial    NOT NULL,
    code               varchar(500) NOT NULL,
    title              varchar(500) NOT NULL,

    created_date       timestamp    NOT NULL,
    last_modified_date timestamp DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE story_tag
(
    story_id bigint NOT NULL,
    tag_id   bigint NOT NULL,

    PRIMARY KEY (story_id, tag_id),
    CONSTRAINT fk_story_tag_story_id FOREIGN KEY (story_id) REFERENCES story (id),
    CONSTRAINT fk_story_tag_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id)
);