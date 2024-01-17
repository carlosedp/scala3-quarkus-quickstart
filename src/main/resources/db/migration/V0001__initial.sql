create table if not exists tasks (
    id bigint primary key generated always as identity,
    title text not null,
    description text not null,
    done boolean not null default false
);

create table if not exists app_users (
    id bigint primary key generated always as identity,
    name text not null,
    email text not null,
    hashed_password text not null
);
