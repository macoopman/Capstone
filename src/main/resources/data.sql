

-- Security Roles
INSERT INTO role (id, role_name, description) VALUES (1, "ROLE_ADMIN", "Admin role");
INSERT INTO role (id, role_name, description) VALUES (2, "ROLE_USER", "User role");

-- Users
INSERT INTO security_user(id, username, password, user_data_id) VALUES
    (1,'jbravo','password', 1);

--  '$2a$12$rSmJbpOVWHa9obhWFq8xiujx23LuMyvmjLvh00AyHhX70sN4H1KjK'  password
INSERT INTO security_user(id, username, password, user_data_id) VALUES
    (2,"rbobby","$2y$12$uElbfaG79Ta9SfX0H/HQYeeRXDx9/Xnol4EmWOz73W7nL6Vk8o7rS", 2);


INSERT INTO security_user(id, username, password, user_data_id) VALUES (3,"aeinstein", "password", 3);

-- Students
INSERT INTO student (id, first_name, last_name,  gpa, major, email) VALUES (1, "Johnny", "Bravo",  3.0, "Computer Science",  "bravo_jon@google.com");
INSERT INTO student (id, first_name, last_name,  gpa, major, email) VALUES (2, "Ricky", "Bobby",  4.5, "Cooking", "bobby_ricky@first.com");

-- Professors
INSERT INTO professor(id, first_name, last_name, rating, email) VALUES (3,"Albert", "Einstein", 3.1, "einstein_albert@yale.edu");

-- Assign security roles to users
INSERT INTO user_roles(user_id, roles_id) Values (1,1);
INSERT INTO user_roles(user_id, roles_id) Values (2,2);
INSERT INTO user_roles(user_id, roles_id) Values (3,1);
INSERT INTO user_roles(user_id, roles_id) Values (3,2);



INSERT INTO klass(id, subject) VALUES (1, "Computer Science");


INSERT INTO session(id, start_date, end_date, session_name) VALUES (1, "2019-01-15", "2019-02-15", "JAN_2019");
INSERT INTO session(id, start_date, end_date, session_name) VALUES (2, "2019-02-15", "2019-03-15", "FEB_2019");
INSERT INTO session(id, start_date, end_date, session_name) VALUES (3, "2019-04-15", "2019-05-15", "MAR_2019");

INSERT INTO klass_session_list(klass_id, session_list_id ) VALUES (1,1);
INSERT INTO klass_session_list(klass_id, session_list_id ) VALUES (1,2);
INSERT INTO klass_session_list(klass_id, session_list_id ) VALUES (1,3);








