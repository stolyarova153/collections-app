--liquibase formatted sql

--changeset me:1

CREATE TABLE IF NOT EXISTS public.collections
(
    id            bigserial primary key NOT NULL,
    name          character varying     NOT NULL,
    description   text,
    last_modified timestamp without time zone,
    global_id     bigint                NOT NULL,
    "user"        bigint                NOT NULL
);

CREATE TABLE IF NOT EXISTS public.friends
(
    id        bigserial primary key NOT NULL,
    user1     bigint,
    user2     bigint,
    tag       character varying,
    startdate timestamp without time zone,
    enddate   timestamp without time zone
);

CREATE TABLE IF NOT EXISTS public.globalmedias
(
    media  bigint NOT NULL,
    global bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.globals
(
    id       bigserial primary key NOT NULL,
    datetime timestamp without time zone
);

CREATE TABLE IF NOT EXISTS public.items
(
    id            bigserial primary key NOT NULL,
    name          character varying     NOT NULL,
    origin        character varying,
    creation_year smallint,
    hand_datetime timestamp without time zone,
    materials     character varying,
    description   text,
    global_id     bigint                NOT NULL
);

CREATE TABLE IF NOT EXISTS public.items2collections
(
    collection bigint NOT NULL,
    item       bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.medias
(
    id        bigserial primary key NOT NULL,
    name      character varying,
    size      double precision,
    hash      character varying,
    caption   character varying,
    mediatype bigint                NOT NULL,
    datetime  timestamp without time zone,
    file      bytea
);

CREATE TABLE IF NOT EXISTS public.mediatypes
(
    id          bigserial primary key NOT NULL,
    name        character varying     NOT NULL,
    description character varying
);

CREATE TABLE IF NOT EXISTS public.refresh_tokens
(
    id          bigserial primary key       NOT NULL,
    "user"      bigint                      NOT NULL,
    token       character varying           NOT NULL,
    expiry_date timestamp without time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS public.roles
(
    id    bigserial primary key NOT NULL,
    name  character varying     NOT NULL,
    alias character varying,
    rank  integer
);

CREATE TABLE IF NOT EXISTS public.user2role
(
    "user" bigint NOT NULL,
    role   bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.usermediahistory
(
    id       bigserial primary key NOT NULL,
    "user"   bigint                NOT NULL,
    media    bigint,
    datetime timestamp without time zone
);

CREATE TABLE IF NOT EXISTS public.users
(
    id         bigserial primary key       NOT NULL,
    lastname   character varying           NOT NULL,
    name       character varying           NOT NULL,
    patronymic character varying,
    birthdate  timestamp without time zone NOT NULL,
    email      character varying,
    phone      character varying,
    password   character varying           NOT NULL
);

ALTER TABLE ONLY public.globalmedias
    DROP CONSTRAINT IF EXISTS globalmedias_pkey;
ALTER TABLE ONLY public.globalmedias
    ADD CONSTRAINT globalmedias_pkey PRIMARY KEY (global, media);

ALTER TABLE ONLY public.items2collections
    DROP CONSTRAINT IF EXISTS items2collections_pkey;
ALTER TABLE ONLY public.items2collections
    ADD CONSTRAINT items2collections_pkey PRIMARY KEY (collection, item);

ALTER TABLE ONLY public.refresh_tokens
    DROP CONSTRAINT IF EXISTS refresh_tokens_token_key;
ALTER TABLE ONLY public.refresh_tokens
    ADD CONSTRAINT refresh_tokens_token_key UNIQUE (token);

ALTER TABLE ONLY public.user2role
    DROP CONSTRAINT IF EXISTS user2role_pkey;
ALTER TABLE ONLY public.user2role
    ADD CONSTRAINT user2role_pkey PRIMARY KEY ("user", role);

ALTER TABLE ONLY public.collections
    DROP CONSTRAINT IF EXISTS collections_global_id_fkey;
ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_global_id_fkey FOREIGN KEY (global_id) REFERENCES public.globals (id) NOT VALID;

ALTER TABLE ONLY public.collections
    DROP CONSTRAINT IF EXISTS collections_user_fkey;
ALTER TABLE ONLY public.collections
    ADD CONSTRAINT collections_user_fkey FOREIGN KEY ("user") REFERENCES public.users (id) NOT VALID;

ALTER TABLE ONLY public.globalmedias
    DROP CONSTRAINT IF EXISTS globalmedias_global_fkey;
ALTER TABLE ONLY public.globalmedias
    ADD CONSTRAINT globalmedias_global_fkey FOREIGN KEY (global) REFERENCES public.globals (id);

ALTER TABLE ONLY public.globalmedias
    DROP CONSTRAINT IF EXISTS globalmedias_media_fkey;
ALTER TABLE ONLY public.globalmedias
    ADD CONSTRAINT globalmedias_media_fkey FOREIGN KEY (media) REFERENCES public.medias (id);

ALTER TABLE ONLY public.items2collections
    DROP CONSTRAINT IF EXISTS items2collections_collection_fkey;
ALTER TABLE ONLY public.items2collections
    ADD CONSTRAINT items2collections_collection_fkey FOREIGN KEY (collection) REFERENCES public.collections (id);

ALTER TABLE ONLY public.items2collections
    DROP CONSTRAINT IF EXISTS items2collections_item_fkey;
ALTER TABLE ONLY public.items2collections
    ADD CONSTRAINT items2collections_item_fkey FOREIGN KEY (item) REFERENCES public.items (id);

ALTER TABLE ONLY public.items
    DROP CONSTRAINT IF EXISTS items_global_id_fkey;
ALTER TABLE ONLY public.items
    ADD CONSTRAINT items_global_id_fkey FOREIGN KEY (global_id) REFERENCES public.globals (id) NOT VALID;

ALTER TABLE ONLY public.medias
    DROP CONSTRAINT IF EXISTS medias_mediatype_fkey;
ALTER TABLE ONLY public.medias
    ADD CONSTRAINT medias_mediatype_fkey FOREIGN KEY (mediatype) REFERENCES public.mediatypes (id) NOT VALID;

ALTER TABLE ONLY public.refresh_tokens
    DROP CONSTRAINT IF EXISTS refresh_tokens_user_fkey;
ALTER TABLE ONLY public.refresh_tokens
    ADD CONSTRAINT refresh_tokens_user_fkey FOREIGN KEY ("user") REFERENCES public.users (id);

ALTER TABLE ONLY public.user2role
    DROP CONSTRAINT IF EXISTS user2role_role_fkey;
ALTER TABLE ONLY public.user2role
    ADD CONSTRAINT user2role_role_fkey FOREIGN KEY (role) REFERENCES public.roles (id);

ALTER TABLE ONLY public.user2role
    DROP CONSTRAINT IF EXISTS user2role_user_fkey;
ALTER TABLE ONLY public.user2role
    ADD CONSTRAINT user2role_user_fkey FOREIGN KEY ("user") REFERENCES public.users (id);

ALTER TABLE ONLY public.usermediahistory
    DROP CONSTRAINT IF EXISTS usermediahistory_user_fkey;
ALTER TABLE ONLY public.usermediahistory
    ADD CONSTRAINT usermediahistory_user_fkey FOREIGN KEY ("user") REFERENCES public.users (id);
