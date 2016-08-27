package com.gdg.jpq.maltratocero.model;

public class Alert {
    private String message1;
    private String message2;
    private String message3;
    private String referenceContact;
    private String referencePhone;

    public Alert() {}

    public Alert(String message1, String message2, String message3, String referenceContact, String referencePhone) {
        this.message1 = message1;
        this.message2 = message2;
        this.message3 = message3;
        this.referenceContact = referenceContact;
        this.referencePhone = referencePhone;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public String getMessage3() {
        return message3;
    }

    public void setMessage3(String message3) {
        this.message3 = message3;
    }

    public String getReferenceContact() {
        return referenceContact;
    }

    public void setReferenceContact(String referenceContact) {
        this.referenceContact = referenceContact;
    }

    public String getReferencePhone() {
        return referencePhone;
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone = referencePhone;
    }
}
