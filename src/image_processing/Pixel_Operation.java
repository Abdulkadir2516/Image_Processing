
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
        //sistem kütüphanesini yükleme
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //görüntüyü barýndýracak nesne
        Mat imageArray;
        //görüntü dosyasýný oku
        imageArray = Imgcodecs.imread("src/image/opencv_logo_with_text.png");
        //mat nesnesinin satýr ve sutun sayýsý
        // orjinal görüntüyü ekranda gösteriyorsuz
        HighGui.imshow("orjinal resim", imageArray);
        
        // görüntü hakkýnda bililendirme
        System.out.println(imageArray.channels());
        System.out.println(imageArray.rows());
        System.out.println(imageArray.cols());

        // görüntüyü mat nesnesine aktarma
        Mat image = Imgcodecs.imread("src/image/opencv_logo_with_text.png");

        // üzerinde iþlem ve filtre uygulayýp yazmak için sýfýrlardan oluþan bir mat nesnesinin tanýmlanmasý
        Mat newImage = Mat.zeros(image.size(), image.type());
        
        // verilerimiz byte þeklinde tam olarak þu formatta 1. kanal için x y ve renk kodu
        // 0,0,0 - 0,0,1 - 0,0,2 üç kanallý bir resimde bu iþlemler biraz karýþýk 
        // 0,1,255 1. kanal blue rengi için pixel deðeri
        // 0,1,255 2. kanal green rengi için pixel deðeri
        // 0,1,255 3. kanal red rengi için pixel deðeri
        // open cv resimleri okurken bgr formatýnda okur 
        byte[] imageData = new byte[(int) (image.total() * image.channels())];

        double alpha =1;
        int beta = 1;
        
        image.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (newImage.total() * newImage.channels())];
        
        for (int y = 0; y < image.rows(); y++) {
            for (int x = 0; x < image.cols(); x++) {
                
                for (int c = 0; c < image.channels(); c++) {
                    
                    // buradaki hesaplama iþleminde aslýnda sýfýrdan bizler bir format oluþturmaya çalýþýyoruz
                    // open cv nin formatýna uygun olarak aþaðýdaki gibi bir fomül kullanýlmakta
                    // ayný zamanda kendi formatýmýzý aþaðýda 0-255 arasýnda tutuyoruz
                    double pixelValue = imageData[(y * image.cols() + x) * image.channels() + c];
                    
                    // bgr
                    pixelValue =  pixelValue < 0 ? pixelValue + 256 : pixelValue;
                    
                    if(c==1 && pixelValue<255)
                    {
                        pixelValue = 255;
                    }
                    
                    // bu kod satýrýnda aslýnda bizim filtremizin oluþturulmasý iþlemi
                    // saturate metodu deðerlerimizi normalize etmemize olanak saðlýyor yaný deðerleri 0-255 arasýnda tutuyor
                    newImageData[(y * image.cols() + x) * image.channels() + c] = saturate(alpha * pixelValue + beta);
                
                }
            }
        }
        // oluþturduðumuz filtreyi put metodu sayesinde yeni image matrimize iþliyor ve 
        // son olarak imshow ile filtre uygulanmýþ mresmi ekranda gösteriyoruz
        newImage.put(0, 0, newImageData);
        
        HighGui.imshow("Kýrmýzý Pixellerin Silinmiþ Hali", newImage);
                
        HighGui.waitKey();
        
        System.exit(0);
    }
    
    public static byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }
}
