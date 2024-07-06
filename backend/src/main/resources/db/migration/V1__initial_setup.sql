create table problem
(
    id   uuid primary key,
    grid text not null,
    size int  not null
);

create table solution
(
    id         uuid primary key,
    problem_id uuid not null,

    constraint fk_problem
        foreign key (problem_id)
            references problem (id)
);

create table solution_step
(
    id          uuid primary key,
    solution_id uuid not null,
    index       int  not null,
    row         int  not null,
    col         int  not null,

    constraint fk_solution
        foreign key (solution_id)
            references solution (id)
);