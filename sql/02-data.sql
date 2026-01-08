USE `lotr`;

SET CHARACTER SET utf8mb4;

insert into side values(UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"), "bando del bien");
insert into side values(UUID_TO_BIN("5471980f-31c9-4a4d-92bc-9d8fa27a031b"),"bando del mal");
insert into side values(UUID_TO_BIN("9f273070-5ffe-4910-ac7a-d1c916dfe7bd"),"bando nautral");


insert into breed values(UUID_TO_BIN("f99fd696-399c-4417-9acb-72e3eeffb1c7"),"Elfos");
insert into breed values(UUID_TO_BIN("4f54a1d6-83b8-468a-996d-6f8c0b9c0330"),"Hombres");
insert into breed values(UUID_TO_BIN("c593ae12-93b0-4c9a-a09c-3128335d628c"),"Enanos");
insert into breed values(UUID_TO_BIN("5f02ba1d-8744-4598-becc-63953a523c36"),"Hobbits");
insert into breed values(UUID_TO_BIN("762fe2eb-4f16-4bd5-ae3d-569e2a04d3cd"),"Orcos");
insert into breed values(UUID_TO_BIN("2f1cdb02-4317-49e9-a2b7-a5008d315a19"),"Ents");
insert into breed values(UUID_TO_BIN("76e2686f-431f-40f0-9361-aed97f5b62a5"),"Maiar");

insert into heroes values(UUID_TO_BIN("54fa1b2e-e785-4e02-bf15-df030428c4b6"),"Legolas", "Hojaverde",1.80,"Rubio","Azules","Príncipe del Bosque Negro, ágil, arquero experto, miembro de la Comunidad del Anillo.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("f99fd696-399c-4417-9acb-72e3eeffb1c7"));
insert into heroes values(UUID_TO_BIN("94f3d630-d725-4179-acc3-cfcdac8f6a14"),"Elrond", "Peredhil",1.90,"Negro","Grises","Señor de Rivendel, sabio y poderoso, padre de Arwen, medio elfo pero eligió ser elfo.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("f99fd696-399c-4417-9acb-72e3eeffb1c7"));
insert into heroes values(UUID_TO_BIN("a5d57102-2cd7-4488-beb1-c8b283201580"),"Galadriel", "Finarfin",1.90,"Dorado Plateado","Azules/Grises", "Señora de Lothlórien, una de las elfas más antiguas y poderosas, poseedora de Nenya, uno de los Tres Anillos élficos.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("f99fd696-399c-4417-9acb-72e3eeffb1c7"));
Insert into heroes values(UUID_TO_BIN("96e45918-9804-4df0-8fb4-a9c9cfcfeae1"),"Aragorn", "Elsessar",1.98,"Oscuro","Grises","Heredero de Isildur, montaraz, líder de la Comunidad, futuro rey de Gondor y Arnor.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("4f54a1d6-83b8-468a-996d-6f8c0b9c0330"));
insert into heroes values(UUID_TO_BIN("ee5ff55b-967e-4d16-a31b-0ef5e05298ce"),"Boromir","de gondor",1.90,"Castaño Claro","Grises","Capitán de Gondor, valiente pero tentado por el poder del Anillo, muere defendiendo a Merry y Pippin.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("4f54a1d6-83b8-468a-996d-6f8c0b9c0330"));
insert into heroes values(UUID_TO_BIN("75ba8ee5-800e-4015-9763-c9531efe9365"),"Théoden","hijo de thengel",1.85,"Rubio","Azules","Rey de Rohan, honorable, liberado de la influencia de Saruman, cae en la Batalla de los Campos del Pelennor.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("4f54a1d6-83b8-468a-996d-6f8c0b9c0330"));
insert into heroes values(UUID_TO_BIN("edafd4a0-9ad3-4cdd-a9c8-0ca889c79a5e"),"Gimli","hijo de glóin",1.35,"Rojo","Marrones","Guerrero enano, miembro de la Comunidad, forja amistad con Legolas.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("c593ae12-93b0-4c9a-a09c-3128335d628c"));
insert into heroes values(UUID_TO_BIN("0c553a78-a6e9-4998-b661-0e06bb10588d"),"Góin","hijo de gróin",1.35,"Rojo","Oscuros","Padre de Gimli, uno de los enanos de El Hobbit, aparece en el Concilio de Elrond.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("c593ae12-93b0-4c9a-a09c-3128335d628c"));
insert into heroes values(UUID_TO_BIN("a7777dec-29fa-4846-a5d2-3688d03a7ebf"),"Dain","Pie de hierro",1.35,"Castaño/Rojizo","Oscuros","Rey bajo la Montaña tras la muerte de Thorin, líder valiente de los enanos de las Colinas de Hierro.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("c593ae12-93b0-4c9a-a09c-3128335d628c"));
insert into heroes values(UUID_TO_BIN("3eb92105-224c-4c1b-aa35-4c61834be917"),"Frodo","Bolsón",1.20,"Castaño","Azules","Portador del Anillo, sobrino de Bilbo, carga con la misión de destruirlo en Mordor.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("5f02ba1d-8744-4598-becc-63953a523c36"));
insert into heroes values(UUID_TO_BIN("8d2f81e1-8681-4d99-8221-5f094307d420"),"Sam","Gamyi",1.22,"Rubio/Castaño claro","Marrones","Jardinero y amigo leal de Frodo, pieza clave en la destrucción del Anillo.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("5f02ba1d-8744-4598-becc-63953a523c36"));
insert into heroes values(UUID_TO_BIN("e2e55f98-1c50-401b-81e2-fcde32baab82"),"Pippin","Tuk",1.20,"Castaño claro","Verdes","El más joven de la Comunidad, curioso y algo imprudente, pero valiente en el campo de batalla.",UUID_TO_BIN("ce145f20-c5d3-42d2-89a1-1c9df74061f6"),UUID_TO_BIN("5f02ba1d-8744-4598-becc-63953a523c36"));
insert into heroes values(UUID_TO_BIN("9ebf7d8d-0f35-44e4-9931-f591c4142aec"),"Lurtz","sin apellido",2.10,"Negro","Amarillentos","Uruk-hai creado por Saruman, líder en Amon Hen, mata a Boromir (versión cinematográfica).",UUID_TO_BIN("5471980f-31c9-4a4d-92bc-9d8fa27a031b"),UUID_TO_BIN("762fe2eb-4f16-4bd5-ae3d-569e2a04d3cd"));
insert into heroes values(UUID_TO_BIN("91fb3199-3a2f-4aff-8267-6177f554ee3f"),"Gorbag","sin apellido",1.80,"Escaso","Rojizos","Orco de Minas Morgul, comandaba el ataque a las minas tirith",UUID_TO_BIN("5471980f-31c9-4a4d-92bc-9d8fa27a031b"),UUID_TO_BIN("762fe2eb-4f16-4bd5-ae3d-569e2a04d3cd"));
insert into heroes values(UUID_TO_BIN("56403631-3232-4b9c-ae21-7cc21c1b492d"),"Grishnákh","sin apellido",1.75,"Claro","Oscuros","Orco de Mordor, acompaña a los Uruk-hai que capturan a Merry y Pippin, muere en Fangorn.",UUID_TO_BIN("5471980f-31c9-4a4d-92bc-9d8fa27a031b"),UUID_TO_BIN("762fe2eb-4f16-4bd5-ae3d-569e2a04d3cd"));
insert into heroes values(UUID_TO_BIN("770db63a-59db-4751-81d3-cedfb3934f28"),"Barbol","Treebread",5,"no tiene pelo","verde-dorados","El más viejo de los Ents, ayuda a los hobbits y dirige el ataque a Isengard.",UUID_TO_BIN("9f273070-5ffe-4910-ac7a-d1c916dfe7bd"),UUID_TO_BIN("2f1cdb02-4317-49e9-a2b7-a5008d315a19"));
insert into heroes values(UUID_TO_BIN("192b77bb-b2a1-495e-bad3-41f26045101b"),"Bregalad","Corteza Rápida",4,"Similar a hojas jóvenes","Verde claro","Más impetuoso que otros ents, amigo de Merry y Pippin",UUID_TO_BIN("9f273070-5ffe-4910-ac7a-d1c916dfe7bd"),UUID_TO_BIN("2f1cdb02-4317-49e9-a2b7-a5008d315a19"));
insert into heroes values(UUID_TO_BIN("dec5a206-5f7f-4867-a1c1-5aaaa25c6d60"),"Gandalf","El gris",1.8,"Gris","Azules","Mago, guía de la Comunidad, sabio y compasivo, regresa como Gandalf el Blanco tras su caída en Moria.",UUID_TO_BIN("9f273070-5ffe-4910-ac7a-d1c916dfe7bd"),UUID_TO_BIN("76e2686f-431f-40f0-9361-aed97f5b62a5"));
insert into heroes values(UUID_TO_BIN("193ece8a-4f61-4037-a750-ce94628e81ec"),"Saruman","El blanco",1.90,"Blanco","Oscuros","Líder del Concilio Blanco, traiciona y se alía con Sauron, controla Isengard.",UUID_TO_BIN("9f273070-5ffe-4910-ac7a-d1c916dfe7bd"),UUID_TO_BIN("76e2686f-431f-40f0-9361-aed97f5b62a5"));
insert into heroes values(UUID_TO_BIN("f53bc7a7-e8ff-47d3-9549-303db25a8b31"),"Sauron", "El señor oscuro",2,"Negro","Rojo","Antiguo Maia corrompido, creador del Anillo Único, enemigo principal de la Tercera Edad.",UUID_TO_BIN("9f273070-5ffe-4910-ac7a-d1c916dfe7bd"),UUID_TO_BIN("76e2686f-431f-40f0-9361-aed97f5b62a5"));
