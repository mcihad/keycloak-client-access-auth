# Keycloak Client Access Authenticator

Bu proje, Keycloak için özel bir authenticator sağlar. Kullanıcıların belirli bir client uygulamasına erişim için gerekli role sahip olup olmadığını kontrol eder.

## Özellikler

- Dinamik rol kontrolü: Client ID'sine göre `{client-id}-access` rolünü kontrol eder.
- Türkçe hata mesajları: Kullanıcı dostu hata mesajları.
- Kolay entegrasyon: Keycloak'un SPI sistemi üzerinden çalışır.

## Gereksinimler

- Java 11 veya üzeri
- Maven 3.6+
- Keycloak 20+ (test edildiği sürüm)

## Kurulum

1. Projeyi klonlayın veya indirin.
2. Maven ile derleyin:
   ```
   mvn clean package
   ```
3. Oluşan JAR dosyasını (`target/keycloak-client-access-auth-1.0-SNAPSHOT.jar`) Keycloak'un `providers` klasörüne kopyalayın.
4. Keycloak'u yeniden başlatın.

## Yapılandırma

### Realm'de Rol Oluşturma

Her client için `{client-id}-access` adında bir rol oluşturun. Örneğin, client ID'si `my-app` ise `my-app-access` rolü oluşturun.

### Authenticator Ekleme

1. Keycloak Admin Console'da realm'inize gidin.
2. **Authentication** > **Flows** sekmesine gidin.
3. Mevcut bir flow'u düzenleyin veya yeni bir flow oluşturun.
4. **Add execution** ile "Role Based Authenticator" ekleyin.
5. Execution'ı **REQUIRED** olarak ayarlayın.

### Kullanıcılara Rol Atama

Kullanıcıların ilgili client'a erişimi için gerekli rolü atayın.

## Kullanım

Authenticator, kullanıcı girişi sırasında otomatik olarak çalışır:

- Kullanıcı gerekli role sahipse, giriş başarılı olur.
- Rol yoksa veya kullanıcı role sahip değilse, Türkçe hata mesajı gösterilir.

## Hata Mesajları

- **Rol bulunamadı**: "Uygulama için gerekli rol yoktur. Yönetici ile iletişime geçin. Gerekli Rol(Realm): {role}"
- **Erişim reddedildi**: "Uygulamaya erişim izniniz yok. Yönetici ile iletişime geçin. Gerekli Rol(Realm): {role}"

## Geliştirme

Kod değişiklikleri yaptıktan sonra:
```
mvn clean compile
```

Test için:
```
mvn test
```

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır.
