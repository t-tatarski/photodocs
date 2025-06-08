import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ResizeImage {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Użycie: java ResizeImage <ścieżka_do_obrazu> <ścieżka_do_zapisu>");
            System.out.println("np java ResizeImage /to/plik/zrodlowy.jpg /to/plik/docelowy.jpg");
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];

        try {
            BufferedImage originalImage = ImageIO.read(new File(inputPath));

            // Wymiary 
            double widthMm = 35;
            double heightMm = 45;

            // DPI - standardowe 300 dpi dla zdjęć
            int dpi = 300;

            // przeliczenie na piksele
            int targetWidthPx = (int) Math.round(widthMm / 25.4 * dpi);
            int targetHeightPx = (int) Math.round(heightMm / 25.4 * dpi);

            //  proporcje oryginalnego obrazu
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            double originalAspect = (double) originalWidth / originalHeight;
            double targetAspect = (double) targetWidthPx / targetHeightPx;

            int newWidth, newHeight;

            // Zachowaj proporcje, dopasowując do wymiarów docelowych
            if (originalAspect > targetAspect) {
                newWidth = targetWidthPx;
                newHeight = (int) (targetWidthPx / originalAspect);
            } else {
                newHeight = targetHeightPx;
                newWidth = (int) (targetHeightPx * originalAspect);
            }

            // Utwórz nowy obraz o wymiarach z zachowaniem proporcji
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            // Skaluj oryginalny obraz do nowych wymiarów
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g.dispose();

            // Zapisz zmieniony obraz do pliku
            ImageIO.write(resizedImage, "jpg", new File(outputPath));

            System.out.println("Obraz został zmieniony z zachowaniem proporcji i zapisany jako " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
