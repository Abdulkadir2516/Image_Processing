

package image_processing;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;

/**
 * @author yesil
 */
public class Cv_exp4 extends JFrame implements Runnable, Thread.UncaughtExceptionHandler {

    public static void main(String[] args) {
        // TODO code application logic here
        Cv_exp4.start();
    }
  
    // Kameradan frameleri almak için bu sýnýf kullanýlacak
    private VideoCapture videoCapture = null;

    // Kamera görüntüsünü yazdýracaðýmýz kontrol
    private JLabel jLabel1 = null;
    
    // Kameradan gelen frameler ile ilgili iþlemler için bu sýnýf kullanýlacak
    private Mat frame = null;
    

    
    // Doðrudan nesne oluþturulmasýn diye kurucu metodu private olarak tanýmladýk.
    private Cv_exp4() {
    }

    private void initComponents() {
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getContentPane().add(jLabel1, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setResizable(false);

        int width = (int) videoCapture.get(3);
        int height = (int) videoCapture.get(4);
        setSize(width + 20, height + 40);
        setTitle(width + ":" + height);

        setLocationRelativeTo(null);
    }

    private void initCamera() {
        // Opencv kütüphanesi yükleniyor.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Kamera görüntüsünü alacaðýmýz nesne oluþturuluyor.
        videoCapture = new VideoCapture();
        // Kamera açýlýyor.
        if (!videoCapture.open(0)) {
            System.err.println("Kamera açýlmadý.");
            System.exit(-1);
        }

        // Herbir frame'i tutacaðýmýz nesne oluþturuluyor.
        frame = new Mat();
    }

    public static void start() {
        // <editor-fold defaultstate="collapsed" desc="SwingUtilities.invokeLater">
        /**
         * Farklý thread'lar üzerinde oluþturulan bileþenlere sadece ayný thread
         * üzerinden eriþim yaparsak sorun yaþamayýz. Bu uygulamada
         * kullandýðýmýz Graphical User Interfaces (GUI) kontrolleri farklý bir
         * Thread üzerinde baþlatýlýrsa ve baþka bir thread'dan bu GUI
         * kontrollerine eriþim yapýldýðýnda sorunlar olabilir. Bu sorunun
         * üstesinden gelmek için Java'da invokeLater isimli metodu
         * kullanýyoruz. Ayrýca burada kullandýðýmýz kontrollerin Swing
         * kontrolleri olduðuna dikkat ediniz. Swing ile iþ parçacýðý
         * oluþturmaya çalýþtýðýmýzda, kullanýcý arayüzündeki tüm güncellemeler
         * olay sevketme thread'ýnda (The Event Dispatch Thread) gerçekleþir. Bu
         * nedenle, diðer herhangi bir thread'dan GUI güncelleme kodunu, Event
         * Dispatch Thread'ýndan çaðrýlacak þekilde düzenlememiz gerekir.
         *
         * Aþaðýdaki açýklamalar için link:
         * https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html
         * The Event Dispatch Thread (Olay Sevketme Threadý): Swing olay iþleme
         * kodu, Event Dispatch Thread'ý olarak bilinen özel bir thread'da
         * çalýþýr. Swing yöntemlerini çaðýran çoðu kod da bu thread üzerinde
         * çalýþýr. Bu, çoðu Swing nesne yöntemi "thread safe" olmadýðý için
         * gereklidir: Bunlarý birden çok thread'dan çaðýrmak, thread engelleme
         * veya bellek tutarlýlýðý hatalarý riskine neden olur. Bazý Swing
         * bileþen yöntemleri, API belirtiminde "thread safe" olarak
         * etiketlenmiþtir. Bunlar herhangi bir thread'dan güvenle çaðrýlabilir.
         * Diðer tüm Swing bileþeni yöntemleri, Event Dispatch Thread'ýndan
         * çaðrýlmalýdýr. Bu kuralý göz ardý eden programlar çoðu zaman düzgün
         * çalýþabilir. Ancak yeniden üretilmesi zor olan öngörülemeyen hatalara
         * maruz kalýrlar. Event Dispatch Thread'ýnda çalýþan kodu bir dizi kýsa
         * görev olarak düþünmek yararlýdýr. Görevlerin çoðu,
         * ActionListener.actionPerformed gibi olay iþleme yöntemlerinin
         * çaðrýlarýdýr. Diðer görevler, invokeLater veya invokeAndWait
         * kullanýlarak uygulama koduna göre zamanlanabilir. Event Dispatch
         * Thread'ýndaki görevler hýzlý bir þekilde bitmelidir. Diðer türlü,
         * iþlenmeyen olaylar yedeklenir ve kullanýcý arayüzü yanýt vermemeye
         * baþlar.
         */
        // </editor-fold>
        SwingUtilities.invokeLater(new Cv_exp4());

    }

    private void renderFrames() {
        while (true) {
            // Kamera görüntüsünü elde et ve bunu frame nesnesine aktar.
            if (videoCapture.read(frame)) {
                // Frame boþsa bir sorun var. Bu nedenle çýkýþ yapýyoruz.
                if (frame.empty()) {
                    break;
                }
                
                //Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

                // Frame nesnesi BufferedImage nesnesine dönüþtürülüyor.
                BufferedImage image = FrameToImage(frame);

                // Image nesnesi JLabel nesnesi içerisine yazdýrýlýyor.
                jLabel1.setIcon(new ImageIcon(image));

            }
        }
    }

    public BufferedImage FrameToImage(Mat frame) {
        int colorType = BufferedImage.TYPE_CUSTOM;

        if (frame.channels() == 1) {
            colorType = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            colorType = BufferedImage.TYPE_3BYTE_BGR;
        }

        // <editor-fold defaultstate="collapsed" desc="BufferedImage">
        /**
         * BufferedImage sýnýfý, eriþilebilir bir görüntü verisi arabelleðine
         * sahip bir Görüntüyü tanýmlar. BufferedImage, bir ColorModel ve bir
         * Raster görüntü verisinden oluþur. Raster'ýn SampleModel'indeki
         * bantlarýn sayýsý ve türleri, ColorModel'in renk ve alfa bileþenlerini
         * temsil etmesi için gereken sayý ve türlerle eþleþmelidir. Tüm
         * BufferedImage nesnelerinin sol üst köþesi (0, 0) koordinatýna
         * sahiptir. BufferedImage oluþturmak için kullanýlan herhangi bir
         * Raster bu nedenle minX=0 ve minY=0 olmalýdýr.
         *
         * Daha fazla bilgi için:
         * https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
         */
        // </editor-fold>
        BufferedImage bufferedImage = new BufferedImage(frame.width(), frame.height(), colorType);

        // <editor-fold defaultstate="collapsed" desc="Raster">
        /**
         * Raster sýnýfý, dikdörtgen bir piksel dizisini temsil eden bir
         * sýnýftýr. Bir Raster, örnek deðerleri depolayan bir DataBuffer'ý ve
         * bir DataBuffer'da belirli bir örnek deðerinin nasýl bulunacaðýný
         * açýklayan bir SampleModel'i içine alýr. Raster, düzlemin belirli bir
         * dikdörtgen alanýný kaplayan pikseller için deðerleri tanýmlar.
         * Raster'ýn sýnýrlayýcý dikdörtgeni olarak bilinen ve getBounds
         * yöntemiyle kullanýlabilen dikdörtgen; minX, minY, geniþlik ve
         * yükseklik deðerleriyle tanýmlanýr. minX ve minY deðerleri, Raster'in
         * sol üst köþesinin koordinatýný tanýmlar. Sýnýrlayýcý dikdörtgenin
         * dýþýndaki piksellere yapýlan baþvurular, bir istisnanýn atýlmasýna
         * veya Raster'ýn iliþkili DataBuffer'ýnýn istenmeyen öðelerine
         * baþvurulara neden olabilir. Bu tür piksellere eriþimden kaçýnmak
         * kullanýcýnýn sorumluluðundadýr. Bir SampleModel, bir Raster
         * örneklerinin bir DataBuffer'ýn primitive dizi öðelerinde nasýl
         * saklandýðýný açýklar. Örnekler, PixelInterleavedSampleModel veya
         * BandedSampleModel'de olduðu gibi veri öðesi baþýna bir tane
         * saklanabilir veya SinglePixelPackedSampleModel veya
         * MultiPixelPackedSampleModel'de olduðu gibi birkaç öðeye
         * paketlenebilir. Bir Raster düzlemde herhangi bir yerde yaþayabilse
         * de, SampleModel (0, 0)'dan baþlayan basit bir koordinat sisteminden
         * yararlanýr. Bu nedenle bir Raster, piksel konumlarýnýn Raster'in
         * koordinat sistemi ile SampleModel'inki arasýnda eþlenmesine izin
         * veren bir öteleme faktörü içerir. SampleModel koordinat sisteminden
         * Raster'ýnkine çeviri, getSampleModelTranslateX ve
         * getSampleModelTranslateY yöntemleriyle elde edilebilir. Bir Raster,
         * bir DataBuffer'ý baþka bir Raster ile açýk bir þekilde inþa ederek
         * veya createChild ve createTranslatedChild yöntemlerini kullanarak
         * paylaþabilir. Bu yöntemlerle oluþturulan rasterler, getParent yöntemi
         * aracýlýðýyla oluþturulduklarý Raster'a bir baþvuru döndürebilir.
         * createTranslatedChild veya createChild çaðrýsý yoluyla oluþturulmamýþ
         * bir Raster için getParent null deðerini döndürür.
         * createTranslatedChild yöntemi, geçerli Raster'ýn tüm verilerini
         * paylaþan ancak ayný geniþlik ve yükseklikte ancak farklý bir
         * baþlangýç ??noktasý olan bir sýnýrlayýcý dikdörtgeni kaplayan yeni
         * bir Raster döndürür. Örneðin, ana Raster (10, 10) ile (100, 100)
         * bölgesini iþgal ettiyse ve çevrilen Raster (50, 50) ile baþlayacak
         * þekilde tanýmlanmýþsa o zaman ebeveynin pikseli (20, 20) ve piksel
         * (60, 60), iki Raster tarafýndan paylaþýlan DataBuffer'da ayný konumu
         * iþgal eder. Ýlk durumda, karþýlýk gelen SampleModel koordinatýný elde
         * etmek için bir piksel koordinatýna (-10, -10) eklenmeli ve ikinci
         * durumda (-50, -50) eklenmelidir. Bir ebeveyn ve alt Raster arasýndaki
         * çeviri, çocuðun sampleModelTranslateX ve sampleModelTranslateY
         * deðerlerinin ebeveynin deðerlerinden çýkarýlmasýyla belirlenebilir.
         * createChild yöntemi, ebeveyninin sýnýrlayýcý dikdörtgeninin yalnýzca
         * bir alt kümesini veya ebeveyninin bantlarýnýn bir alt kümesini iþgal
         * eden yeni bir Raster oluþturmak için kullanýlabilir. Raster
         * oluþturmanýn doðru yolu, bu sýnýfta tanýmlanan statik oluþturma
         * yöntemlerinden birini kullanmaktýr.
         *
         * Daha fazla bilgi için:
         * https://docs.oracle.com/javase/7/docs/api/java/awt/image/Raster.html
         */
        // </editor-fold>
        WritableRaster writableRaster = bufferedImage.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) writableRaster.getDataBuffer();
        byte[] buffer = dataBuffer.getData();
        frame.get(0, 0, buffer);

        return bufferedImage;
    }

    @Override
    public void run() {
        initCamera();
        initComponents();

        
        // Form iþlemleri gerçekleþtirilirken ayný anda da kamera görüntüsünün elde edilmesi ve forma yazdýrýlmasý için bir Thread oluþturuyoruz.
        Thread t = new Thread() {
            @Override
            public void run() {
                renderFrames();
            }
        };
        t.setName("Thread1");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(t.getName() + ": " + e.getMessage());
    }


    

}
