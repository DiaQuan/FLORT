# Flört

Basit bir ilişki notu uygulaması. Partnerinin ismini, yaşını, sevdiği özellikleri
ve unutulmaması gereken şeyleri (+ fotoğrafını) kaydet.

## Nasıl açılır
1. Android Studio'yu aç (Hedgehog veya üstü önerilir).
2. "Open" ile bu klasörü (Flort) seç.
3. Gradle senkronizasyonunun bitmesini bekle (ilk açılışta internet gerekir,
   bağımlılıklar indirilecek).
4. Bir emülatör veya gerçek cihaz seç, Run (▶) butonuna bas.

## Yapı
- `app/src/main/java/com/example/flort/data` → Room veritabanı (Partner, Dao, Database, ViewModel)
- `app/src/main/java/com/example/flort/ui` → Compose ekranları (Liste, Ekle)
- `MainActivity.kt` → Navigasyon (liste ekranı ↔ ekle ekranı)

## GitHub'da otomatik APK derleme
Proje `.github/workflows/build.yml` içeriyor. Yapman gerekenler:

1. GitHub'da yeni bir repo oluştur (public veya private).
2. Bu klasörün tüm içeriğini o repoya push et:
   ```
   git init
   git add .
   git commit -m "ilk commit"
   git branch -M main
   git remote add origin <REPO_URL>
   git push -u origin main
   ```
3. Push işleminden sonra GitHub'da repo sayfasında **Actions** sekmesine git.
4. "Android APK Build" workflow'unun çalıştığını göreceksin (birkaç dakika sürer).
5. Çalışma bitince workflow sayfasının altında **Artifacts** bölümünden
   `flort-debug-apk` dosyasını indirebilirsin — içinde `app-debug.apk` var.
6. Bu APK'yı telefonuna kopyalayıp kurabilirsin (bilinmeyen kaynaklardan
   yükleme izni gerekebilir).

Workflow her `main`/`master` branch'e push'ta ve elle tetiklemede
(Actions sekmesinde "Run workflow") otomatik çalışır.
