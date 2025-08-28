# TC Kimlik Numarası (TCKN) Implementasyonu

Bu dokument, User Service'e eklenen TC Kimlik Numarası doğrulama özelliğini açıklamaktadır.

## Özellikler

### 1. TCKN Doğrulama
- TC Kimlik Numarası algoritmasına göre doğrulama
- 11 hane kontrolü
- İlk hane 0 olamaz kontrolü
- Tüm haneler aynı olamaz kontrolü
- Checksum algoritması kontrolü

### 2. Veritabanı Değişiklikleri
- `User` entity'sine `tckn` alanı eklendi
- TCKN alanı unique ve not null olarak tanımlandı
- Database migration gerekebilir

### 3. API Endpoints

#### Yeni Endpoint
- `GET /users/tckn/{tckn}` - TCKN ile kullanıcı arama

#### Güncellenen Endpoints
- `POST /users` - Artık TCKN alanı zorunlu
- `PUT /users/{id}` - TCKN güncellemesi desteklenir

### 4. DTO Güncellemeleri
- `CreateUserDTO` - TCKN alanı eklendi
- `UserDTO` - TCKN alanı eklendi (response'larda görünür)

### 5. Validation Kuralları
- TCKN formatı ve algoritması doğrulanır
- Duplicate TCKN kontrolü yapılır
- TCKN boş olamaz

## TCKN Algoritması

Türk TC Kimlik Numarası aşağıdaki kurallara uyar:
1. 11 hane olmalıdır
2. İlk hane 0 olamaz
3. Tüm haneler aynı olamaz
4. İlk 10 hanenin toplamının 10'a bölümünden kalan = 11. hane
5. (1+3+5+7+9. hanelerin toplamı × 7) - (2+4+6+8. hanelerin toplamı) mod 10 = 10. hane

## Kullanım Örnekleri

### Kullanıcı Oluşturma
```json
POST /users
{
  "email": "test@example.com",
  "firstName": "Ahmet",
  "lastName": "Yılmaz", 
  "password": "password123",
  "phoneNumber": "+905551234567",
  "tckn": "12345678901",
  "address": "İstanbul, Türkiye"
}
```

### TCKN ile Kullanıcı Arama
```
GET /users/tckn/12345678901
```

## Test

TCKN doğrulama için unit testler `TCKNValidatorTest` sınıfında bulunmaktadır.

## Dikkat Edilmesi Gerekenler

1. Mevcut veritabanındaki kullanıcılar için TCKN alanı eklenmelidir
2. Frontend uygulamasında TCKN alanı eklenmeli ve validation yapılmalıdır
3. Policy Service'deki UserDTO da güncellendi
4. Production'a geçmeden önce database migration scripti hazırlanmalıdır
