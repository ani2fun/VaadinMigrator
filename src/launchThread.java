import java.io.File;


public class launchThread implements Runnable{
    File file;
    int conversions;
    public launchThread(File file, int convertion)
    {
        this.file = file;
        this.conversions = convertion;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {

        ProjectConverter converter = new ProjectConverter(conversions);
        converter.convertProject(file);

    }
}