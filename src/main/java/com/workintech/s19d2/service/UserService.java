package com.workintech.s19d2.service;

// Gerekli repository ve Spring Security importları
import com.workintech.s19d2.repository.MemberRepository;
import lombok.AllArgsConstructor; // Tüm argümanları içeren bir yapıcı metot oluşturur
import org.springframework.security.core.userdetails.UserDetails; // Kullanıcı detayları arayüzü
import org.springframework.security.core.userdetails.UserDetailsService; // Kullanıcı detayları servisi arayüzü
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Kullanıcı bulunamadığında fırlatılan istisna
import org.springframework.stereotype.Service; // Bu sınıfın bir servis bileşeni olduğunu belirtir

// @AllArgsConstructor: Lombok anotasyonu, tüm 'final' alanları (MemberRepository) için bir yapıcı metot oluşturur.
// @Service: Spring anotasyonu, bu sınıfın bir iş mantığı servisi olduğunu belirtir.
// UserService sınıfı, Spring Security'nin UserDetailsService arayüzünü implement eder.
// Bu arayüz, Spring Security'nin kimlik doğrulama sürecinde kullanıcı bilgilerini yüklemesi için kullanılır.
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    // MemberRepository bağımlılığının enjekte edilmesi. Kullanıcı bilgilerini veritabanından almak için kullanılır.
    private final MemberRepository memberRepository;

    // UserDetailsService arayüzünden implement edilen loadUserByUsername metodu.
    // Spring Security, kimlik doğrulama sırasında bu metodu çağırarak verilen kullanıcı adına (username) sahip
    // UserDetails nesnesini ister.
    // Parametre: username (bu projede kullanıcının e-posta adresi)
    // Dönüş Tipi: UserDetails (Spring Security'nin kullanıcının kimlik ve yetki bilgilerini içeren nesnesi)
    // Hata Durumu: Eğer kullanıcı bulunamazsa UsernameNotFoundException fırlatır.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // memberRepository.findByEmail(username) metodu ile veritabanından kullanıcıyı e-posta adresine göre arar.
        // .orElseThrow(): Eğer Optional boşsa (yani kullanıcı bulunamazsa) belirtilen istisnayı fırlatır.
        // new UsernameNotFoundException("Member is not valid!"): Kullanıcı bulunamadığında fırlatılacak özel hata mesajı.
        return memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member is not valid!"));
    }
}