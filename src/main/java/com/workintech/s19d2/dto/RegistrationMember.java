package com.workintech.s19d2.dto;


//Bu RegistrationMember record'u, yeni bir kullanıcı kaydederken
// istemciden beklenen veri yapısını temsil ediyor.
// Özellikle AuthController sınıfındaki register metodunun parametre tipi olarak kullanılacak.



// Kullanıcı kayıt (registration) işlemi için istemciden alınacak verilerin yapısını tanımlayan bir record.
// Bu record, yeni bir Member oluşturmak için gerekli olan bilgileri taşır.
// Record'lar, Java 16 ile tanıtılan, değişmez (immutable) veri taşıyıcı sınıflar oluşturmak için
// kısa ve öz bir sözdizimi sunar. Otomatik olarak constructor, getter'lar, equals(), hashCode() ve toString() metotlarını sağlar.
// Bu DTO (Data Transfer Object), AuthController sınıfında 'register' metodunun parametresi olarak kullanılacaktır.
public record RegistrationMember(
        String email,    // Kayıt olmak isteyen kullanıcının e-posta adresi (aynı zamanda kullanıcı adı olarak kullanılacak)
        String password  // Kayıt olmak isteyen kullanıcının belirlediği şifre
) {
    // Record'lar için özel bir metot veya ek bir constructor tanımlanmadığı sürece
    // içi boş kalabilir. Derleyici gerekli yapıyı otomatik olarak oluşturur.
}