package com.workintech.s19d2.dto;


//Bu RegisterResponse record'u, kullanıcı kayıt işlemi (registration) tamamlandığında
// API'nin istemciye döneceği veri yapısını temsil ediyor.
// Özellikle AuthController sınıfındaki register metodunun dönüş tipi olarak kullanılacak.




// Kayıt işlemi sonrası API'den dönülecek yanıtın yapısını tanımlayan bir record.
// Record'lar, Java 16 ile birlikte tanıtılan, değişmez (immutable) veri taşıyıcı sınıflar oluşturmak için
// kısa ve öz bir sözdizimi sunar. Otomatik olarak constructor, getter'lar, equals(), hashCode() ve toString() metotlarını sağlar.
// Bu DTO (Data Transfer Object), kullanıcı kayıt işlemi başarılı olduğunda istemciye
// dönülecek bilgileri içerir.
public record RegisterResponse(
        String email, // Kayıt işlemi yapılan kullanıcının e-posta adresi (kullanıcı adı)
        String message // İşlem sonucunu belirten bir mesaj (örn: "Kayıt başarılı", "Kullanıcı oluşturuldu")
) {
    // Record'lar için özel bir metot veya ek bir constructor tanımlanmadığı sürece
    // içi boş kalabilir. Derleyici gerekli yapıyı otomatik olarak oluşturur.
}