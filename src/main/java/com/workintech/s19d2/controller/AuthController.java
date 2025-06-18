package com.workintech.s19d2.controller;

// Gerekli DTO, entity, service ve Spring Web importları
import com.workintech.s19d2.dto.RegisterResponse; // Kayıt yanıt DTO'su
import com.workintech.s19d2.dto.RegistrationMember; // Kayıt isteği DTO'su
import com.workintech.s19d2.entity.Member; // Member entity'si
import com.workintech.s19d2.service.AuthenticationService; // Kimlik doğrulama servisimiz
import lombok.AllArgsConstructor; // Tüm argümanları içeren bir yapıcı metot oluşturur
import org.springframework.web.bind.annotation.PostMapping; // HTTP POST isteği için
import org.springframework.web.bind.annotation.RequestBody; // İstek gövdesini nesneye bağlamak için
import org.springframework.web.bind.annotation.RequestMapping; // Temel URL belirlemek için
import org.springframework.web.bind.annotation.RestController; // REST kontrolcüsü olduğunu belirtir

// @AllArgsConstructor: Lombok anotasyonu, tüm 'final' alanları (AuthenticationService) için bir yapıcı metot otomatik olarak oluşturur.
// @RestController: Bu sınıfın bir REST kontrolcüsü olduğunu ve metotlarının doğrudan HTTP yanıtı döndüreceğini belirtir.
// @RequestMapping("/auth"): Bu kontrolcüdeki tüm endpoint'lerin temel yolunu ("/auth") belirtir.
// Proje gereksinimlerinde "/workintech/auth/register" şeklinde bir yol belirtilmişti. Bu yapılandırmayla uyumludur.
@AllArgsConstructor
@RestController
@RequestMapping("/auth") // Base URL for authentication operations
public class AuthController {

    // AuthenticationService bağımlılığının enjekte edilmesi. Kimlik doğrulama iş mantığına erişmek için kullanılır.
    private final AuthenticationService authenticationService;

    // HTTP POST isteği için endpoint. Tam URL: /auth/register (e.g., POST /auth/register)
    // Yeni bir kullanıcı kaydı (register) işlemini gerçekleştirir.
    // @RequestBody RegistrationMember registrationMember: İstemciden gelen JSON/XML verisini RegistrationMember nesnesine dönüştürür.
    // Bu endpoint'e kimlik doğrulaması olmadan erişilebilir (SecurityConfig'e göre permitAll).
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegistrationMember registrationMember) {
        // AuthenticationService üzerinden üye kayıt işlemini çağırır.
        Member createdMember = authenticationService.register(registrationMember.email(), registrationMember.password());
        // Kayıt başarılı olduğunda RegisterResponse DTO'sunu oluşturur ve istemciye döner.
        return new RegisterResponse(createdMember.getEmail(),"kayıt başarılı bir şekilde gerçekleşti.");
    }

    // Not: Normalde bir kimlik doğrulama kontrolcüsü içinde sadece kayıt değil,
    // giriş (login) gibi başka metotlar da bulunabilir. Ancak proje sadece register istemiş.
}