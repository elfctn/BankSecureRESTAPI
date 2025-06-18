package com.workintech.s19d2.repository;


//Bu AccountRepository interface'i, Account entity'si için veritabanı erişim katmanını sağlar.
// Spring Data JPA sayesinde, temel CRUD işlemleri otomatik olarak sağlanır
// elle bir implementasyon yazma zahmetinden kurtuluruz.



// Gerekli entity sınıfı importu
import com.workintech.s19d2.entity.Account;
// Spring Data JPA'dan JpaRepository importu
import org.springframework.data.jpa.repository.JpaRepository;

// AccountRepository interface'i, Spring Data JPA'nın JpaRepository interface'inden miras alır.
// <Account, Long>:
// - Account: Bu repository'nin yöneteceği entity sınıfı (Account tablosuyla etkileşim kuracak).
// - Long: Account entity'sinin birincil anahtarının (id) veri tipi.
// JpaRepository, CRUD (Create, Read, Update, Delete) operasyonları gibi temel veritabanı işlemlerini
// otomatik olarak sağlayan bir dizi metot sunar (örn: save, findById, findAll, delete).
// Bu sayede Account entity'si için özel bir implementasyon yazmamıza gerek kalmaz.
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Şu an için özel bir sorgu metoduna ihtiyaç duyulmadığından, bu interface içi boştur.
    // Ancak gelecekte özel sorgular (örneğin, isme göre hesap bulma) eklenebilir:
    // List<Account> findByName(String name);
}