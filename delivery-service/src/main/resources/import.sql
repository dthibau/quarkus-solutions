insert into livreur (id, nom,telephone,status) values (1, 'Speedy Gonzales','06-49-89-69-19','ACTIF');
insert into review(note, commentaire) values (5, 'Super livreur');
insert into Livreur_Review (livreur_id, reviews_id) values (1, 1);

insert into livraison ( noCommande, creationDate, status, livreur_id) values ( 1, '2024-01-01 12:00:00', 'LIVREUR_AFFECTE', 1);
insert into livraison ( noCommande, creationDate, status, livreur_id) values ( 2, '2024-01-01 12:00:00', 'CREE', null);
insert into livraison ( noCommande, creationDate, status, livreur_id) values ( 3, '2024-01-01 12:00:00', 'CREE', null);
insert into livraison ( noCommande, creationDate, status, livreur_id) values ( 4, '2024-01-01 12:00:00', 'CREE', null);





