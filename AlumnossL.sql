create database AlumnossL ;
use AlumnossL;

create table Carrera(claveCarrera varchar(3) not null, NombreCarrera varchar(150),
primary key (claveCarrera));

create table Alumno(numControl int not null, primary key (numControl), 
semestre int not null,
nombre varchar(50) not null,
apPaterno varchar(50) not null,
apMaterno varchar(50) not null, 
turno varchar(10) not null, 
campus varchar(10) not null,
claveCarrera varchar(3), foreign key (claveCarrera) references Carrera(claveCarrera));

create table Materias(nombre varchar(100) not null,
claveMateria varchar(10) not null, primary key(claveMateria),
claveCarrera varchar(3), foreign key (claveCarrera) references carrera(claveCarrera),
creditos int not null, 
semestre int);

create table grupo (claveGrupo int not null, primary key(claveGrupo),
claveMateria varchar(10), foreign key(claveMateria) references materias(ClaveMateria),
aula varchar(5), 
maestro varchar(100),
Hlunes varchar(12) not null,
Hmartes varchar(12) not null,
Hmiercoles varchar(12) not null,
Hjueves varchar(12) not null,
Hviernes varchar(12) not null);

create table grupoAlumno(numControl int not null, foreign key(numControl) references alumno(numControl),
claveGrupo int not null, foreign key (claveGrupo) references grupo(claveGrupo));

insert into carrera values ('ISX', 'Ingeniería en Sistemas Computacionales');
insert into carrera values ('IIX', 'Ingeniería Industrial');
insert into carrera values ('ITI','Ingeniería en Tecnologías de la informacion');
insert into carrera values ('IGE', 'Ingeniería en Gestíon Empresarial');
 
insert into alumno values (15240540, 4,'José Miguel','Becerra','Vázquez','Matutino','Campus I','ISX');
insert into alumno values (15221702, 3,'Alfredo','Espinoza','Velazquez','Matutino','Campus I','IIX');
insert into alumno values (15241208, 2,'Claudia Patricia','Lopez','Ortiz','Matutino','Campus I','ITI');


insert into materias values('Fundamentos de Bases de Datos', 'AEF1031','ISX',4,4);
insert into materias values('Ecuaciones Diferenciales', 'ACF095','ISX',5,4);
insert into materias values('Topicos Avanzados De Programacion ', 'ATP0527','ISX',5,4);
insert into materias values('Principios Electricos Y Aplicaciones Digitales ', 'AED115','ISX',5,4);
insert into materias values('Metodos Numericos ', 'AMN701','ISX',4,4);

insert into materias values('Calculo Vectorial', 'AIC826','IIX',5,3);
insert into materias values('Desarrollo Sustentable', 'ADS971','IIX',4,3);
insert into materias values('Estructura De Datos', 'AD017','IIX',4,3);
insert into materias values('Fisica General', 'AFG730','IIX',5,3);
insert into materias values('Investigacion De Operaciones', 'ADO652','IIX',4,3);

insert into materias values('Algebra Lineal', 'ALL912','ITI',5,2);
insert into materias values('Contabilidad Financiera', 'ACF090','ITI',4,2);
insert into materias values('Quimica', 'AQM101','ITI',5,2);
insert into materias values('Probabilidad y Estadistica', 'APE005','ITI',4,2);
insert into materias values('Taller de Etica', 'ATE656','ITI',4,2);

insert into grupo values(4075,'AEF1031','LBD','Ruiz Gaytan Luz del Carmen','12:15-13:55','',
'12:15-13:55','','12:15-13:05');
insert into grupo values(4065,'ACF095','ED','Rico Perez Joel','10:30-12:10','',
'10:30-12:10','','10:30-11:20');
insert into grupo values(4093,'ATP0527','TAP','Levy Rojas Carlos Rafael','','10:30-12:10','',
'10:30-12:10','10:30-11:20');
insert into grupo values(4081,'AED115','PEAD','Perez Pintor Eduardo Jose','8:45-10:25','',
'8:45-10:25','','8:45-9:35');
insert into grupo values(4069,'AMN701','MN','Rico Perez Joel','','12:15-13:55','',
'12:15-13:55','');

insert into grupo values(4066,'AIC826','CV','Ramon Tapia Jose','','8:45-10:25','',
'8:45-10:25','8:45-9:35');
insert into grupo values(4011,'ADS971','DS','Ramirez Alvarez Irma De Jesus ','','10:30-12:10','',
'10:30-12:10','');
insert into grupo values(4006,'AD017','ED','Cirino Silva Tovar','','7:00-8:40','',
'7:00-8:40','');
insert into grupo values(4017,'AFG730','FG','David Asael Gutierrez Hernandez ','8:45-10:25','','8:45-10:25',
'','8:45-9:35');
insert into grupo values(4053,'ADO652','IO','Gomez Guerra Fernando ','12:15-13:55','','12:15-13:55',
'','');

insert into grupo values(4015,'ALL912','AL',' Infante Medina Rogelio ','8:45-10:25','','8:45-10:25',
'','8:45-9:35');
insert into grupo values(4088,'ACF090','CF',' Juan Martin Carpio Valadez ','','12:15-13:55','',
'12:15-13:55','');
insert into grupo values(4070,'AQM101','QM','Rico Perez Joel ','7:00-8:40','','7:00-8:40',
'','7:00-8:40');
insert into grupo values(4060,'APE005','PE',' Juan De Dios Paz Salinas ','10:30-12:10','','10:30-12:10',
'','');
insert into grupo values(4033,'ATE656','TE',' Castellanos Nolasco Elizabeth ','','10:30-12:10','',
'10:30-12:10','');

insert into grupoalumno values(15240540,4075);
insert into grupoalumno values(15240540,4065);
insert into grupoalumno values(15240540,4093);
insert into grupoalumno values(15240540,4081);
insert into grupoalumno values(15240540,4069);

insert into grupoalumno values(15221702,4066);
insert into grupoalumno values(15221702,4011);
insert into grupoalumno values(15221702,4006);
insert into grupoalumno values(15221702,4017);
insert into grupoalumno values(15221702,4053);

insert into grupoalumno values(15241208,4015);
insert into grupoalumno values(15241208,4088);
insert into grupoalumno values(15241208,4070);
insert into grupoalumno values(15241208,4060);
insert into grupoalumno values(15241208,4033);

select * from alumno;
select * from carrera;
select * from materias;
select * from grupo;
select * from grupoalumno;

----consultas----
 
 update alumno set semestre = 2 where alumno.numControl = 15241208;
 
select * from grupo join materias  where  claveCarrera= 'IGE' and semestre = 2;

select * from materias where  claveCarrera = 'IGE'; 

select * from materias join grupo where materias.nombre = 'Quimica';
 
 select nombre
 from alumno 
 where alumno.claveCarrera ='ISX';
  
 select * from materias join grupo where materias.claveCarrera= 'ITI';
 
select nombre,apPaterno,apMaterno
from Alumno
 
select claveMateria 
from grupo 

select maestro
from grupo
where maestro= 'Rico Perez Joel'