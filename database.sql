DROP DATABASE IF EXISTS tpsit;
CREATE DATABASE tpsit ;
use tpsit;

CREATE TABLE studenti (
    IdStudente int ,
    Cognome varchar(255),
    Nome varchar(255),
	PRIMARY KEY (IdStudente)
);

insert into studenti(IdStudente,Cognome,Nome)values("1","Rossi","Mario");
insert into studenti(IdStudente,Cognome,Nome)values("2","Verdi","Luigi");
insert into studenti(IdStudente,Cognome,Nome)values("3","Bianchi","Marco");
insert into studenti(IdStudente,Cognome,Nome)values("4","Neri","Umberto");
