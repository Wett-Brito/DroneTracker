package DroneControl.ControlDrone;

import java.util.Scanner;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws MailjetException, MailjetSocketTimeoutException
    {
        
    	Scanner sc = new Scanner(System.in);
    	
    	SendEmail email = new SendEmail();
    	
    	email.enviarEmail("Teste", "Teste", "wellingtonsantosbritos@gmail.com");
    	
    	System.out.println("ID do drone: ");
    	String id_drone = sc.nextLine();
    	
    	System.out.println("Latitude da localizacao: ");
    	String latitude = sc.nextLine();
    	
    	System.out.println("longitude da localizacao: ");
    	String longitude = sc.nextLine();
    	
    	int temperatura;
    	int umidade;
    	
    	System.out.println("Ativar rastreamento? (true/false)");

    	boolean rastreamento = sc.nextBoolean();
    	
    	int alertaTemperatura = 0;
    	int alertaUmidade = 0;


    	while(rastreamento) {
    		
        	System.out.println("Medicacao da temperatura: ");
    		temperatura = sc.nextInt();
    		
    		if(temperatura <= 0|| temperatura >= 35) {
    			alertaTemperatura++;
    			if(alertaTemperatura >= 6) {
    	        	System.out.println("funcionou temp: ");
    	        	
    				//Desparar Email
    	        	
        			alertaTemperatura = 0;
    			}
    			
    		} else {
    			alertaTemperatura = 0;
    		}
    		
        	System.out.println("Medicacao da umidade: ");
    		umidade = sc.nextInt();
    		
    		if(umidade < 15) {
    			alertaUmidade++;
    			if(alertaUmidade >= 6) {
    	        	System.out.println("funcionou umidade: ");

    				//Desparar Email
    	        	
        			alertaUmidade = 0;
    			}
    		} else {
    			alertaUmidade = 0;
    		}
    		
    		try {
				Thread.sleep(10_0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	sc.close();
    }
}
