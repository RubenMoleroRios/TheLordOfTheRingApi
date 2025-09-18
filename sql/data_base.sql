create database if not exists `lotr`;
use `lotr`;

drop table heroes;
drop table breed;
drop table side;




create table if not exists `side`(
	`id` bigint primary key auto_increment,
    `name` varchar(40)
);

create table if not exists `breed`(
	`id` bigint primary key auto_increment,
    `name` varchar(40)
);


create table if not exists `heroes`(
	`id` bigint primary key auto_increment,
    `name` varchar(40),
    `last_name` varchar(40),
    `height` double,
    `hair_color` varchar(30),
    `eyes_color` varchar (30),
    `description` varchar (600),
    `id_side` bigint,
    foreign key(id_side) references side(`id`),
    `id_breed` bigint,
    foreign key(id_breed) references breed(`id`)    
);

insert into side values(1,"bando del bien");
insert into side values(2,"bando del mal");
insert into side values(3,"bando nautral");

insert into breed values(1,"Elfos");
insert into breed values(2,"Hombres");
insert into breed values(3,"Enanos");
insert into breed values(4,"Hobbits");
insert into breed values(5,"Orcos");
insert into breed values(6,"Ents");
insert into breed values(7,"Maiar");

insert into heroes values(1,"Legolas", "Hojaverde",1.80,"Rubio","Azules","Príncipe del Bosque Negro, ágil, arquero experto, miembro de la Comunidad del Anillo.",1,1);
insert into heroes values(2,"Elrond", "Peredhil",1.90,"Negro","Grises","Señor de Rivendel, sabio y poderoso, padre de Arwen, medio elfo pero eligió ser elfo.",1,1);
insert into heroes values(3,"Galadriel", "Finarfin",1.90,"Dorado Plateado","Azules/Grises", "Señora de Lothlórien, una de las elfas más antiguas y poderosas, poseedora de Nenya, uno de los Tres Anillos élficos.",1,1);
Insert into heroes values(4,"Aragorn", "Elsessar",1.98,"Oscuro","Grises","Heredero de Isildur, montaraz, líder de la Comunidad, futuro rey de Gondor y Arnor.",1,2);
insert into heroes values(5,"Boromir","de gondor",1.90,"Castaño Claro","Grises","Capitán de Gondor, valiente pero tentado por el poder del Anillo, muere defendiendo a Merry y Pippin.",1,2);
insert into heroes values(6,"Théoden","hijo de thengel",1.85,"Rubio","Azules","Rey de Rohan, honorable, liberado de la influencia de Saruman, cae en la Batalla de los Campos del Pelennor.",1,2);
insert into heroes values(7,"Gimli","hijo de glóin",1.35,"Rojo","Marrones","Guerrero enano, miembro de la Comunidad, forja amistad con Legolas.",1,3);
insert into heroes values(8,"Góin","hijo de gróin",1.35,"Rojo","Oscuros","Padre de Gimli, uno de los enanos de El Hobbit, aparece en el Concilio de Elrond.",1,3);
insert into heroes values(9,"Dain","Pie de hierro",1.35,"Castaño/Rojizo","Oscuros","Rey bajo la Montaña tras la muerte de Thorin, líder valiente de los enanos de las Colinas de Hierro.",1,3);
insert into heroes values(10,"Frodo","Bolsón",1.20,"Castaño","Azules","Portador del Anillo, sobrino de Bilbo, carga con la misión de destruirlo en Mordor.",1,4);
insert into heroes values(11,"Sam","Gamyi",1.22,"Rubio/Castaño claro","Marrones","Jardinero y amigo leal de Frodo, pieza clave en la destrucción del Anillo.",1,4);
insert into heroes values(12,"Pippin","Tuk",1.20,"Castaño claro","Verdes","El más joven de la Comunidad, curioso y algo imprudente, pero valiente en el campo de batalla.",1,4);
insert into heroes values(13,"Lurtz","sin apellido",2.10,"Negro","Amarillentos","Uruk-hai creado por Saruman, líder en Amon Hen, mata a Boromir (versión cinematográfica).",2,5);
insert into heroes values(14,"Gorbag","sin apellido",1.80,"Escaso","Rojizos","Orco de Minas Morgul, comandaba el ataque a las minas tirith",2,5);
insert into heroes values(15,"Grishnákh","sin apellido",1.75,"Claro","Oscuros","Orco de Mordor, acompaña a los Uruk-hai que capturan a Merry y Pippin, muere en Fangorn.",2,5);
insert into heroes values(16,"Barbol","Treebread",5,"no tiene pelo","verde-dorados","El más viejo de los Ents, ayuda a los hobbits y dirige el ataque a Isengard.",3,6);
insert into heroes values(17,"Bregalad","Corteza Rápida",4,"Similar a hojas jóvenes","Verde claro","Más impetuoso que otros ents, amigo de Merry y Pippin",3,6);
insert into heroes values(18,"Gandalf","El gris",1.8,"Gris","Azules","Mago, guía de la Comunidad, sabio y compasivo, regresa como Gandalf el Blanco tras su caída en Moria.",1,7);
insert into heroes values(19,"Saruman","El blanco",1.90,"Blanco","Oscuros","Líder del Concilio Blanco, traiciona y se alía con Sauron, controla Isengard.",2,7);
insert into heroes values(20,"Sauron", "El señor oscuro",2,"Negro","Rojo","Antiguo Maia corrompido, creador del Anillo Único, enemigo principal de la Tercera Edad.",2,7);


select * From heroes;
