package com.workintech.s19d2.entity;


//Bu Member sınıfı, Spring Security ile veritabanı tabanlı (JDBC) kimlik doğrulama için temel kullanıcı varlığını temsil ediyor.
// UserDetails arayüzünü uygulayarak Spring Security'nin kullanıcı bilgilerine erişmesini sağlar.



// Gerekli JPA (Java Persistence API) ve Lombok kütüphanelerinden importlar
import jakarta.persistence.*;
import lombok.Data; // Getter, Setter, toString, equals ve hashCode metotlarını otomatik oluşturur
import lombok.NoArgsConstructor; // Argümansız yapıcı metodu (constructor) otomatik oluşturur
import org.springframework.security.core.GrantedAuthority; // Spring Security yetkilendirme arayüzü
import org.springframework.security.core.userdetails.UserDetails; // Spring Security kullanıcı detayları arayüzü

import java.util.ArrayList; // Dinamik liste için import
import java.util.Collection; // Koleksiyon arayüzü
import java.util.List; // Liste arayüzü

// @Data: Lombok anotasyonu, boilerplate kodu (getter, setter, vb.) otomatik üretir.
// @NoArgsConstructor: Lombok anotasyonu, varsayılan bir argümansız yapıcı metot oluşturur.
// @Entity: Bu sınıfın bir JPA varlığı olduğunu ve veritabanında bir tabloya eşleneceğini belirtir.
// @Table: Varlığın eşleneceği veritabanı tablosunun adını ve şemasını belirtir.
// name = "member": Tablonun veritabanındaki adı.
// schema = "bank": Tablonun ait olduğu veritabanı şeması. Proje gereksinimlerine göre 'bank' şeması altında tanımlanmıştır.
@Data
@NoArgsConstructor
@Entity
@Table(name = "member", schema = "bank")
public class Member implements UserDetails { // Spring Security ile entegrasyon için UserDetails arayüzünü implement eder

    // @Id: Bu alanın birincil anahtar (Primary Key) olduğunu belirtir.
    // @GeneratedValue: Birincil anahtarın nasıl oluşturulacağını belirtir.
    // strategy = GenerationType.IDENTITY: Veritabanının kimlik sütununu otomatik olarak artırmasını sağlar (MySQL, PostgreSQL için uygundur).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Üyenin benzersiz kimliği

    // @Column: Alanın veritabanı sütununa eşlendiğini belirtir. name özelliği, sütun adını açıkça tanımlar.
    @Column(name = "email")
    private String email; // Üyenin e-posta adresi (genellikle kullanıcı adı olarak kullanılır)

    @Column(name = "password")
    private String password; // Üyenin şifresi (Spring Security tarafından şifrelenerek saklanmalıdır)

    // @ManyToMany: Member ve Role sınıfları arasında Many-to-Many (Çoktan Çoka) ilişkiyi tanımlar.
    // fetch = FetchType.EAGER: İlişkili Role nesnelerinin Member yüklendiğinde hemen (eagerly) yüklenmesini sağlar.
    // @JoinTable: İlişkinin, 'member_role' adlı ayrı bir birleştirme tablosu aracılığıyla yönetileceğini belirtir.
    // name = "member_role": Birleştirme tablosunun adı.
    // schema = "bank": Birleştirme tablosunun ait olduğu veritabanı şeması.
    // joinColumns: Bu taraftaki (Member) varlığın birleştirme tablosundaki sütununu belirtir.
    // @JoinColumn(name = "member_id"): 'member_role' tablosunda 'member' tablosunun anahtarını tutan sütun adı.
    // inverseJoinColumns: Karşı taraftaki (Role) varlığın birleştirme tablosundaki sütununu belirtir.
    // @JoinColumn(name = "role_id"): 'member_role' tablosunda 'role' tablosunun anahtarını tutan sütun adı.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_role", schema = "bank",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>(); // Üyenin sahip olduğu rolleri tutan liste

    // UserDetails arayüzünden gelen metotların implementasyonları:

    // Kullanıcının yetkilerini (rollerini) döndürür. Spring Security bu metodu kullanarak kullanıcının hangi rollere sahip olduğunu belirler.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles; // Member'ın sahip olduğu Role nesneleri GrantedAuthority olarak döner.
    }

    // Kullanıcının benzersiz kullanıcı adını döndürür. Bu projede e-posta adresi kullanıcı adı olarak kullanılır.
    @Override
    public String getUsername() {
        return email;
    }

    // Kullanıcı hesabının süresi dolup dolmadığını belirtir. true döndürülmesi hesabın süresinin dolmadığı anlamına gelir.
    @Override
    public boolean isAccountNonExpired() {
        return true; // Hesap süresinin dolmadığını varsayıyoruz.
    }

    // Kullanıcı hesabının kilitli olup olmadığını belirtir. true döndürülmesi hesabın kilitli olmadığı anlamına gelir.
    @Override
    public boolean isAccountNonLocked() {
        return true; // Hesap kilitli değil varsayıyoruz.
    }

    // Kullanıcı kimlik bilgilerinin (şifre gibi) süresi dolup dolmadığını belirtir. true döndürülmesi kimlik bilgilerinin süresinin dolmadığı anlamına gelir.
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Kimlik bilgilerinin süresinin dolmadığını varsayıyoruz.
    }

    // Kullanıcının etkin (enabled) olup olmadığını belirtir. true döndürülmesi kullanıcının giriş yapabileceği anlamına gelir.
    @Override
    public boolean isEnabled() {
        return true; // Kullanıcının etkin olduğunu varsayıyoruz.
    }
}