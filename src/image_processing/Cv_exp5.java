// https://github.com/opencv/opencv/tree/4.x/samples/java
// https://mesutpiskin.com/blog/wp-content/uploads/2017/01/OpenCV%20Kitap.pdf
// https://docs.opencv.org/4.x/d9/df8/tutorial_root.html
package image_processing;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.core.CvType;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Cv_exp5 {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = new Mat();
        
        image.create(500, 500,CvType.CV_8UC3);
        
        /* rectangle metodu paramatetre olarak, üzerinde çizim yapýlacak bir mat nesnesi
        * dikdörtgen çizimi için gerekli olan 4 köþenin koordinatý ve rengini almaktadýr. * */
        //Imgproc.rectangle(image, new Point(10, 100), new Point(100, 200), new Scalar(76, 255, 0));
        
        Imgproc.circle(image, new Point(image.width()/2,image.height()/2 ), 50, new Scalar(0,0,0), 3, 1);
        Imgproc.circle(image, new Point(image.width()/2,image.height()/2 ), 80, new Scalar(255,0,0), 3, 2);
        
        Imgproc.line(image, new Point(image.width()/2,0), new Point(image.width()/2,image.height()), new Scalar(255,255,0), 2);
        Imgproc.line(image, new Point(0,image.height()/2), new Point(image.width(),image.height()/2), new Scalar(0,0,255), 5);

        
        //Imgproc.circle(image, new Point(image.width()/2,image.height()/2 ), 100, new Scalar(210, 41, 45), 5, 5 );
        
        
        
        HighGui.imshow("Çýktý", image);
        //Imgcodecs.imwrite("src/image/images.png", goruntuDizisi);
        HighGui.waitKey();
        
        System.exit(0);

        System.out.println("Düzenlenen görüntü dosya sistemine yazýldý.");
    }

}

