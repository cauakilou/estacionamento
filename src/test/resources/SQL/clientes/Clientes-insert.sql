insert into USUARIOS (id, username, password, role)values(100, 'ana@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_ADMIN')
insert into USUARIOS (id, username, password, role)values(101, 'caua@email.com','$2a$12$DnRGdhJRYfinn5DdgkXbiOZhEdKL.q.nLq0LXciB4yvfVlYAlOSH2','ROLE_CLIENT')
insert into USUARIOS (id, username, password, role)values(123, 'sam@email.com','$2a$12$AJmWeuEWvCV1VtRNcS776OclrYrmcoEWw8YTrQVfCo1vC9ak6JVoa','ROLE_CLIENT')
insert into USUARIOS (id, username, password, role)values(102, 'toby@email.com','$2a$12$A38mKnfWocV7uK9zc/KxOuei53EQkKXn/3Y1747KWuQDSmbJKLcnW','ROLE_CLIENT')

insert into CLIENTES (id,nome,cpf,id_Usuario)values(10, 'Bianca Silva', '97688468019',101)
insert into CLIENTES (id,nome,cpf,id_Usuario)values(20, 'Roberto Gomes', '81708991093',123)