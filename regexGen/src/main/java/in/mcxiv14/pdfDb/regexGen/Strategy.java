package in.mcxiv14.pdfDb.regexGen;

public abstract class Strategy {
    /** Change state */
    public abstract void initialize();
    /** Test state */
    public abstract void test();
    /** Get value */
    public abstract void process();
}
