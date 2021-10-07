public class BeatDetection
{
   private BitInputStream file;
   public BeatDetection(String f)
   {
      file = new BitInputStream(f);
   }
   
   public int currentBPM()
   {
      return 1;
   }
}