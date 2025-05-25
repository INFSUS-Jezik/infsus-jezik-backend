Ovo je backend za DZ03 domaću zadaću iz kolegija INFSUS (Informacijski sustavi) na FER-u.

---

## Tehnologije korištene

- Java 21 
- Spring Boot 
- Maven 
- PostgreSQL (u produkciji)
- H2 baza (lokalni development)

---

## Preduvjeti

Prije pokretanja projekta, treba imati instalirano:

- **Java 21**
- **Maven 3.9+**
- **Docker** (za pokretanje aplikacije s PostgreSQL bazom podataka)

## Kako buildati i pokrenuti projekt


### 1. Kloniraj repozitorij 

- git clone https://github.com/INFSUS-Jezik/infsus-jezik-backend.git
- cd infsus-jezik-backend

### 2. Build projekta
mvn clean install

### 3. Pokretanje aplikacije 
a) s PostgreSQL bazom podataka
  - docker-compose up
  - mvn spring-boot:run -Dspring-boot.run.profiles=prod

b) s H2 bazom podataka
  - mvn spring-boot:run -Dspring-boot.run.profiles=dev

### 4. Pokretanje testova
- za pokretanje testova: mvn test

  
