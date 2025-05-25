#INFSUS - Jezik Backend

Ovo je backend dio projekta **INFSUS - Jezik**, razvijen korištenjem **Spring Boot**, **Java 21**, i **Maven** kao build alatom. Aplikacija služi za upravljanje predmetima, profesorima, rasporedima i upisima.

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
- **Docker** (za pokretanje aplikacije s PostgreSQL bazom podataka

## Kako buildati i pokrenuti projekt


### 1. Kloniraj repozitorij 

git clone https://github.com/INFSUS-Jezik/infsus-jezik-frontend.git
cd infsus-jezik-backend

### 2. Build projekta
mvn clean install

### 3. Pokretanje aplikacije 
a) s PostgreSQL bazom podataka
  - docker-compose up
  - mvn spring-boot:run -Dspring-boot.run.profiles=prod

b) s H2 bazom podataka
  - mvn spring-boot:run -Dspring-boot.run.profiles=dev

  
