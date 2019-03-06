import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


class personne {
    private String mailAdress;

    public String getMailAdress() {
        return mailAdress;
    }

    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
    }

    public personne(String mailAdress) throws AddressException {
        if(emailvalidatorr(mailAdress))
            this.mailAdress = mailAdress; 
        else throw new AddressException();

        
    }

    
    public boolean emailvalidatorr(String mail) throws AddressException
    {
        boolean isValid = false;
        try{
            InternetAddress internetaddress = new InternetAddress(mail);
            internetaddress.validate();
            isValid = true;
        } catch(AddressException e)
        {
            System.out.println("ERREUR de creation: "+mail+ " est une adress mail invalide! ");
        }
        return isValid;
        
    }
}
