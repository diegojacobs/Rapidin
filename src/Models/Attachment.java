/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Diego Jacobs
 */
public class Attachment {
    private int AttachmentId;
    private int EmailId;
    private String FilePath;

    public Attachment() {
    }

    public Attachment(int EmailId, String FilePath) {
        this.EmailId = EmailId;
        this.FilePath = FilePath;
    }

    public Attachment(int AttachmentId, int EmailId, String FilePath) {
        this.AttachmentId = AttachmentId;
        this.EmailId = EmailId;
        this.FilePath = FilePath;
    }
    
    public int getEmailId() {
        return EmailId;
    }

    public void setEmailId(int EmailId) {
        this.EmailId = EmailId;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public int getAttachmentId() {
        return AttachmentId;
    }

    public void setAttachmentId(int AttachmentId) {
        this.AttachmentId = AttachmentId;
    }
    
    @Override
    public String toString() {
        return "Attachment{" + "EmailId=" + EmailId + ", FilePath=" + FilePath + '}';
    }
}
