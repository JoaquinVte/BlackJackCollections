package cartas;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 * @web http://jc-mouse.blogspot.com/
 * @author Mouse
 */
public class i2ascii {
    private int r,g,b;
    private Color color;
    private int umbral = 127;
    private int negro = -16777216;
    private int blanco = -1;
    private String ascii = "";
    // los caracteres por los que se reemplazan los pixeles    
    //                             0   1   2   3   4    5   6   7   8   9  10  11  12  13  14     15=\
    private String[] caracter =  {"`","'","°",",",".","\"","_","[","]","J","7","P","L","8","/","" + (char) 92};
    
    private BufferedImage foto;

    public i2ascii(String file){
        try {
            //se carga una foto
            foto = ImageIO.read(i2ascii.class.getResource("/images/"+file));
            //foto = ImageIO.read(getClass().getResource("jcmouse.jpg"));
            //se convierte la foto en blanco y negro
            foto = set_Blanco_y_Negro_con_Umbral(foto);
            
        } catch (IOException ex) {
            Logger.getLogger(i2ascii.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String ConvertToAscii(){
        BufferedImage f = foto;

        int x = f.getWidth();
        int y = f.getHeight();

        //se recorre la matriz de la imagen fila x fila de tres en tres
        for(int j=0;j<y ;j=j+3 ){
          //se recorre la matriz de la imagen col x col de dos en dos
          for(int i=0;i<x;i=i+2 ){
              
              if( (i < (x-1)) && (j < (y-1)) ){
              //__ 0
              //__
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==blanco ){
                  ascii = ascii + caracter[0];
                  continue;
              }
              //_X 1
              //__
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==blanco ){
                  ascii = ascii + caracter[1];
                  continue;
              }
              //X_ 2
              //__
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==blanco ){
                    ascii = ascii + caracter[2];
                    continue;
              }
              //__ 3
              //X_
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==blanco ){
                    ascii = ascii + caracter[3];
                    continue;
              }
              //__ 4
              //_X
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==negro ){
                    ascii = ascii + caracter[4];
                    continue;
              }
              //XX 5
              //__
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==blanco ){
                    ascii = ascii + caracter[5];
                    continue;
              }
              //__ 6
              //XX
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==negro ){
                    ascii = ascii + caracter[6];
                    continue;
              }
              //X_ 7
              //X_
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==blanco ){
                    ascii = ascii + caracter[7];
                    continue;
              }
              //_X 8
              //_X
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==negro ){
                  ascii = ascii + caracter[8];
                  continue;
              }
              //_X 9
              //XX
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==negro ){
                  ascii = ascii + caracter[9];
                  continue;
              }
              //XX 10
              //_X
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==negro ){
                  ascii = ascii + caracter[10];
                  continue;
              }
              //XX 11
              //X_
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==blanco ){
                  ascii = ascii + caracter[11];
                  continue;
              }
              //X_ 12
              //XX
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==negro ){
                  ascii = ascii + caracter[12];
                  continue;
              }
              //XX 13
              //XX
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==negro ){
                  ascii = ascii + caracter[13];
                  continue;
              }

              //_X 14
              //X_
              if( f.getRGB(i, j)==blanco && f.getRGB(i+1, j)==negro &&
                  f.getRGB(i, j+1)==negro && f.getRGB(i+1, j+1)==blanco ){
                  ascii = ascii + caracter[14];
                  continue;
              }

              //X_ 15
              //_X
              if( f.getRGB(i, j)==negro && f.getRGB(i+1, j)==blanco &&
                  f.getRGB(i, j+1)==blanco && f.getRGB(i+1, j+1)==negro ){
                  ascii = ascii + caracter[15];
                  continue;
              }
            }// fin si
          }
          //se concatena la fila resultante y se añade un salto de linea
          ascii = ascii + "\n";
        }        
        //se retorna la imagen transformada en ASCII
        return ascii;
    }
    
    //Metodo dado una imagen, la convierte en imagen en blanco y negro
    private  BufferedImage set_Blanco_y_Negro_con_Umbral(BufferedImage f){
        BufferedImage bn = new BufferedImage(f.getWidth(),f.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        //se traspasan los colores Pixel a Pixel
        for(int i=0;i<f.getWidth();i++){
          for(int j=0;j<f.getHeight();j++){
               color = new Color(f.getRGB(i, j));
               //se extraen los valores RGB
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                //dependiendo del valor del umbral, se van separando los
                // valores RGB a 0 y 255
                r =(r>umbral)? 255: 0;
                g =(g>umbral)? 255: 0;
                b =(b>umbral)? 255: 0;
                bn.setRGB(i, j, new Color(r,g,b).getRGB());
          }
        }
        return bn;
    }

}
