package eu.javaspecialists.tjsn.issue003;

// TeeOutputStream
import java.io.*;
public class TeeOutputStream extends FilterOutputStream {
  private final OutputStream out2;
  public TeeOutputStream(OutputStream out1, OutputStream out2) {
    super(out1);
    this.out2 = out2;
  }
  public void write(int b) throws IOException {
    super.write(b);
    out2.write(b);
  }
  public void flush() throws IOException {
    super.flush();
    out2.flush();
  }
  public void close() throws IOException {
    super.close();
    out2.close();
  }
}
