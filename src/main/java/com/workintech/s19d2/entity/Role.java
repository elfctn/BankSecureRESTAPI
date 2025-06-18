package com.workintech.s19d2.entity;



//Bu Role sınıfı, Spring Security'deki yetkilendirme mekanizmasının temelini oluşturur.
// GrantedAuthority arayüzünü implement ederek, bir Member'ın sahip olabileceği farklı yetki seviyelerini (rolleri) tanımlamamızı sağlar.



// Gerekli JPA ve Lombok kütüphanelerinden importlar
import jakarta.persistence.*;
import lombok.Data; // Getter, Setter, toString, equals ve hashCode metotlarını otomatik oluşturur
import lombok.NoArgsConstructor; // Argümansız yapıcı metodu (constructor) otomatik oluşturur
import org.springframework.security.core.GrantedAuthority; // Spring Security yetkilendirme arayüzü

// @Data: Lombok anotasyonu, boilerplate kodu (getter, setter, vb.) otomatik üretir.
// @NoArgsConstructor: Lombok anotasyonu, varsayılan bir argümansız yapıcı metot oluşturur.
// @Entity: Bu sınıfın bir JPA varlığı olduğunu ve veritabanında bir tabloya eşleneceğini belirtir.
// @Table: Varlığın eşleneceği veritabanı tablosunun adını ve şemasını belirtir.
// name = "role": Tablonun veritabanındaki adı.
// schema = "bank": Tablonun ait olduğu veritabanı şeması. Proje gereksinimlerine göre 'bank' şeması altında tanımlanmıştır.
@Data
@NoArgsConstructor
@Entity
@Table(name = "role", schema = "bank")
public class Role implements GrantedAuthority { // Spring Security ile entegrasyon için GrantedAuthority arayüzünü implement eder

    // @Id: Bu alanın birincil anahtar (Primary Key) olduğunu belirtir.
    // @GeneratedValue: Birincil anahtarın nasıl oluşturulacağını belirtir.
    // strategy = GenerationType.IDENTITY: Veritabanının kimlik sütununu otomatik olarak artırmasını sağlar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Rolün benzersiz kimliği

    // @Column: Alanın veritabanı sütununa eşlendiğini belirtir. name özelliği, sütun adını açıkça tanımlar.
    // authority: Rolün adını tutar (örn: "ADMIN", "USER"). Spring Security bu değeri yetkilendirme için kullanır.
    @Column(name = "authority")
    private String authority; // Rolün adı veya yetki bilgisi

    // GrantedAuthority arayüzünden gelen metot:
    // Bu metot, rolün yetki dizesini döndürür. Spring Security bu metodu kullanarak kullanıcının hangi yetkilere sahip olduğunu kontrol eder.
    @Override
    public String getAuthority() {
        return authority; // Rolün string temsilini döndürür (örneğin "ADMIN", "USER").
    }
}