create table exercise (
	id	INTEGER IDENTITY PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	weight DOUBLE,
	quantity int,
	points DOUBLE
);

create table activity (
	id	INTEGER IDENTITY PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	weekday VARCHAR(30)
);

create table activity_exercise (
	activities_id INTEGER,
	exercises_id INTEGER,
	constraint fk_activities foreign key (activities_id) references activity(id),
	constraint fk_exercices foreign key (exercises_id) references exercise(id)
);