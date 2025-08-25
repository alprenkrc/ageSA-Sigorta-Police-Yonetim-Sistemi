@echo off
echo =======================================
echo  Sigorta Poliçe Yönetim Sistemi Test
echo =======================================
echo.

echo 1. Eureka Server Health Check...
curl -s http://localhost:8761/actuator/health
echo.
echo.

echo 2. User Service Health Check...
curl -s http://localhost:8081/actuator/health
echo.
echo.

echo 3. Policy Service Health Check...
curl -s http://localhost:8082/actuator/health
echo.
echo.

echo 4. API Gateway Health Check...
curl -s http://localhost:8080/actuator/health
echo.
echo.

echo 5. API Gateway Routes...
curl -s http://localhost:8080/actuator/gateway/routes
echo.
echo.

echo 6. Test User Creation...
curl -X POST http://localhost:8080/api/users ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"test@example.com\",\"firstName\":\"Test\",\"lastName\":\"User\",\"password\":\"password123\",\"phoneNumber\":\"+905551234567\",\"address\":\"İstanbul\"}"
echo.
echo.

echo 7. Get All Users...
curl -s http://localhost:8080/api/users
echo.
echo.

echo 8. Test Policy Creation...
curl -X POST http://localhost:8080/api/policies ^
  -H "Content-Type: application/json" ^
  -d "{\"userId\":1,\"type\":\"HEALTH\",\"coverageAmount\":100000,\"premium\":1200,\"startDate\":\"2024-01-01\",\"endDate\":\"2024-12-31\",\"description\":\"Test Sağlık Sigortası\"}"
echo.
echo.

echo 9. Get All Policies...
curl -s http://localhost:8080/api/policies
echo.
echo.

echo Test tamamlandı!
pause
