package com.workintech.s19d2.controller;

// Gerekli entity, service ve Spring Web importları
import com.workintech.s19d2.entity.Account;
import com.workintech.s19d2.service.AccountService;
import lombok.AllArgsConstructor; // Tüm argümanları içeren bir yapıcı metot oluşturur
import org.springframework.web.bind.annotation.*; // RESTful API anotasyonları için

import java.util.List; // List importu

// @AllArgsConstructor: Lombok anotasyonu, tüm 'final' alanları (AccountService) için bir yapıcı metot otomatik olarak oluşturur.
// @RestController: Bu sınıfın bir REST kontrolcüsü olduğunu ve metotlarının doğrudan HTTP yanıtı döndüreceğini belirtir.
// @RequestMapping("/account"): Bu kontrolcüdeki tüm endpoint'lerin temel yolunu ("/account") belirtir.
// Proje gereksinimlerinde "/workintech/accounts" şeklinde bir yol belirtilmişti. Burası "/account" olarak belirlenmiş.
// Bu durum, Controller'daki @RequestMapping veya SecurityConfig'deki URL matcher'ları ile uyumlu hale getirilmelidir.
@AllArgsConstructor
@RestController
@RequestMapping("/account") // Base URL for account related operations
public class AccountController {
    // AccountService bağımlılığının enjekte edilmesi. İş mantığı katmanına erişmek için kullanılır.
    private final AccountService accountService;

    // HTTP GET isteği için endpoint. Temel URL: /account (e.g., GET /account)
    // Tüm Account nesnelerini liste olarak döndürür.
    // Bu endpoint'e "ADMIN" veya "USER" rolüne sahip kullanıcılar erişebilir (SecurityConfig'e göre).
    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll(); // Servis katmanından tüm hesapları alır
    }

    // HTTP POST isteği için endpoint. Temel URL: /account (e.g., POST /account)
    // İstemciden gelen istek gövdesindeki (request body) Account nesnesini veritabanına kaydeder.
    // @RequestBody: Gelen JSON/XML verisini Account nesnesine dönüştürmesini sağlar.
    // Bu endpoint'e sadece "ADMIN" rolüne sahip kullanıcılar erişebilir (SecurityConfig'e göre).
    @PostMapping
    public Account save(@RequestBody Account account) {
        return accountService.save(account); // Servis katmanı aracılığıyla hesabı kaydeder
    }

    // Not: Proje gereksinimlerinde Account için şu CRUD metotları da istenmişti:
    // [GET]/workintech/accounts/{id} => İlgili id deki account objesini dönmeli.
    // [PUT]/workintech/accounts/{id} => İlgili id deki account objesinin değerlerini yeni gelen data ile değiştirir.
    // [DELETE]/workintech/accounts/{id} => İlgili id değerindeki account objesini veritabanından siler.
    // Bu metotların bu kontrolcüye ve AccountService'e eklenmesi gerekmektedir.
}



//AccountController'da eksik olan CRUD metotları (findById, update ve delete) bulunmaktadır.
// Bu metotların hem AccountService'e hem de bu kontrolcüye eklenmesi gerekmektedir.
// Proje gereksinimlerine göre /workintech/accounts path'i belirtilmesine rağmen,
// controller @RequestMapping("/account") kullanıyor.
// Bu, ya dökümantasyonun güncellenmesi ya da
// controller mapping'inin @RequestMapping("/workintech/accounts") olarak değiştirilmesi gerektiğini gösterir.
// Güvenlik yapılandırması /account/** yollarını kullanıyorsa, mevcut controller mapping'iyle uyumludur.