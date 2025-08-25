# 🎯 Sigorta Poliçe Yönetim Sistemi - Swagger UI Entegrasyonu

## 🚀 Swagger UI Dokümantasyonu

Tüm API'lerin interaktif dokümantasyonu artık mevcut!

### 📊 API Dokümantasyonu Erişim Linkleri:

- **🏠 User Service Swagger UI**: http://localhost:8081/swagger-ui.html
- **📋 Policy Service Swagger UI**: http://localhost:8082/swagger-ui.html
- **⚡ API Gateway Health**: http://localhost:8080/actuator/health
- **🔍 Eureka Dashboard**: http://localhost:8761

## 🎨 Swagger UI Özellikleri

### ✨ Mevcut Özellikler:
- **📖 Interaktif API Dokümantasyonu**: Tüm endpoint'ler için detaylı açıklamalar
- **🧪 Test Arabirimu**: Direkt tarayıcıdan API testleri
- **📝 Schema Tanımları**: Request/Response modelleri
- **🔧 Parametre Validasyonu**: Gerekli alanlar ve formatlar
- **📊 Response Örnekleri**: Başarılı ve hata durumları
- **🏷️ Türkçe Açıklamalar**: Kullanıcı dostu dokümantasyon

### 🎯 API Grupları:
1. **User Management**: Kullanıcı yönetimi işlemleri
2. **Policy Management**: Sigorta poliçesi yönetimi işlemleri

## 🚀 Servisleri Çalıştırma

```bash
# 1. Eureka Server
cd backend/eureka-server
mvn spring-boot:run

# 2. User Service
cd backend/user-service
mvn spring-boot:run

# 3. Policy Service  
cd backend/policy-service
mvn spring-boot:run

# 4. API Gateway
cd backend/api-gateway
mvn spring-boot:run
```

## 📱 Swagger UI Kullanımı

### 1. User Service Swagger (Port: 8081)
- **URL**: http://localhost:8081/swagger-ui.html
- **Endpoint'ler**: Kullanıcı CRUD işlemleri
- **Özellikler**: Email ile arama, kullanıcı doğrulama

### 2. Policy Service Swagger (Port: 8082)
- **URL**: http://localhost:8082/swagger-ui.html
- **Endpoint'ler**: Poliçe CRUD işlemleri, durum güncelleme
- **Özellikler**: Kullanıcı bazlı sorgulama, OpenFeign entegrasyonu

## 🎯 Test Senaryoları

### 📝 Kullanıcı Oluşturma Testi:
1. User Service Swagger'a git
2. POST /users endpoint'ini genişlet
3. "Try it out" butonuna tıkla
4. Örnek JSON'u düzenle:
```json
{
  "email": "test@example.com",
  "firstName": "Test",
  "lastName": "User",
  "password": "password123",
  "phoneNumber": "+905551234567",
  "address": "İstanbul, Türkiye"
}
```
5. "Execute" butonuna tıkla

### 📋 Poliçe Oluşturma Testi:
1. Policy Service Swagger'a git
2. POST /policies endpoint'ini genişlet
3. "Try it out" butonuna tıkla
4. Örnek JSON'u düzenle:
```json
{
  "userId": 1,
  "type": "HEALTH",
  "coverageAmount": 100000,
  "premium": 1200,
  "startDate": "2024-01-01",
  "endDate": "2024-12-31",
  "description": "Sağlık Sigortası"
}
```
5. "Execute" butonuna tıkla

## 🔧 Teknik Detaylar

### 📦 Kullanılan Teknolojiler:
- **SpringDoc OpenAPI 3**: Modern API dokümantasyonu
- **Swagger UI**: İnteraktif API arayüzü
- **Spring Boot 3.x**: Framework
- **OpenFeign**: Mikroservis iletişimi

### 🎨 Özelleştirmeler:
- Türkçe açıklamalar
- Örneklerle zenginleştirilmiş parametreler
- HTTP status kodları ve açıklamaları
- Validasyon kuralları
- Schema tanımları

## 🌟 Gelişmiş Özellikler

### 🔍 API Keşfi:
- Tüm endpoint'ler otomatik olarak keşfediliyor
- Model şemaları otomatik oluşturuluyor
- Request/Response örnekleri mevcut

### ⚡ Performans:
- Lazy loading ile hızlı başlatma
- Minimal memory footprint
- Production-ready konfigürasyon

Bu entegrasyon ile artık API'lerinizi görsel olarak keşfedebilir ve test edebilirsiniz! 🎉