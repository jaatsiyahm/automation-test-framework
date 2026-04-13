
# Automation Test Framework

Framework untuk pengujian otomatis Web UI dan API menggunakan Java, Cucumber, dan Gradle.

## Tech Stack

| Teknologi | Versi | Fungsi |
|-----------|-------|--------|
| Java | 11 | Bahasa pemrograman |
| Gradle | 8.x | Build tool & task runner |
| Cucumber | 7.14.0 | BDD framework (Gherkin) |
| Selenium WebDriver | 4.15.0 | Web UI automation |
| REST Assured | 5.3.2 | API testing |
| JUnit | 4.13.2 | Test framework |
| WebDriverManager | 5.6.3 | Auto-download browser driver |


## Target Aplikasi

- **Web UI Test:** https://www.saucedemo.com
- **API Test:** https://dummyapi.io (App-ID: `63a804408eb0cb069b57e43a`)

## Struktur Project

```
src/test/
├── java/
│   ├── api/
│   │   ├── runners/ApiTestRunner.java
│   │   └── steps/UserApiSteps.java
│   └── web/
│       ├── pages/          ← Page Object Model
│       ├── runners/WebTestRunner.java
│       └── steps/SauceDemoSteps.java
└── resources/
└── features/
├── api/            ← Feature files API
└── web/            ← Feature files Web
```

## Cara Menjalankan Test

### Prasyarat
- Java 11 atau lebih baru
- Google Chrome (untuk Web test)
- Koneksi internet

### Menjalankan API Tests

```bash
./gradlew apiTest
```

### Menjalankan Web UI Tests

```bash
./gradlew webTest
```

### Menjalankan Web Tests dengan Mode Headless (tanpa GUI)

```bash
./gradlew webTest -Dheadless=true
```

### Menjalankan di Windows (gunakan `gradlew.bat`)

```bash
gradlew.bat apiTest
gradlew.bat webTest
```

## Melihat Report

Setelah test selesai, report tersedia di:
- **API Report:** `build/reports/cucumber/api/api-report.html`
- **Web Report:** `build/reports/cucumber/web/web-report.html`

Buka file HTML tersebut di browser untuk melihat hasil test.

## GitHub Actions

Workflow CI/CD ada di `.github/workflows/test.yml`.

**Cara trigger manual:**
1. Buka tab **Actions** di GitHub
2. Pilih workflow **"Automation Test Framework CI"**
3. Klik **"Run workflow"**
4. Pilih jenis test: `all`, `api`, atau `web`
5. Klik **"Run workflow"**

**Trigger otomatis:** Workflow akan berjalan otomatis setiap ada **Pull Request** ke branch `main`.
