# in-pohovor
# Application to store courts, surfaceTypes, reservations, customers.

# Main url: http://localhost:8080/api/
# H2 in-memory DB: http://localhost:8080/h2-console/
- JDBC URL: jdbc:h2:mem:tennis-db
- User Name: sa
- Password: 
# Swagger docs: http://localhost:8080/docs

# All tennis clubs
- http://localhost:8080/api/tennis-club
# By id
- http://localhost:8080/api/tennis-club/1


# All courts
- http://localhost:8080/api/court
# By id
- http://localhost:8080/api/court/1
# Reservations by id of the court
- http://localhost:8080/api/court/1/reservation


# Reservations by phone number
- http://localhost:8080/api/reservation/phone-number/123456789
# Reservations by id
- http://localhost:8080/api/reservation/id/1