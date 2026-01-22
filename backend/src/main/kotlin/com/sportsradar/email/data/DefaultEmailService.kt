package com.sportsradar.email.data

import jakarta.mail.Message
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.email.EmailBuilder

class DefaultEmailService(
    private val mailer: Mailer
) : EmailService {
    override suspend fun sendEmail(data: EmailData): Boolean {
        val userName = data.emailTo.split("@").getOrNull(0) ?: return false
        val email = EmailBuilder.startingBlank()
            .from("e.g username, verify, etc.", data.emailFrom)
            .to(userName, data.emailTo)
            .withSubject(data.subject)
            .withPlainText(data.message)
            .buildEmail()
        return try {
            mailer.sendMail(email)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun sendEmail2(data: EmailData): Boolean {
        //Get the session object
        val properties = System.getProperties()
        properties.setProperty("mail.smtp.host", "localhost")
        val session = Session.getDefaultInstance(properties)

        //compose the message
        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(data.emailFrom))
                addRecipient(Message.RecipientType.TO, InternetAddress(data.emailTo))
                subject = data.subject
                setText(data.message)
            }

            // Send message
            Transport.send(message)
            println("message sent successfully...")
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}