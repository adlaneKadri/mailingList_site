/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listedediffusion;

/**
 *
 * @author ameni
 */
public class Main {
    
    public static void main(String[] args) {
        Server serveurListe = new Server(Server.port, 1);
            serveurListe.ManageRequest();
    } 
}
