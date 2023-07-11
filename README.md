# Superhero Sightings Web Application

This Superhero Sightings Web Application is a comprehensive solution developed to track and manage superhero/supervillain sightings, hero information, location details, and superhero/supervillain organization data. It provides a user-friendly interface with features like creating, viewing, editing, and deleting superheroes, superpowers, locations, organizations, and superhero/supervillain sightings. The application is built using Spring Boot, following the MVC pattern and making efficient use of dependency injection.

## Key Features
- Manage superhero/supervillain information, including names, descriptions, and superpowers.
- Track superhero/supervillain affiliations with organizations.
- Maintain location details with names, descriptions, addresses, and latitude/longitude coordinates.
- Record superhero/supervillain sightings for specific locations and dates.
- Retrieve information on superheroes sighted at a particular location.
- Retrieve information on locations where a specific superhero has been seen.
- View sightings (hero and location) for a specific date.
- Access organization member details.
- Explore organizations to which a superhero/villain belongs.

## Deliverables
- Entity-Relationship Diagram (ERD) showcasing the database design, achieving at least 2nd normal form.
- Database creation script, including tables, columns, relationships, and re-runnable structure.
- DAO (Data Access Object) implementation and unit tests, covering all CRUD operations, data relationships, and transaction handling.
- Wireframes depicting all web application pages, with appropriate annotations for endpoints and parameters.
- Spring Boot Web Application implementation following MVC patterns, emphasizing dependency injection.

## Update (10-07-2023) - Image Support

Now, in addition to managing superhero information, you can also add an image for each superhero. The images will be stored in the MySQL database along with other superhero data.

To add an image for a superhero:
- When creating or editing a superhero, you can select an image file to upload.
- The uploaded image will be associated with the respective superhero and stored in the database.
- The image will be displayed alongside the superhero details in the application.

Please make sure to have the necessary database and application configurations set up as mentioned in the previous sections.

## Instructions to Run the App

### Prerequisites
- JDK 11 or higher
- MySQL (Make sure it's installed and running)

### Database Setup
1. Create a new MySQL database for the application.
2. Update the `application.properties` file located in the `src/main/resources` directory with your MySQL database connection details.

spring.datasource.url=jdbc:mysql://localhost:3306/{database_name}
spring.datasource.username={database_username}
spring.datasource.password={database_password}

### Running the Application
1. Clone the repository: `git clone https://github.com/AleksGalenko/Superhero.git`
2. Open the project in your preferred Java IDE.
3. Build and run the project using the IDE's tools or by running the main application class.
4. Access the application by navigating to `http://localhost:8080` in your web browser.

Make sure to have Java and MySQL installed and properly configured before running the project.

## Acknowledgements

- This project was completed as part of an assignment for the Java Fundamentals course by Wiley Edge..
- I would like to thank my mentors for their guidance and support throughout the development process.
