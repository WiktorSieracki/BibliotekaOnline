
title,authors,categories,thumbnail,description,published_year,ratings_count,average_rating,num_pages
Gilead,Marilynne Robinson,Fiction,http://books.google.com/books/content?id=KQZCPgAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api,"A NOVEL THAT READERS and critics have been eagerly anticipating for over a decade, Gilead is an astonishingly imagined story of remarkable lives. John Ames is a preacher, the son of a preacher and the grandson (both maternal and paternal) of preachers. It’s 1956 in Gilead, Iowa, towards the end of the Reverend Ames’s life, and he is absorbed in recording his family’s story, a legacy for the young son he will never see grow up. Haunted by his grandfather’s presence, John tells of the rift between his grandfather and his father: the elder, an angry visionary who fought for the abolitionist cause, and his son, an ardent pacifist. He is troubled, too, by his prodigal namesake, Jack (John Ames) Boughton, his best friend’s lost son who returns to Gilead searching for forgiveness and redemption. Told in John Ames’s joyous, rambling voice that finds beauty, humour and truth in the smallest of life’s details, Gilead is a song of celebration and acceptance of the best and the worst the world has to offer. At its heart is a tale of the sacred bonds between fathers and sons, pitch-perfect in style and story, set to dazzle critics and readers alike.",2004,361,3.85,247

INSERT INTO author (name) VALUES ('Marilynne Robinson');

-- Insert data into the Book table
INSERT INTO book (title, categories, description, published_year, ratings_count, average_rating, num_pages)
VALUES ('Gilead', 'Fiction', 'A NOVEL THAT READERS and critics have been eagerly anticipating for over a decade...', '2004', 361, 3.85, 247);

-- Establish the relationship between the book and the author
INSERT INTO book_author (book_id, author_id)
VALUES (
           (SELECT id FROM book WHERE title = 'Gilead'),
           (SELECT id FROM author WHERE name = 'Marilynne Robinson')
       );

-- Insert data into the Author table
INSERT INTO author (name) VALUES ('Marilynne Robinson');
INSERT INTO author (name) VALUES ('Author 2');

-- Insert data into the Book table
INSERT INTO book (title, categories, description, published_year, ratings_count, average_rating, num_pages)
VALUES ('Book 1', 'Category 1', 'Description 1', '2020', 10, 4.5, 300);
INSERT INTO book (title, categories, description, published_year, ratings_count, average_rating, num_pages)
VALUES ('Book 2', 'Category 2', 'Description 2', '2021', 20, 4.0, 250);

-- Insert data into the User table
INSERT INTO "user" (name, email, password) VALUES ('John Doe', 'john.doe@example.com', 'password123');
INSERT INTO "user" (name, email, password) VALUES ('Jane Smith', 'jane.smith@example.com', 'password456');

-- Insert data into the Comment table
INSERT INTO comment (user_id, book_id, created_at, text) VALUES (1, 1, '2023-01-01 10:00:00', 'Great book!');
INSERT INTO comment (user_id, book_id, created_at, text) VALUES (2, 2, '2023-01-02 11:00:00', 'Interesting read.');