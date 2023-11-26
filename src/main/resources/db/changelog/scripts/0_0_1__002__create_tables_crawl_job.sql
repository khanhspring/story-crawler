CREATE TABLE source_url
(
    id                 bigserial     NOT NULL,
    source_id          bigint        NOT NULL,
    url                varchar(2500) NOT NULL,

    created_date       timestamp     NOT NULL,
    last_modified_date timestamp DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    CONSTRAINT fk_source_url_source_id FOREIGN KEY (source_id) REFERENCES source (id)
);

CREATE TABLE crawl_stories_job
(
    id                 bigserial     NOT NULL,
    source_id          bigint        NOT NULL,

    base_url           varchar(2500) NOT NULL,
    status             varchar(30)   NOT NULL,
    current_page       int NULL,

    completed_time     timestamp              DEFAULT NULL,
    failed_count       int           NOT NULL DEFAULT 0,
    error_message      text NULL,

    created_date       timestamp     NOT NULL,
    last_modified_date timestamp              DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    CONSTRAINT fk_crawl_stories_job_source_id FOREIGN KEY (source_id) REFERENCES source (id)
);

CREATE TABLE crawl_story_job
(
    id                 bigserial   NOT NULL,
    story_id           bigint      NOT NULL,

    type               varchar(30) NOT NULL, -- New, Update, Reset
    priority           int         NOT NULL DEFAULT 100,

    url                varchar(2500) NULL,
    status             varchar(30) NOT NULL,

    completed_time     timestamp            DEFAULT NULL,
    failed_count       int         NOT NULL DEFAULT 0,
    error_message      text NULL,

    created_date       timestamp   NOT NULL,
    last_modified_date timestamp            DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    CONSTRAINT fk_crawl_story_job_story_id FOREIGN KEY (story_id) REFERENCES story (id)
);

CREATE TABLE crawl_chapters_job
(
    id                 bigserial   NOT NULL,
    story_id           bigint      NOT NULL,

    status             varchar(30) NOT NULL,

    start_index        int NULL,
    start_url          varchar(2500) NULL,

    current_index      int NULL,
    current_url        varchar(2500) NULL,

    end_index          int NULL,
    end_url            varchar(2500) NULL,

    completed_time     timestamp            DEFAULT NULL,
    failed_count       int         NOT NULL DEFAULT 0,
    error_message      text NULL,

    created_date       timestamp   NOT NULL,
    last_modified_date timestamp            DEFAULT NULL,
    version            int,

    PRIMARY KEY (id),
    CONSTRAINT fk_crawl_chapters_job_story_id FOREIGN KEY (story_id) REFERENCES story (id)
);