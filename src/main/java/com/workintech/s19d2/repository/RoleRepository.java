package com.workintech.s19d2.repository;



//Bu RoleRepository interface'i, Role entity'si için veritabanı erişimini sağlar.
// JpaRepository temel CRUD işlemlerini sağlarken,
// @Query anotasyonu ile rol adına (authority) göre rol arama gibi özel bir işlevi de ekledik.
// Bu metot, özellikle yeni bir kullanıcı kaydedilirken veya mevcut bir kullanıcıya rol atanırken belirli rolleri veritabanından bulmak için kullanılacaktır.



// Gerekli entity sınıfı importu
import com.workintech.s19d2.entity.Role;
// Spring Data JPA'dan JpaRepository ve Query importları
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional; // NullPointerException riskini azaltmak için Optional importu

// RoleRepository interface'i, Spring Data JPA'nın JpaRepository interface'inden miras alır.
// <Role, Long>:
// - Role: Bu repository'nin yöneteceği entity sınıfı (Role tablosuyla etkileşim kuracak).
// - Long: Role entity'sinin birincil anahtarının (id) veri tipi.
// JpaRepository, temel CRUD operasyonlarını otomatik olarak sağlar.
public interface RoleRepository extends JpaRepository<Role, Long> {

    // @Query anotasyonu, özel bir sorgu tanımlamak için kullanılır.
    // "SELECT r FROM Role r WHERE r.authority=:authority": JPQL (Java Persistence Query Language) sorgusu.
    // Metot parametresi olarak gelen 'authority' değeri sorgudaki ':authority' parametresine bağlanır.
    // Optional<Role>: Metot, null olabilecek durumlarda NullPointerException riskini azaltmak için
    // bir Optional kapsayıcısı içinde döndürür. Bu, aranan yetkiye sahip bir rol bulunamazsa boş bir Optional döneceği anlamına gelir.
    Optional<Role> findByAuthority(String authority); // Rol adına (authority) göre rol arayan özel metot
}