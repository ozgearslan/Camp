package tr.org.lkd.lyk2015.camp.service;

public interface EmailService {

	// email yollayan service hoca kodunu jar olarak verecek ondan kodu varmýþ
	// gibi düþünüyoruz

	public boolean sendEmail(String to, String subject, String content);

}
