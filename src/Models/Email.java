/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;

/**
 *
 * @author Diego Jacobs
 */
public class Email {
    private int EmailId;
    private String From;
    private String To;
    private String Subject;
    private String Content;
    private ArrayList<Attachment> Attachments;

    public Email() {
    }
    
    public Email(String From, String To, String Subject, String Content) {
        this.From = From;
        this.To = To;
        this.Subject = Subject;
        this.Content = Content;
    }
    
    public Email(int EmailId, String From, String To, String Subject, String Content) {
        this.EmailId = EmailId;
        this.From = From;
        this.To = To;
        this.Subject = Subject;
        this.Content = Content;
    }

    public Email(String From, String To, String Subject, String Content, ArrayList<Attachment> Attachments) {
        this.From = From;
        this.To = To;
        this.Subject = Subject;
        this.Content = Content;
        this.Attachments = Attachments;
    }
    
    public Email(int EmailId, String From, String To, String Subject, String Content, ArrayList<Attachment> Attachments) {
        this.EmailId = EmailId;
        this.From = From;
        this.To = To;
        this.Subject = Subject;
        this.Content = Content;
        this.Attachments = Attachments;
    }
    
    public int getEmailId() {
        return EmailId;
    }

    public void setEmailId(int EmailId) {
        this.EmailId = EmailId;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String To) {
        this.To = To;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public ArrayList<Attachment> getAttachments() {
        return Attachments;
    }

    public void setAttachments(ArrayList<Attachment> Attachments) {
        this.Attachments = Attachments;
    }
}
