SET NAMES utf8mb4;

DROP DATABASE IF EXISTS electivesdb;
CREATE DATABASE electivesdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE electivesdb;

CREATE TABLE role (
	id INT NOT NULL PRIMARY KEY,
	mame VARCHAR(10) NOT NULL UNIQUE)
ENGINE=InnoDB;

CREATE TABLE user (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	email VARCHAR(45) NOT NULL UNIQUE,
	password CHAR(64) NOT NULL,
	f_name VARCHAR(20) NOT NULL,
	l_name VARCHAR(20) NOT NULL,
	role_id INT NOT NULL,
	is_blocked TINYINT NOT NULL,
	FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE)
ENGINE=InnoDB;

CREATE TABLE topic (
	id INT NOT NULL PRIMARY KEY,
	mame VARCHAR(45) NOT NULL UNIQUE)
ENGINE=InnoDB;

CREATE TABLE course (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(45) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	duration INT NOT NULL,
	topic_id INT NOT NULL,
	teacher_id INT NOT NULL,
	description VARCHAR(255) DEFAULT NULL,
	FOREIGN KEY (topic_id) REFERENCES topic (id) ON DELETE CASCADE)
	FOREIGN KEY (teacher_id) REFERENCES user (id) ON DELETE CASCADE)
ENGINE=InnoDB;

CREATE TABLE course_student (
	course_id INT NOT NULL,
	student_id INT NOT NULL,
	mark INT DEFAULT NULL,
	FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
	FOREIGN KEY (student_id) REFERENCES user (id) ON DELETE CASCADE)
ENGINE=InnoDB;

INSERT INTO role (id, name) VALUES (0, 'admin');
INSERT INTO role (id, name) VALUES (1, 'teacher');
INSERT INTO role (id, name) VALUES (2, 'student');

INSERT INTO topic VALUES (default, 'Java');
INSERT INTO topic VALUES (default, 'JavaScript');
INSERT INTO topic VALUES (default, 'Python');
INSERT INTO topic VALUES (default, 'PHP');
INSERT INTO topic VALUES (default, 'C#');

INSERT INTO user VALUES (default, 'admin@gmail.com', '21232f297a57a5a743894a0e4a801fc3', 'Andriy', 'Adminov', 0, 0);
INSERT INTO user VALUES (default, 'teacher@gmail.com', '8d788385431273d11e8b43bb78f3aa41', 'Alan', 'Dzhavaev', 1, 0);
INSERT INTO user VALUES (default, 'teacher1@gmail.com', '41c8949aa55b8cb5dbec662f34b62df3', 'Alex', 'Pitonov', 1, 0);
INSERT INTO user VALUES (default, 'teacher2@gmail.com', 'ccffb0bb993eeb79059b31e1611ec353', 'Oleh', 'Skriptovich', 1, 0);
INSERT INTO user VALUES (default, 'teacher3@gmail.com', '82470256ea4b80343b27afccbca1015b', 'Dmytro', 'Sisharpko', 1, 0);
INSERT INTO user VALUES (default, 'teacher4@gmail.com', '93dacda950b1dd917079440788af3321', 'Ihor', 'Zminniy', 1, 0);
INSERT INTO user VALUES (default, 'teacher5@gmail.com', 'ea105f0d381e790cdadc6a41eb611c77', 'Konstantin', 'Void', 1, 0);
INSERT INTO user VALUES (default, 'teacher6@gmail.com', 'ff1643afb67a6edb36ee3f6fea756323', 'Anatoliy', 'Varko', 1, 0);
INSERT INTO user VALUES (default, 'teacher7@gmail.com', '71e0f8d7d61b45e27b57c62eb8684583', 'Anton', 'Serverun', 1, 0);
INSERT INTO user VALUES (default, 'teacher8@gmail.com', 'ee1079e7de417c403b87932ea235dab7', 'Valeriy', 'Mapiuk', 1, 0);
INSERT INTO user VALUES (default, 'student@gmail.com', 'cd73502828457d15655bbd7a63fb0bc8', 'Serhiy', 'Studentin', 2, 0);
INSERT INTO user VALUES (default, 'student1@gmail.com', '5e5545d38a68148a2d5bd5ec9a89e327', 'Vladimir', 'Volodko', 2, 0);
INSERT INTO user VALUES (default, 'student2@gmail.com', '213ee683360d88249109c2f92789dbc3', 'Denis', 'Denisov', 2, 0);
INSERT INTO user VALUES (default, 'student3@gmail.com', '8e4947690532bc44a8e41e9fb365b76a', 'Oleksandr', 'Sashko', 2, 0);
INSERT INTO user VALUES (default, 'student4@gmail.com', '166a50c910e390d922db4696e4c7747b', 'Oleksa', 'Makedon', 2, 0);
INSERT INTO user VALUES (default, 'student5@gmail.com', '9fd9280a7aa3578c8e853745a5fcc18a', 'Ivan', 'Gonchar', 2, 0);
INSERT INTO user VALUES (default, 'student6@gmail.com', '27e062bf3df59edebb5db9f89952c8b3', 'Vasil', 'Ivasiuk', 2, 0);
INSERT INTO user VALUES (default, 'student7@gmail.com', '72e8744fc2faa17a83dec9bed06b8b65', 'Pavlo', 'Systema', 2, 0);
INSERT INTO user VALUES (default, 'student8@gmail.com', '8aa7fb36a4efbbf019332b4677b528cf', 'Oleksiy', 'Listyn', 2, 0);
INSERT INTO user VALUES (default, 'student9@gmail.com', '7c8cd5da17441ff04bf445736964dd16', 'Maksym', 'Tredak', 2, 0);

INSERT INTO course VALUES (default, 'Beginner Java course', '2022-06-01', '2022-08-30', 90, 1, 2, 'The beginners course that will provide theoretical knowledge and practical skills with Java for further growth');
INSERT INTO course VALUES (default, 'Basic Java course', '2022-07-01', '2022-11-30', 152, 1, 2, 'A basic course that will help you master java core perfectly');
INSERT INTO course VALUES (default, 'Advanced Java course', '2022-06-10', '2022-12-10', 183, 1, 2, 'An advanced course that will help you master Java, Maven/Gradle, Spring, Kafka, PostgreSQL, Docker, Hibernate and more');
INSERT INTO course VALUES (default, 'Senior Java course', '2022-08-01', '2023-01-25', 177, 1, 2, 'The seniors course that will give you enough knowledge and practice to get a job as a trainee/junior java developer');
INSERT INTO course VALUES (default, 'Beginner JavaScript course', '2022-09-01', '2022-11-10', 70, 2, 4, 'The beginners course that will provide theoretical knowledge and practical skills with JavaScript for further growth');
INSERT INTO course VALUES (default, 'Basic JavaScript course', '2022-10-10', '2023-01-30', 112, 2, 4, 'A basic course that will help you master JS core features perfectly');
INSERT INTO course VALUES (default, 'Advanced JavaScript course', '2022-09-10', '2023-01-15', 127, 2, 4, 'An advanced course that will help you master JavaScript, CSS, HTML and more');
INSERT INTO course VALUES (default, 'Senior JavaScript course', '2022-09-20', '2023-02-20', 153, 2, 4, 'The seniors course that will give you enough knowledge and practice to get a job as a trainee/junior js developer');
INSERT INTO course VALUES (default, 'Beginner Python course', '2022-10-05', '2022-12-25', 81, 3, 3, 'The beginners course that will provide theoretical knowledge and practical skills with Python for further growth');
INSERT INTO course VALUES (default, 'Basic Python course', '2022-10-20', '2023-01-30', 102, 3, 3, 'A basic course that will help you master Python core features perfectly');
INSERT INTO course VALUES (default, 'Advanced Python course', '2022-11-01', '2023-03-01', 120, 3, 3, 'An advanced course that will help you master Python, Flask, Django and more');
INSERT INTO course VALUES (default, 'Senior Python course', '2022-11-01', '2023-03-31', 150, 3, 3, 'The seniors course that will give you enough knowledge and practice to get a job as a trainee/junior python developer');
INSERT INTO course VALUES (default, 'Beginner PHP course', '2023-03-05', '2023-06-05', 92, 4, 6, 'The beginners course that will provide theoretical knowledge and practical skills with PHP for further growth');
INSERT INTO course VALUES (default, 'Basic PHP course', '2022-11-10', '2023-02-25', 107, 4, 7, 'A basic course that will help you master PHP core features perfectly');
INSERT INTO course VALUES (default, 'Advanced PHP course', '2023-02-25', '2023-06-25', 120, 4, 8, 'An advanced course that will help you master PHP, Apache, NGINX, Symfony, Twig and more');
INSERT INTO course VALUES (default, 'Senior PHP course', '2022-12-01', '2023-05-05', 155, 4, 9, 'The seniors course that will give you enough knowledge and practice to get a job as a trainee/junior php developer');
INSERT INTO course VALUES (default, 'Beginner C# course', '2022-12-05', '2023-02-05', 62, 5, 5, 'The beginners course that will provide theoretical knowledge and practical skills with C# for further growth');
INSERT INTO course VALUES (default, 'Basic C# course', '2022-12-12', '2023-02-20', 70, 5, 5, 'A basic course that will help you master C# core features perfectly');
INSERT INTO course VALUES (default, 'Advanced C# course', '2022-09-30', '2023-01-30', 122, 5, 5, 'An advanced course that will help you master C#, .Net Core, .Net Framework, ASP.NET and more');
INSERT INTO course VALUES (default, 'Senior C# course', '2022-09-15', '2023-03-20', 186, 5, 5, 'The seniors course that will give you enough knowledge and practice to get a job as a trainee/junior c# developer');
INSERT INTO course VALUES (default, 'Початковий Java курс', '2022-09-01', '2022-12-01', 91, 1, 2, 'Це початковий курс, який дасть теоретичні знання та практичні навички з Java для розвитку далі');
INSERT INTO course VALUES (default, 'Поглиблений Java курс', '2022-12-11', '2023-06-10', 181, 1, 2, 'Це поглиблений курс, який допоможе досконало освоїти Java, Maven/Gradle, Spring, Kafka, PostgreSQL, Docker, Hibernate та інше');
INSERT INTO course VALUES (default, 'Базовий PHP курс', '2022-12-01', '2023-03-15', 104, 4, 7, 'Це базовий курс, який допоможе досконало освоїти ключові функції PHP');
INSERT INTO course VALUES (default, 'Вищий PHP курс', '2023-01-10', '2023-06-15', 156, 4, 9, 'Це вищий курс, який дасть достатню кількість знань та практики, аби працевлаштуватись на позицію молодшого php розробника');
INSERT INTO course VALUES (default, 'Базовий Python курс', '2023-03-10', '2023-06-25', 107, 3, 3, 'Це базовий курс, який допоможе досконало освоїти ключові функції Python');
INSERT INTO course VALUES (default, 'Поглиблений Python курс', '2022-12-01', '2023-04-01', 121, 3, 3, 'Це поглиблений курс, який допоможе досконало освоїти Python, Flask, Django та інше');
INSERT INTO course VALUES (default, 'Початковий JavaScript курс', '2023-02-20', '2023-04-30', 69, 2, 4, 'Це початковий курс, який дасть теоретичні знання та практичні навички з JavaScript для розвитку далі');
INSERT INTO course VALUES (default, 'Базовий JavaScript курс', '2023-03-01', '2023-06-20', 111, 2, 4, 'Це базовий курс, який допоможе досконало освоїти ключові функції JavaScript');
INSERT INTO course VALUES (default, 'Базовий C# курс', '2022-08-01', '2022-10-20', 80, 5, 5, 'Це базовий курс, який допоможе досконало освоїти ключові особливості C#');
INSERT INTO course VALUES (default, 'Поглиблений C# курс', '2022-08-10', '2023-02-05', 179, 5, 5, 'Це поглиблений курс, який допоможе досконало освоїти C#, .Net Core, .Net Framework, ASP.NET та інше');

INSERT INTO course_student VALUES (1, 11, 85);
INSERT INTO course_student VALUES (2, 11, 81);
INSERT INTO course_student VALUES (3, 11, 70);
INSERT INTO course_student VALUES (4, 11, 73);
INSERT INTO course_student VALUES (5, 11, 93);
INSERT INTO course_student VALUES (6, 11, default);
INSERT INTO course_student VALUES (7, 11, 89);
INSERT INTO course_student VALUES (8, 11, default);
INSERT INTO course_student VALUES (9, 11, 78);
INSERT INTO course_student VALUES (10, 11, default);
INSERT INTO course_student VALUES (11, 11, default);
INSERT INTO course_student VALUES (12, 11, default);
INSERT INTO course_student VALUES (13, 11, default);
INSERT INTO course_student VALUES (14, 11, default);
INSERT INTO course_student VALUES (15, 11, default);
INSERT INTO course_student VALUES (16, 11, default);
INSERT INTO course_student VALUES (17, 11, default);
INSERT INTO course_student VALUES (18, 11, default);
INSERT INTO course_student VALUES (19, 11, default);
INSERT INTO course_student VALUES (20, 11, default);
INSERT INTO course_student VALUES (21, 11, 86);
INSERT INTO course_student VALUES (22, 11, default);
INSERT INTO course_student VALUES (23, 11, default);
INSERT INTO course_student VALUES (24, 11, default);
INSERT INTO course_student VALUES (25, 11, default);
INSERT INTO course_student VALUES (26, 11, default);
INSERT INTO course_student VALUES (27, 11, default);
INSERT INTO course_student VALUES (28, 11, default);
INSERT INTO course_student VALUES (29, 11, 95);
INSERT INTO course_student VALUES (30, 11, default);

INSERT INTO course_student VALUES (1, 12, 88);
INSERT INTO course_student VALUES (3, 12, 79);
INSERT INTO course_student VALUES (4, 12, 64);
INSERT INTO course_student VALUES (2, 13, 91);
INSERT INTO course_student VALUES (3, 13, 78);
INSERT INTO course_student VALUES (4, 13, 85);
INSERT INTO course_student VALUES (1, 14, 86);
INSERT INTO course_student VALUES (3, 14, 84);
INSERT INTO course_student VALUES (4, 14, 77);
INSERT INTO course_student VALUES (2, 15, 59);
INSERT INTO course_student VALUES (3, 15, 69);
INSERT INTO course_student VALUES (4, 15, 90);
INSERT INTO course_student VALUES (1, 16, 97);
INSERT INTO course_student VALUES (2, 16, 78);
INSERT INTO course_student VALUES (3, 16, 93);
INSERT INTO course_student VALUES (2, 17, 65);
INSERT INTO course_student VALUES (3, 17, 79);
INSERT INTO course_student VALUES (4, 17, 96);
INSERT INTO course_student VALUES (2, 18, 77);
INSERT INTO course_student VALUES (3, 18, 85);
INSERT INTO course_student VALUES (2, 19, 94);
INSERT INTO course_student VALUES (3, 19, 81);
INSERT INTO course_student VALUES (2, 20, 89);
INSERT INTO course_student VALUES (3, 20, 92);
