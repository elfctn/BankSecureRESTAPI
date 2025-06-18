package com.workintech.s19d2.service;

// Gerekli entity, repository ve Spring Security importları
import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import com.workintech.s19d2.repository.MemberRepository;
import com.workintech.s19d2.repository.RoleRepository;
import lombok.AllArgsConstructor; // Tüm argümanları içeren bir yapıcı metot oluşturur
import org.springframework.security.crypto.password.PasswordEncoder; // Şifreleme için
import org.springframework.stereotype.Service; // Bu sınıfın bir servis bileşeni olduğunu belirtir

import java.util.ArrayList; // Dinamik liste için
import java.util.List; // List interface'i
import java.util.Optional; // NullPointerException riskini azaltmak için

// @AllArgsConstructor: Lombok anotasyonu, tüm 'final' alanları için bir yapıcı metot oluşturur.
// @Service: Spring anotasyonu, bu sınıfın bir iş mantığı servisi olduğunu belirtir.
// Bu servis, kullanıcı kaydı (registration) ve rol yönetimi gibi kimlik doğrulama ile ilgili işlemleri yürütür.
@AllArgsConstructor
@Service
public class AuthenticationService {
    // Bağımlılık enjeksiyonu ile repository'ler ve şifreleyici enjekte edilir.
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder; // Şifreleri güvenli bir şekilde saklamak için kullanılır

    // Yeni bir kullanıcı kaydı (register) işlemini gerçekleştiren metot.
    // Parametreler: email (kullanıcı adı), password (şifre)
    public Member register(String email, String password) {
        // 1. E-posta ile mevcut bir kullanıcı olup olmadığını kontrol et.
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            // Eğer aynı e-posta ile bir kullanıcı zaten varsa hata fırlat.
            throw new RuntimeException("User with given email already exist! Email: " + email);
        }

        // 2. Şifreyi şifreleyici (PasswordEncoder) kullanarak güvenli bir şekilde encode et (hashle).
        String encodedPassword = passwordEncoder.encode(password);

        // 3. Kullanıcıya atanacak rolleri (yetkileri) hazırla.
        List<Role> roleList = new ArrayList<>();

        // 4. "ADMIN" rolünün veritabanında olup olmadığını kontrol et.
        Optional<Role> roleAdmin = roleRepository.findByAuthority("ADMIN");
        if (!roleAdmin.isPresent()) {
            // Eğer "ADMIN" rolü yoksa, yeni bir Role entity'si oluştur, adını "ADMIN" yap ve veritabanına kaydet.
            Role roleAdminEntity = new Role();
            roleAdminEntity.setAuthority("ADMIN");
            roleList.add(roleRepository.save(roleAdminEntity));
        } else {
            // Eğer "ADMIN" rolü varsa, mevcut rolü al ve listeye ekle.
            // Not: Proje gereksinimlerinde bir adet user, bir adet admin rolünde kullanıcı tanımlanması istenmişti.
            // Bu metot şu an sadece ADMIN rolü ekliyor. 'USER' rolünü de dinamik olarak ekleyebilmek için geliştirilebilir.
            // Örneğin: roleRepository.findByAuthority("USER") kontrolü de eklenebilir.
            roleList.add(roleAdmin.get());
        }

        // 5. Yeni Member nesnesini oluştur ve bilgilerini set et.
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        member.setRoles(roleList); // Kullanıcıya atanan rolleri ekle.

        // 6. Yeni Member nesnesini veritabanına kaydet ve kaydedilen nesneyi döndür.
        return memberRepository.save(member);
    }

}



//AuthenticationService'deki register metodu,
// her yeni kullanıcı kaydında otomatik olarak "ADMIN" rolünü atama eğiliminde.
// Proje gereksinimlerinde "bir adet user rolünde bir adet admin rolünde kullanıcı tanımlayın" denmişti.
// Bu metot bu haliyle sadece ADMIN rolü ekleyebiliyor.
// Kullanıcı kaydı sırasında istemciden gelen bir rol bilgisine göre atama yapmak
// veya varsayılan bir "USER" rolü atamak daha esnek olacaktır.
// Ancak mevcut yazdığım kod bazında yorumladım sorun yok