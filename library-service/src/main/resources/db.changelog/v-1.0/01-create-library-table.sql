create table library (
         id bigserial not null,
         book_id bigint not null,
         borrowed_at timestamp(6),
         return_due_at timestamp(6),
         primary key (id)
)