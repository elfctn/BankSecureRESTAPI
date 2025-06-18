package com.workintech.s19d2.service;

// Account entity'sinin importu
import com.workintech.s19d2.entity.Account;

import java.util.List; // List interface'inin importu

// AccountService interface'i, Account entity'si ile ilgili iş mantığı operasyonlarını tanımlar.
// Bu arayüz, servis katmanının sözleşmesini (contract) belirler ve bağımlılık enjeksiyonu (Dependency Injection) için önemlidir.
// Bu sayede, AccountService'in farklı implementasyonları (örneğin test amaçlı mock implementasyonlar) kolayca sağlanabilir.
public interface AccountService {
    // Tüm Account nesnelerini liste olarak döndüren metot.
    List<Account> findAll();

    // Verilen Account nesnesini veritabanına kaydeden veya güncelleyen metot.
    // Eğer Account nesnesinin ID'si yoksa yeni bir kayıt oluşturur, varsa mevcut kaydı günceller.
    Account save(Account account);

    // Not: Proje gereksinimlerinde Account için CRUD operasyonlarının tamamı istenmişti.
    // Şu an burada sadece findAll ve save metotları mevcut.
    // [GET]/workintech/accounts/{id} => İlgili id deki account objesini dönmeli. (findById metodu eksik)
    // [PUT]/workintech/accounts/{id} => İlgili id deki account objesinin değerlerini yeni gelen data ile değiştirir. (save metodu ile yapılabilir ancak özel bir update metodu da eklenebilir)
    // [DELETE]/workintech/accounts/{id} => İlgili id değerindeki account objesini veritabanından siler. (delete metodu eksik)
    // Bu metotları da eklememiz gerekecek.
}
// AccountService interface'i ve AccountServiceImpl implementasyonu
// proje gereksinimlerinde belirtilen tüm CRUD metotlarını
// (özellikle findById ve delete) içermiyor.
// Bu kısımları daha sonra eklememiz gerekecek.