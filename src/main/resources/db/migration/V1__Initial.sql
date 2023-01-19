CREATE TABLE IF NOT EXISTS public.tutorials
(
    id bigserial not null,
    description character varying(255) COLLATE pg_catalog."default",
    published boolean,
    title character varying(255) COLLATE pg_catalog."default",
    primary key(id)
);

-- TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tutorials
    OWNER to postgres;

INSERT INTO tutorials (description, published, title) VALUES ('tutorial#1',false, 'tutorial#1');
INSERT INTO tutorials (description, published, title) VALUES ('tutorial#2',false, 'tutorial#2');
INSERT INTO tutorials (description, published, title) VALUES ('tutorial#3',false, 'tutorial#3');
INSERT INTO tutorials (description, published, title) VALUES ('tutorial#4',false, 'tutorial#4');