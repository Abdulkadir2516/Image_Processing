

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
  
    // Kameradan frameleri almak i�in bu s�n�f kullan�lacak
    private VideoCapture videoCapture = null;

    // Kamera g�r�nt�s�n� yazd�raca��m�z kontrol
    private JLabel jLabel1 = null;
    
    // Kameradan gelen frameler ile ilgili i�lemler i�in bu s�n�f kullan�lacak
    private Mat frame = null;
    

    
    // Do�rudan nesne olu�turulmas�n diye kurucu metodu private olarak tan�mlad�k.
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
        // Opencv k�t�phanesi y�kleniyor.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Kamera g�r�nt�s�n� alaca��m�z nesne olu�turuluyor.
        videoCapture = new VideoCapture();
        // Kamera a��l�yor.
        if (!videoCapture.open(0)) {
            System.err.println("Kamera a��lmad�.");
            System.exit(-1);
        }

        // Herbir frame'i tutaca��m�z nesne olu�turuluyor.
        frame = new Mat();
    }

    public static void start() {
        // <editor-fold defaultstate="collapsed" desc="SwingUtilities.invokeLater">
        /**
         * Farkl� thread'lar �zerinde olu�turulan bile�enlere sadece ayn� thread
         * �zerinden eri�im yaparsak sorun ya�amay�z. Bu uygulamada
         * kulland���m�z Graphical User Interfaces (GUI) kontrolleri farkl� bir
         * Thread �zerinde ba�lat�l�rsa ve ba�ka bir thread'dan bu GUI
         * kontrollerine eri�im yap�ld���nda sorunlar olabilir. Bu sorunun
         * �stesinden gelmek i�in Java'da invokeLater isimli metodu
         * kullan�yoruz. Ayr�ca burada kulland���m�z kontrollerin Swing
         * kontrolleri oldu�una dikkat ediniz. Swing ile i� par�ac���
         * olu�turmaya �al��t���m�zda, kullan�c� aray�z�ndeki t�m g�ncellemeler
         * olay sevketme thread'�nda (The Event Dispatch Thread) ger�ekle�ir. Bu
         * nedenle, di�er herhangi bir thread'dan GUI g�ncelleme kodunu, Event
         * Dispatch Thread'�ndan �a�r�lacak �ekilde d�zenlememiz gerekir.
         *
         * A�a��daki a��klamalar i�in link:
         * https://docs.oracle.com/javase/tutorial/uiswing/concurrency/dispatch.html
         * The Event Dispatch Thread (Olay Sevketme Thread�): Swing olay i�leme
         * kodu, Event Dispatch Thread'� olarak bilinen �zel bir thread'da
         * �al���r. Swing y�ntemlerini �a��ran �o�u kod da bu thread �zerinde
         * �al���r. Bu, �o�u Swing nesne y�ntemi "thread safe" olmad��� i�in
         * gereklidir: Bunlar� birden �ok thread'dan �a��rmak, thread engelleme
         * veya bellek tutarl�l��� hatalar� riskine neden olur. Baz� Swing
         * bile�en y�ntemleri, API belirtiminde "thread safe" olarak
         * etiketlenmi�tir. Bunlar herhangi bir thread'dan g�venle �a�r�labilir.
         * Di�er t�m Swing bile�eni y�ntemleri, Event Dispatch Thread'�ndan
         * �a�r�lmal�d�r. Bu kural� g�z ard� eden programlar �o�u zaman d�zg�n
         * �al��abilir. Ancak yeniden �retilmesi zor olan �ng�r�lemeyen hatalara
         * maruz kal�rlar. Event Dispatch Thread'�nda �al��an kodu bir dizi k�sa
         * g�rev olarak d���nmek yararl�d�r. G�revlerin �o�u,
         * ActionListener.actionPerformed gibi olay i�leme y�ntemlerinin
         * �a�r�lar�d�r. Di�er g�revler, invokeLater veya invokeAndWait
         * kullan�larak uygulama koduna g�re zamanlanabilir. Event Dispatch
         * Thread'�ndaki g�revler h�zl� bir �ekilde bitmelidir. Di�er t�rl�,
         * i�lenmeyen olaylar yedeklenir ve kullan�c� aray�z� yan�t vermemeye
         * ba�lar.
         */
        // </editor-fold>
        SwingUtilities.invokeLater(new Cv_exp4());

    }

    private void renderFrames() {
        while (true) {
            // Kamera g�r�nt�s�n� elde et ve bunu frame nesnesine aktar.
            if (videoCapture.read(frame)) {
                // Frame bo�sa bir sorun var. Bu nedenle ��k�� yap�yoruz.
                if (frame.empty()) {
                    break;
                }
                
                //Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

                // Frame nesnesi BufferedImage nesnesine d�n��t�r�l�yor.
                BufferedImage image = FrameToImage(frame);

                // Image nesnesi JLabel nesnesi i�erisine yazd�r�l�yor.
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
         * BufferedImage s�n�f�, eri�ilebilir bir g�r�nt� verisi arabelle�ine
         * sahip bir G�r�nt�y� tan�mlar. BufferedImage, bir ColorModel ve bir
         * Raster g�r�nt� verisinden olu�ur. Raster'�n SampleModel'indeki
         * bantlar�n say�s� ve t�rleri, ColorModel'in renk ve alfa bile�enlerini
         * temsil etmesi i�in gereken say� ve t�rlerle e�le�melidir. T�m
         * BufferedImage nesnelerinin sol �st k��esi (0, 0) koordinat�na
         * sahiptir. BufferedImage olu�turmak i�in kullan�lan herhangi bir
         * Raster bu nedenle minX=0 ve minY=0 olmal�d�r.
         *
         * Daha fazla bilgi i�in:
         * https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferedImage.html
         */
        // </editor-fold>
        BufferedImage bufferedImage = new BufferedImage(frame.width(), frame.height(), colorType);

        // <editor-fold defaultstate="collapsed" desc="Raster">
        /**
         * Raster s�n�f�, dikd�rtgen bir piksel dizisini temsil eden bir
         * s�n�ft�r. Bir Raster, �rnek de�erleri depolayan bir DataBuffer'� ve
         * bir DataBuffer'da belirli bir �rnek de�erinin nas�l bulunaca��n�
         * a��klayan bir SampleModel'i i�ine al�r. Raster, d�zlemin belirli bir
         * dikd�rtgen alan�n� kaplayan pikseller i�in de�erleri tan�mlar.
         * Raster'�n s�n�rlay�c� dikd�rtgeni olarak bilinen ve getBounds
         * y�ntemiyle kullan�labilen dikd�rtgen; minX, minY, geni�lik ve
         * y�kseklik de�erleriyle tan�mlan�r. minX ve minY de�erleri, Raster'in
         * sol �st k��esinin koordinat�n� tan�mlar. S�n�rlay�c� dikd�rtgenin
         * d���ndaki piksellere yap�lan ba�vurular, bir istisnan�n at�lmas�na
         * veya Raster'�n ili�kili DataBuffer'�n�n istenmeyen ��elerine
         * ba�vurulara neden olabilir. Bu t�r piksellere eri�imden ka��nmak
         * kullan�c�n�n sorumlulu�undad�r. Bir SampleModel, bir Raster
         * �rneklerinin bir DataBuffer'�n primitive dizi ��elerinde nas�l
         * sakland���n� a��klar. �rnekler, PixelInterleavedSampleModel veya
         * BandedSampleModel'de oldu�u gibi veri ��esi ba��na bir tane
         * saklanabilir veya SinglePixelPackedSampleModel veya
         * MultiPixelPackedSampleModel'de oldu�u gibi birka� ��eye
         * paketlenebilir. Bir Raster d�zlemde herhangi bir yerde ya�ayabilse
         * de, SampleModel (0, 0)'dan ba�layan basit bir koordinat sisteminden
         * yararlan�r. Bu nedenle bir Raster, piksel konumlar�n�n Raster'in
         * koordinat sistemi ile SampleModel'inki aras�nda e�lenmesine izin
         * veren bir �teleme fakt�r� i�erir. SampleModel koordinat sisteminden
         * Raster'�nkine �eviri, getSampleModelTranslateX ve
         * getSampleModelTranslateY y�ntemleriyle elde edilebilir. Bir Raster,
         * bir DataBuffer'� ba�ka bir Raster ile a��k bir �ekilde in�a ederek
         * veya createChild ve createTranslatedChild y�ntemlerini kullanarak
         * payla�abilir. Bu y�ntemlerle olu�turulan rasterler, getParent y�ntemi
         * arac�l���yla olu�turulduklar� Raster'a bir ba�vuru d�nd�rebilir.
         * createTranslatedChild veya createChild �a�r�s� yoluyla olu�turulmam��
         * bir Raster i�in getParent null de�erini d�nd�r�r.
         * createTranslatedChild y�ntemi, ge�erli Raster'�n t�m verilerini
         * payla�an ancak ayn� geni�lik ve y�kseklikte ancak farkl� bir
         * ba�lang�� ??noktas� olan bir s�n�rlay�c� dikd�rtgeni kaplayan yeni
         * bir Raster d�nd�r�r. �rne�in, ana Raster (10, 10) ile (100, 100)
         * b�lgesini i�gal ettiyse ve �evrilen Raster (50, 50) ile ba�layacak
         * �ekilde tan�mlanm��sa o zaman ebeveynin pikseli (20, 20) ve piksel
         * (60, 60), iki Raster taraf�ndan payla��lan DataBuffer'da ayn� konumu
         * i�gal eder. �lk durumda, kar��l�k gelen SampleModel koordinat�n� elde
         * etmek i�in bir piksel koordinat�na (-10, -10) eklenmeli ve ikinci
         * durumda (-50, -50) eklenmelidir. Bir ebeveyn ve alt Raster aras�ndaki
         * �eviri, �ocu�un sampleModelTranslateX ve sampleModelTranslateY
         * de�erlerinin ebeveynin de�erlerinden ��kar�lmas�yla belirlenebilir.
         * createChild y�ntemi, ebeveyninin s�n�rlay�c� dikd�rtgeninin yaln�zca
         * bir alt k�mesini veya ebeveyninin bantlar�n�n bir alt k�mesini i�gal
         * eden yeni bir Raster olu�turmak i�in kullan�labilir. Raster
         * olu�turman�n do�ru yolu, bu s�n�fta tan�mlanan statik olu�turma
         * y�ntemlerinden birini kullanmakt�r.
         *
         * Daha fazla bilgi i�in:
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

        
        // Form i�lemleri ger�ekle�tirilirken ayn� anda da kamera g�r�nt�s�n�n elde edilmesi ve forma yazd�r�lmas� i�in bir Thread olu�turuyoruz.
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
