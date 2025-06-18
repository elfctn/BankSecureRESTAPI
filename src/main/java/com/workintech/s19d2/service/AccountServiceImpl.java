package com.workintech.s19d2.service;

// Gerekli entity ve repository importları
import com.workintech.s19d2.entity.Account;
import com.workintech.s19d2.repository.AccountRepository;
// Lombok ve Spring anotasyonları
import lombok.AllArgsConstructor; // Tüm argümanları içeren bir yapıcı metot oluşturur
import org.springframework.stereotype.Service; // Bu sınıfın bir servis bileşeni olduğunu belirtir

import java.util.List; // List importu

// @AllArgsConstructor: Lombok anotasyonu, tüm 'final' alanları (AccountRepository) için bir yapıcı metot otomatik olarak oluşturur.
// Bu, bağımlılık enjeksiyonu için tercih edilen yöntemdir (Constructor Injection).
// @Service: Spring anotasyonu, bu sınıfın bir iş mantığı servisi olduğunu ve Spring konteyneri tarafından yönetileceğini belirtir.
// Bu sınıf, AccountService interface'inin implementasyonudur.
@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    // AccountRepository bağımlılığının enjekte edilmesi. 'final' keyword'ü, bu alanın constructor ile initialize edileceğini garanti eder.
    private final AccountRepository accountRepository;

    // AccountService interface'inden implement edilen findAll metodu.
    // Tüm Account nesnelerini veritabanından getirir. Repository katmanındaki findAll metodunu çağırır.
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // AccountService interface'inden implement edilen save metodu.
    // Verilen Account nesnesini veritabanına kaydeder veya günceller. Repository katmanındaki save metodunu çağırır.
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    // Not: Proje gereksinimlerinde Account için CRUD operasyonlarının tamamı istenmişti.
    // Bu implementasyonda şu an için findById, update ve delete metotları eksik.
    // Bunları da eklememiz gerekecektir.
}