package com.workintech.s19d2.entity;


//Bu Account sınıfı, bankacılık uygulamasındaki temel hesap varlığını temsil eder.
// Member ve Role sınıflarından farklı olarak, UserDetails veya GrantedAuthority gibi Spring Security arayüzlerini implement etmez,
// çünkü doğrudan kimlik doğrulama veya yetkilendirme süreçlerinde kullanılmaz.
// Daha çok uygulamanın iş mantığı katmanında kullanılacak basit bir veri varlığıdır.



// Gerekli JPA ve Lombok kütüphanelerinden importlar
import jakarta.persistence.*;
import lombok.Data; // Getter, Setter, toString, equals ve hashCode metotlarını otomatik oluşturur
import lombok.NoArgsConstructor; // Argümansız yapıcı metodu (constructor) otomatik oluşturur

// @Data: Lombok anotasyonu, boilerplate kodu (getter, setter, vb.) otomatik üretir.
// @NoArgsConstructor: Lombok anotasyonu, varsayılan bir argümansız yapıcı metot oluşturur.
// @Entity: Bu sınıfın bir JPA varlığı olduğunu ve veritabanında bir tabloya eşleneceğini belirtir.
// @Table: Varlığın eşleneceği veritabanı tablosunun adını ve şemasını belirtir.
// name = "account": Tablonun veritabanındaki adı.
// schema = "bank": Tablonun ait olduğu veritabanı şeması. Proje gereksinimlerine göre 'bank' şeması altında tanımlanmıştır.
@Data
@NoArgsConstructor
@Entity
@Table(name = "account", schema = "bank")
public class Account { // Banka hesabı varlığını temsil eden sınıf

    // @Id: Bu alanın birincil anahtar (Primary Key) olduğunu belirtir.
    // @GeneratedValue: Birincil anahtarın nasıl oluşturulacağını belirtir.
    // strategy = GenerationType.IDENTITY: Veritabanının kimlik sütununu otomatik olarak artırmasını sağlar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Hesabın benzersiz kimliği

    // @Column: Alanın veritabanı sütununa eşlendiğini belirtir. name özelliği, sütun adını açıkça tanımlar.
    @Column(name = "name")
    private String name; // Hesap adı (örn: "Vadesiz Hesap", "Tasrruf Hesabı" veya hesap sahibinin adı)
}