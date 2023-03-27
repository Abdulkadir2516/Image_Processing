
// https://github.com/opencv/opencv/tree/4.x/samples/java
// https://docs.opencv.org/4.x/d9/df8/tutorial_root.html

package image_processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;


public class Pixel_Operation {
    
    public static void main(String[] args)
    {
        //sistem k�t�phanesini y�kleme
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //g�r�nt�y� bar�nd�racak nesne
        Mat imageArray;
        //g�r�nt� dosyas�n� oku
        imageArray = Imgcodecs.imread("src/image/opencv_logo_with_text.png");
        //mat nesnesinin sat�r ve sutun say�s�
        // orjinal g�r�nt�y� ekranda g�steriyorsuz
        HighGui.imshow("orjinal resim", imageArray);
        
        // g�r�nt� hakk�nda bililendirme
        System.out.println(imageArray.channels());
        System.out.println(imageArray.rows());
        System.out.println(imageArray.cols());

        // g�r�nt�y� mat nesnesine aktarma
        Mat image = Imgcodecs.imread("src/image/opencv_logo_with_text.png");

        // �zerinde i�lem ve filtre uygulay�p yazmak i�in s�f�rlardan olu�an bir mat nesnesinin tan�mlanmas�
        Mat newImage = Mat.zeros(image.size(), image.type());
        
        // verilerimiz byte �eklinde tam olarak �u formatta 1. kanal i�in x y ve renk kodu
        // 0,0,0 - 0,0,1 - 0,0,2 �� kanall� bir resimde bu i�lemler biraz kar���k 
        // 0,1,255 1. kanal blue rengi i�in pixel de�eri
        // 0,1,255 2. kanal green rengi i�in pixel de�eri
        // 0,1,255 3. kanal red rengi i�in pixel de�eri
        // open cv resimleri okurken bgr format�nda okur 
        byte[] imageData = new byte[(int) (image.total() * image.channels())];

        double alpha =1;
        int beta = 1;
        
        image.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (newImage.total() * newImage.channels())];
        
        for (int y = 0; y < image.rows(); y++) {
            for (int x = 0; x < image.cols(); x++) {
                
                for (int c = 0; c < image.channels(); c++) {
                    
                    // buradaki hesaplama i�leminde asl�nda s�f�rdan bizler bir format olu�turmaya �al���yoruz
                    // open cv nin format�na uygun olarak a�a��daki gibi bir fom�l kullan�lmakta
                    // ayn� zamanda kendi format�m�z� a�a��da 0-255 aras�nda tutuyoruz
                    double pixelValue = imageData[(y * image.cols() + x) * image.channels() + c];
                    
                    // bgr
                    pixelValue =  pixelValue < 0 ? pixelValue + 256 : pixelValue;
                    
                    if(c==1 && pixelValue<255)
                    {
                        pixelValue = 255;
                    }
                    
                    // bu kod sat�r�nda asl�nda bizim filtremizin olu�turulmas� i�lemi
                    // saturate metodu de�erlerimizi normalize etmemize olanak sa�l�yor yan� de�erleri 0-255 aras�nda tutuyor
                    newImageData[(y * image.cols() + x) * image.channels() + c] = saturate(alpha * pixelValue + beta);
                
                }
            }
        }
        // olu�turdu�umuz filtreyi put metodu sayesinde yeni image matrimize i�liyor ve 
        // son olarak imshow ile filtre uygulanm�� mresmi ekranda g�steriyoruz
        newImage.put(0, 0, newImageData);
        
        HighGui.imshow("K�rm�z� Pixellerin Silinmi� Hali", newImage);
                
        HighGui.waitKey();
        
        System.exit(0);
    }
    
    public static byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }
}
