package tr.org.lkd.lyk2015.camp.service;

public interface EmailService {

	// email yollayan service hoca kodunu jar olarak verecek ondan kodu varm��
	// gibi d���n�yoruz

	public boolean sendEmail(String to, String subject, String content);

}
