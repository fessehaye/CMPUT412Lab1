import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.bluetooth.DiscoveryListener;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTConnector;

public class BlueToothPC {
   
   private NXTConnector n;

   private OutputStream out = null;
   
   private InputStream in = null;
   
   public void init(String name) throws NXTCommException{
      n = new NXTConnector();
      n.connectTo(name);
      in = n.getInputStream();
      out = n.getOutputStream();         
   }
   
   public void close() throws IOException{
      in.close();
      out.close();
      n.close();
   }
   
   public void write(int envoi) throws IOException{
      out.write(envoi);
      out.flush();
      
   }
   
   public int read() throws IOException{
      return in.read();
   }

   /**
    * @param args
    */
   public static void main(String[] args) throws Exception {
      
      BlueToothPC bt = new BlueToothPC();
      try{
         bt.init("EV3");
      
         for(int i = 0 ; i < 100 ; i++){
            bt.write(i);
            int r = bt.read();
            System.out.println(r);
         }
      
      bt.close();
      }
      catch(Exception e){
         e.printStackTrace();
      }
   }

}