package com.workintech.s19d2.repository;

//Bu MemberRepository interface'i, Member entity'si için veritabanı erişimini sağlar.
// JpaRepository sayesinde temel CRUD işlemleri hazır gelirken,
// @Query anotasyonu ile e-posta adresine göre üye arama gibi özel bir işlevi de ekledik.
// Bu metot, özellikle Spring Security ile kullanıcı doğrulama (kullanıcı adına göre kullanıcı bulma) sürecinde kritik










// Gerekli entity sınıfı importu
import com.workintech.s19d2.entity.Member;
// Spring Data JPA'dan JpaRepository ve Query importları
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional; // NullPointerException riskini azaltmak için Optional importu

// MemberRepository interface'i, Spring Data JPA'nın JpaRepository interface'inden miras alır.
// <Member, Long>:
// - Member: Bu repository'nin yöneteceği entity sınıfı (Member tablosuyla etkileşim kuracak).
// - Long: Member entity'sinin birincil anahtarının (id) veri tipi.
// JpaRepository, temel CRUD operasyonlarını otomatik olarak sağlar.
public interface MemberRepository extends JpaRepository<Member, Long> {

    // @Query anotasyonu, özel bir sorgu tanımlamak için kullanılır.
    // "SELECT m FROM Member m WHERE m.email=:email": JPQL (Java Persistence Query Language) sorgusu.
    // JPQL, SQL'e benzer ancak doğrudan veritabanı tabloları yerine JPA entity'leri üzerinde çalışır.
    // m.email=:email ifadesi, metot parametresi olarak gelen 'email' değerinin sorgudaki ':email' parametresine bağlanacağını belirtir.
    // Optional<Member>: Metot, bir Member nesnesi döndürmek yerine, null olabilecek durumlarda
    // NullPointerException riskini azaltmak için bir Optional kapsayıcısı içinde döndürür.
    // Bu, aranan e-postaya sahip bir üye bulunamazsa boş bir Optional döneceği anlamına gelir.
    Optional<Member> findByEmail(String email); // E-posta adresine göre üye arayan özel metot
}