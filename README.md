# Spring Boot Web アプリケーション

オーソドックスなSpring Bootを使用したWebアプリケーションです。ユーザー管理機能を備えたシンプルなCRUDアプリケーションです。

## 機能

- **ユーザー管理**: ユーザーの登録、一覧表示、編集、削除
- **バリデーション**: Bean Validationを使用したデータ検証
- **データベース**: H2インメモリデータベース（開発用）
- **UI**: Bootstrap 5とFont Awesomeを使用したモダンなインターフェース
- **テンプレートエンジン**: Thymeleaf

## 技術スタック

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: データベースアクセス
- **H2 Database**: インメモリデータベース
- **Thymeleaf**: テンプレートエンジン
- **Bootstrap**: 5.3.0
- **Font Awesome**: 6.0.0
- **Maven**: ビルドツール

## セットアップ

### 前提条件

- Java 17以上
- Maven 3.6以上

### インストール手順

1. リポジトリをクローン
```bash
git clone <repository-url>
cd spring-web-app
```

2. アプリケーションをビルド
```bash
mvn clean install
```

3. アプリケーションを起動
```bash
mvn spring-boot:run
```

4. ブラウザでアクセス
```
http://localhost:8080
```

## 使用方法

### ホームページ
- アプリケーションの概要と機能説明
- クイックアクションリンク

### ユーザー管理
- **ユーザー一覧**: `/users`
  - 登録されているユーザーの一覧表示
  - 編集・削除ボタン
- **新規ユーザー登録**: `/users/new`
  - 新しいユーザーの登録フォーム
- **ユーザー編集**: `/users/{id}/edit`
  - 既存ユーザーの情報編集

### H2コンソール（開発用）
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- ユーザー名: `sa`
- パスワード: `password`

## プロジェクト構造

```
src/
├── main/
│   ├── java/
│   │   └── com/example/springwebapp/
│   │       ├── controller/
│   │       │   ├── HomeController.java
│   │       │   └── UserController.java
│   │       ├── entity/
│   │       │   └── User.java
│   │       ├── repository/
│   │       │   └── UserRepository.java
│   │       ├── service/
│   │       │   └── UserService.java
│   │       └── SpringWebAppApplication.java
│   └── resources/
│       ├── templates/
│       │   ├── home.html
│       │   ├── layout.html
│       │   └── users/
│       │       ├── form.html
│       │       └── list.html
│       ├── application.properties
│       └── data.sql
└── test/
    └── java/
        └── com/example/springwebapp/
            └── SpringWebAppApplicationTests.java
```

## API エンドポイント

| メソッド | URL | 説明 |
|---------|-----|------|
| GET | `/` | ホームページ |
| GET | `/users` | ユーザー一覧 |
| GET | `/users/new` | 新規ユーザー登録フォーム |
| POST | `/users` | ユーザー登録 |
| GET | `/users/{id}/edit` | ユーザー編集フォーム |
| POST | `/users/{id}` | ユーザー更新 |
| POST | `/users/{id}/delete` | ユーザー削除 |

## バリデーション

- **名前**: 必須、2-50文字
- **メールアドレス**: 必須、有効なメール形式、重複不可
- **パスワード**: 必須、6文字以上

## 開発

### ホットリロード
Spring Boot DevToolsが有効になっているため、コードの変更は自動的にリロードされます。

### ログ
- SQLクエリのログ出力が有効
- Spring Webのデバッグログが有効

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。 