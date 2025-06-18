package com.workintech.s19d2.config;

//Spring Security yapılandırmasının merkezi bir parçasıdır.


// Gerekli Spring Security ve diğer Spring Core importları
import org.springframework.context.annotation.Bean; // Spring bileşenlerini (bean'lerini) tanımlamak için
import org.springframework.context.annotation.Configuration; // Bu sınıfın bir Spring yapılandırma sınıfı olduğunu belirtir
import org.springframework.http.HttpMethod; // HTTP metotlarını (GET, POST vb.) kullanmak için
import org.springframework.security.authentication.AuthenticationManager; // Kimlik doğrulama yöneticisi
import org.springframework.security.authentication.ProviderManager; // AuthenticationManager implementasyonu
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // DAO tabanlı kimlik doğrulama sağlayıcısı
import org.springframework.security.config.Customizer; // Spring Security config customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // HTTP güvenlik yapılandırması için ana sınıf
import org.springframework.security.core.userdetails.UserDetailsService; // Kullanıcı detayları servisi arayüzü
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // BCrypt şifreleme algoritması
import org.springframework.security.crypto.password.PasswordEncoder; // Şifreleme arayüzü
import org.springframework.security.web.SecurityFilterChain; // Güvenlik filtresi zinciri

// @Configuration: Spring'e bu sınıfın bean tanımları içerdiğini ve uygulamanın yapılandırması için kullanılması gerektiğini bildirir.
@Configuration
public class SecurityConfig {

    // @Bean: Bu metodun döndürdüğü nesnenin bir Spring Bean'i olarak Spring konteynerine kaydedilmesini sağlar.
    // PasswordEncoder, şifreleri güvenli bir şekilde hashlemek ve doğrulamak için kullanılır.
    // BCryptPasswordEncoder yaygın ve güvenli bir şifreleme algoritmasıdır.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean: Bu metodun döndürdüğü AuthenticationManager nesnesi, kimlik doğrulama sürecini yönetir.
    // UserDetailsService: Kullanıcı detaylarını (üyeleri) veritabanından yüklemek için kullanılır (UserService sınıfımız).
    // DaoAuthenticationProvider: Veritabanı (DAO) tabanlı kimlik doğrulama için bir sağlayıcıdır.
    // Kendi UserDetailsService implementasyonumuzu (UserService) ve PasswordEncoder'ımızı kullanır.
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService); // Kendi UserService'imizi set ederiz
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder); // Kendi PasswordEncoder'ımızı set ederiz
        return new ProviderManager(daoAuthenticationProvider); // Bir veya daha fazla sağlayıcıyı yöneten manager
    }

    // @Bean: Bu metodun döndürdüğü SecurityFilterChain, HTTP istekleri için güvenlik kurallarını tanımlar.
    // HttpSecurity: HTTP tabanlı güvenlik yapılandırması için bir builder arayüzü.
    // throws Exception: Yapılandırma sırasında oluşabilecek istisnaları belirtir.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable()) // CSRF (Cross-Site Request Forgery) korumasını devre dışı bırakır.
                // Genellikle REST API'lerde stateless yapılar tercih edildiği için devre dışı bırakılır.
                .authorizeHttpRequests(auth -> { // HTTP istekleri için yetkilendirme kurallarını tanımlar
                    // "/auth/**" (kayıt, giriş gibi) ve "/welcome/**" endpoint'lerine kimlik doğrulaması olmadan erişime izin verir.
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/welcome/**").permitAll();
                    // "/actuator/**" endpoint'lerine (Spring Boot Actuator) kimlik doğrulaması olmadan erişime izin verir.
                    auth.requestMatchers("/actuator/**").permitAll();

                    // HTTP GET metodu ile gelen "/account/**" isteklerine "ADMIN" veya "USER" rolüne sahip kullanıcıların erişimine izin verir.
                    auth.requestMatchers(HttpMethod.GET, "/account/**")
                            .hasAnyAuthority("ADMIN", "USER");

                    // HTTP POST metodu ile gelen "/account/**" isteklerine sadece "ADMIN" rolüne sahip kullanıcıların erişimine izin verir.
                    // Bu, yeni hesap oluşturma işlemleri için geçerlidir.
                    auth.requestMatchers(HttpMethod.POST, "/account/**").hasAuthority("ADMIN");

                    // HTTP PUT metodu ile gelen "/account/**" isteklerine sadece "ADMIN" rolüne sahip kullanıcıların erişimine izin verir.
                    // Bu, mevcut hesapları güncelleme işlemleri için geçerlidir.
                    auth.requestMatchers(HttpMethod.PUT, "/account/**").hasAuthority("ADMIN");

                    // HTTP DELETE metodu ile gelen "/account/**" isteklerine sadece "ADMIN" rolüne sahip kullanıcıların erişimine izin verir.
                    // Bu, hesap silme işlemleri için geçerlidir.
                    auth.requestMatchers(HttpMethod.DELETE, "/account/**").hasAuthority("ADMIN");

                    // Yukarıdaki kurallarla eşleşmeyen diğer tüm isteklere kimlik doğrulaması (authenticated) gerektirir.
                    auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults()) // Varsayılan form tabanlı giriş yapılandırmasını etkinleştirir (tarayıcıdan test için).
                .httpBasic(Customizer.withDefaults()) // Varsayılan HTTP Basic kimlik doğrulamasını etkinleştirir (Postman vb. araçlarla test için).
                .build(); // SecurityFilterChain nesnesini oluşturur ve döndürür.
    }

}