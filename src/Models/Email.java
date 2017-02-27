/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Diego Jacobs
 */
public class Email {
    private int EmailId;
    private String From;
    private String To;
    private String labelFrom;
    private ArrayList<String> labelTo;
    private ArrayList<String> Cc;
    private ArrayList<String> Bcc;
    private Date Date;
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

    public Email(String From, String To, String labelFrom, ArrayList<String> labelTo, ArrayList<String> Cc, ArrayList<String> Bcc, String Subject, String Content) {
        this.From = From;
        this.To = To;
        this.labelFrom = labelFrom;
        this.labelTo = labelTo;
        this.Cc = Cc;
        this.Bcc = Bcc;
        this.Subject = Subject;
        this.Content = Content;
    }

    public Email(int EmailId, String From, String To, String labelFrom, ArrayList<String> labelTo, ArrayList<String> Cc, ArrayList<String> Bcc, Date Date, String Subject, String Content) {
        this.EmailId = EmailId;
        this.From = From;
        this.To = To;
        this.labelFrom = labelFrom;
        this.labelTo = labelTo;
        this.Cc = Cc;
        this.Bcc = Bcc;
        this.Date = Date;
        this.Subject = Subject;
        this.Content = Content;
    }
    
    public Email(int EmailId, String From, String To, String labelFrom, ArrayList<String> labelTo, ArrayList<String> Cc, ArrayList<String> Bcc, Date Date, String Subject, String Content, ArrayList<Attachment> Attachments) {
        this.EmailId = EmailId;
        this.From = From;
        this.To = To;
        this.labelFrom = labelFrom;
        this.labelTo = labelTo;
        this.Cc = Cc;
        this.Bcc = Bcc;
        this.Date = Date;
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

    public String getLabelFrom() {
        return labelFrom;
    }

    public void setLabelFrom(String labelFrom) {
        this.labelFrom = labelFrom;
    }

    public ArrayList<String> getLabelTo() {
        return labelTo;
    }

    public void setLabelTo(ArrayList<String> labelTo) {
        this.labelTo = labelTo;
    }
    
    public void setLabelTo(String to) {
        this.labelTo.add(to);
    }

    public ArrayList<String> getCc() {
        return Cc;
    }

    public void setCc(ArrayList<String> Cc) {
        this.Cc = Cc;
    }
    
    public void setCc(String Cc) {
        this.Cc.add(Cc);
    }

    public ArrayList<String> getBcc() {
        return Bcc;
    }

    public void setBcc(ArrayList<String> Bcc) {
        this.Bcc = Bcc;
    }
    
    public void setBcc(String Bcc) {
        this.Bcc.add(Bcc);
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    @Override
    public String toString() {
        return "Email{" + "EmailId=" + EmailId + ", From=" + From + ", To=" + To + ", labelFrom=" + labelFrom + ", labelTo=" + labelTo + ", Cc=" + Cc + ", Bcc=" + Bcc + ", Date=" + Date + ", Subject=" + Subject + ", Content=" + Content + ", Attachments=" + Attachments + '}';
    }
}
