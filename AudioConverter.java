import java.io.*;
import java.util.*;
import java.awt.*;
import ddf.minim.*;
import ddf.minim.signals.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import javax.sound.sampled.*;
/***************************/
import javax.swing.*;
/*****************************/
public class AudioConverter extends Canvas
{
   
   public AudioConverter()
   {
      
   }
   
   public static void main(String args[]) throws FileNotFoundException
   {
      Scanner kb = new Scanner(System.in);
/********MAKING MUSIC NOTE MAP*********************************/
      
      Map<Double,String> freqMap = new TreeMap<>();
      Map<String,Integer> indexMap = new TreeMap<>();
      freqMap.put(440.0,"A4");
      int octave = 4;
      char letter = 'A';
      boolean needFlat = true;
      double frequency = 440;
      for(int i = 1; i < 49; i++)
      {  
         String note = "" + letter;
         
         if(needFlat && letter != 'F' && letter != 'C')
         {
            note += "b";
            
            letter -= 1;
            needFlat = false;
         }
         else if(letter == 'F' || letter == 'C')
         {
            letter -= 1;
         }
         else
         {
            needFlat = true;
         }
         note += octave;
         if(letter == 64)
         {
            letter = 71;
         }
         if(letter == 'B' && needFlat == true)
         {
            octave--;
         }
         
         
         frequency = frequency / Math.pow(2,1.0/12);
         freqMap.put(frequency,note);
         indexMap.put(note,49-i);
         
      }
      letter = 'B';
      needFlat = true;
      octave = 4;
      frequency = 440;
      for(int i = 1; i < 40; i++)
      {  
         String note = "" + letter;
         
         if(needFlat && letter != 'F' && letter != 'C')
         {
            note += "b";
            
            
            needFlat = false;
         }
         else if(letter == 'F' || letter == 'C')
         {
            letter += 1;
         }
         else
         {
            letter += 1;
            needFlat = true;
         }
         note += octave;
         if(letter == 72)
         {
            letter = 65;
         }
         if(letter == 'C' && needFlat == true)
         {
            octave++;
         }
         
         
         frequency = frequency * Math.pow(2,1.0/12);
         freqMap.put(frequency,note);
         indexMap.put(note,49+i);
      }
      
      
    //   for(String i : freqMap.values()) //print notenames
//       {
//          System.out.println(i);
//       }
/**************************************************************/
      String fileName;
      //fileName = "C:\\Users\\jskes\\OneDrive\\Desktop\\Kester CSIII\\Music\\Note A 440 hz.wav";
      fileName = "C:\\Users\\jskes\\OneDrive\\Desktop\\Kester CSIII\\Music\\Test Notes.wav";
      //fileName = "C:\\Users\\jskes\\OneDrive\\Desktop\\Kester CSIII\\Music\\National Anthem of USSR.wav";
      File songFile = new File(fileName);
      BitInputStream file = new BitInputStream(fileName);
      //ChunkID
      String ChunkID = "";
      for(int numBytes = 0; numBytes < 5; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         int i = (int)b;
         char c = (char)i;
         ChunkID += c;
         }
         System.out.println("ChunkID: " + ChunkID);
           
      //ChunkSize
      String chunk = "";
      int ChunkSize = 0;
      for(int numBytes = 0; numBytes < 3; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
      ChunkSize = Integer.parseInt(chunk, 2);
      System.out.println("ChunkSize: " + ChunkSize);
      
      System.out.print("Format: ");
      //get WAVE                                                  
      for(int numBytes = 0; numBytes < 4; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         int i = (int)b;
         char c = (char)i;
         System.out.print(c);
      }
         System.out.println();
      
      //Subchunk1ID
      String Subchunk1ID = "";
      for(int numBytes = 0; numBytes < 4; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         int i = (int)b;
         char c = (char)i;
         Subchunk1ID += c;
      }
         System.out.println("Subchunk1ID: " + Subchunk1ID);
         
      //Subchunk1Size
      int Subchunk1Size = 0;
      for(int numBytes = 0; numBytes < 4; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         Subchunk1Size += (int)b;
      }   
      System.out.println("Subchunk1Size: " + Subchunk1Size);
      
      //AudioFormat
      int AudioFormat = 0;
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         AudioFormat += (int)b;
      }   
      System.out.println("AudioFormat: " + AudioFormat);
      
      //NumChannels
      int NumChannels = 0;
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         NumChannels += (int)b;
      }   
      System.out.println("NumChannels: " + NumChannels);
      
      //SampleRate
      chunk = "";
      int SampleRate = 0;
      for(int numBytes = 0; numBytes < 1; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
    //  System.out.println(chunk);
      SampleRate = Integer.parseInt(chunk, 2);
      //System.out.println("SampleRate: " + SampleRate);
      
      chunk = "";
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
    //  System.out.println(chunk);
      SampleRate += Integer.parseInt(chunk, 2);
      System.out.println("SampleRate: " + SampleRate);
      
      for(int numBytes = 0; numBytes < 1; numBytes++)
      {
         for(int i = 0; i < 8; i++)
         {
         file.readBit();
         }
      }
      
      
      
      //ByteRate
      chunk = "";
      int ByteRate = 0;
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
  //    System.out.println(chunk);
      ByteRate += Integer.parseInt(chunk, 2);
      
      chunk = "";
      
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
  //    System.out.println(chunk);
      ByteRate += Integer.parseInt(chunk, 2);
      System.out.println("Given ByteRate: " + ByteRate);
      
      //BlockAlign
      
      int BlockAlign = 0;
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         BlockAlign += (int)b;
      }   
      System.out.println("BlockAlign: " + BlockAlign);

      //BitsPerSample
      int BitsPerSample = 0;
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         Byte b = new Byte(Byte.parseByte(flip,2));
         BitsPerSample += (int)b;
      }   
      System.out.println("BitsPerSample: " + BitsPerSample);
      ByteRate = SampleRate * NumChannels * BitsPerSample/8;
      System.out.println("Calculated ByteRate: " + ByteRate);
      System.out.println();
      
      // String extraData = "";
//       while(true)
//       {
//          String first = "";
//          for(int i = 0; i < 8; i++)
//          {
//             first += file.readBit();
//          }
//          String flip = "";
//          for(int y = 7; y >= 0; y--)
//          {
//             flip += first.charAt(y);
//          }
//          extraData = flip;
//          if(!extraData.equals("00000000"))
//             break;
//       }
      
      //Subchunk2ID
      String Subchunk2ID = "";
      while(true)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         // Byte b = new Byte(Byte.parseByte(flip,2));
         int i = Integer.parseInt(flip,2);
         char c = (char)i;
         Subchunk2ID += c;
         if(Subchunk2ID.indexOf("data") != -1)
         {
            break;
         }
      }
      System.out.println("Subchunk2ID: " + Subchunk2ID);
     // System.out.println(Subchunk2ID.indexOf("data"));
         
      //Subchunk2Size
      int Subchunk2Size = 0;
      chunk = "";
      for(int numBytes = 0; numBytes < 2; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
   //   System.out.println(chunk);
      Subchunk2Size = Integer.parseInt(chunk, 2);
      
      chunk = "";
      for(int numBytes = 0; numBytes < 3; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
      
      Subchunk2Size += Integer.parseInt(chunk, 2);
      
      chunk = "";
      for(int numBytes = 0; numBytes < 1; numBytes++)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         String flip = "";
         for(int y = 7; y >= 0; y--)
         {
            flip += first.charAt(y);
         }
         chunk += flip;
      }
      
      Subchunk2Size += Integer.parseInt(chunk, 2);
      
      System.out.println("Subchunk2Size: " + Subchunk2Size);
      
      System.out.println("Duration: " + 1.0 * Subchunk2Size / ByteRate / 60);
      
      //skip dead space
      int count = 0;
      while(true)
      {
         String first = "";
         for(int i = 0; i < 8; i++)
         {
            first += file.readBit();
         }
         if(!first.equals("00000000"))
            break; 
         count++;
      }
      System.out.println("Dead Space: " + count + " Bytes");
      
      if(count % 2 != 1)
      {
         for(int i = 0; i < 8; i++)
         {
            file.readBit();
         }
      }
      System.out.println("Total Bytes: " + file.totalBytes());
      
/*****************************************************************************/
      
      int bpm = 100;
      double sixteenthPow = Math.log((60.0*SampleRate)/(bpm*4)) / Math.log(2);
      int pow = (int)(sixteenthPow);
      System.out.println("16th rate: " + sixteenthPow);
      int n = (int)Math.pow(2,pow);
      System.out.println("N: " + n);
      int numMeasures = 10;
      int NumLoops = 16 * numMeasures;                                  /******************Change number of loops******************/
      long totalSamples = 0;
      Complex[] y = new Complex[1];
      Complex[] right = new Complex[1];
      
      
      //n = (int)Math.pow(2,15);
      //n = (int)(60.0 / (4 * bpm) * SampleRate);  //Actual Size for one sixteenth note
      System.out.println("N: " + n);
      
      int extraSize = (int)(60.0 / (4 * bpm) * SampleRate) - n;
      
      for(int i = 0; i < extraSize; i++)
      {
         for(int b = 0; b < 16; b++)
         {
            file.readBit();
         }
         if(extraSize % 2 == 1)
         {
            for(int b = 0; b < 16; b++)
            {
               file.readBit();
            }
         }
      }
      
      
      // /* SKIP SOME DATA */
     //  
//       for(int t = 0; t < 50000; t++)
//       {
//          for(int i = 0; i < 8; i++)
//          {
//             file.readBit();
//          }
//       }
      
      /*******************/
      
      int timeStamp = 1;
      
      ArrayList<Note[]> song = new ArrayList<>();
      ArrayList<String> prevNotes = new ArrayList<>();
      
      for(int loops = 0; loops < NumLoops; loops++)
      { 

         
         System.out.println("Sixteenth #" + (loops+1));
      FFT transform = new FFT();
      Complex[] x = new Complex[n];
      Complex[] r = new Complex[n];
      try
      {
         File raw = new File("rawBytes.csv");
         File raw2 = new File("rawBytes2.csv");
         File complexFile = new File("comResults.csv");
         raw.createNewFile();
         raw2.createNewFile();
         complexFile.createNewFile();
         FileWriter writer1 = new FileWriter(raw);
         FileWriter writer2 = new FileWriter(raw2);
         FileWriter complexWriter = new FileWriter(complexFile);

        // original data
        for (int i = 0; i < 2 * n; i++) 
        {
            String first = "";
            int sign = 0;
            
         for(int k = 0; k < BitsPerSample; k++)
         {
            first = file.readBit() + first;
         }
         
         
            if(first.charAt(0) == '1')
            {
               sign = -1;
            }
            else
            {
               sign = 1;
            }
            
            
            // for(int z = BitsPerSample - 2; z > 0; z--)
//             {
//                flip += first.charAt(z);
//             }
            
            
            String negByte = "";
            if(sign == -1)
            {
               for(int t = 0; t < first.length(); t++)
               {
                  if(first.charAt(t) == '1')
                  {
                     negByte += "0";
                  }
                  else
                  {
                     negByte += "1";
                  }
               }
               first = negByte;
            }
         
            
            if(i % 2 == 0)
            {
               x[i/2] = new Complex(sign * Integer.parseInt(first, 2), 0);
               writer1.write(1.0 * timeStamp / SampleRate + "," + sign * Integer.parseInt(first, 2) +",\n");
            }
            else
            {
               r[i/2] = new Complex(sign * Integer.parseInt(first, 2), 0);
               writer2.write(1.0 * timeStamp / SampleRate + "," + sign * Integer.parseInt(first, 2) +",\n");
                
            }
            timeStamp++;
               
               
            
        }
        
       
          writer1.flush();
          writer1.close();
          writer2.flush();
          writer2.close();
          
          y = transform.fft(x);
          right = transform.fft(r); 
          // for(Complex c : y)
//           {
//             complexWriter.write(c.re() + "," + c.im() + "\n");
//           }
          complexWriter.flush();
          complexWriter.close();
       
  //      transform.show(x, "x");
        
        // FFT of original data (power of 2)
        
         //transform.show(y, "y = fft(x)");                //HERE IS THE FFT PRINT LINE
      }
      catch(Exception E)
      {
         System.out.println(E);
      }
      
      /* Skip Extra Bytes that dont fit in tranform */
      //System.out.println("ExtraSize: " + extraSize);
      // for(int i = 0; i < 2*extraSize; i++)
//       {
//          for(int b = 0; b < 16; b++)
//          {
//             file.readBit();
//          }
//       }
      for(int i = 0; i < extraSize; i++)
      {
         for(int b = 0; b < 16; b++)
         {
            file.readBit();
         }
         if(extraSize % 2 == 1)
         {
            for(int b = 0; b < 16; b++)
            {
               file.readBit();
            }
         }
      }
      /*************************************************/

/************************************************************************************************/
     
        double max = 0;
        Complex[] complexData = y;
        
        double total = 0;
        double average;
        int spikes = 0;
        double length = 0;
        double T = 1.0 / SampleRate;
        double threshold = 0;
        
        frequency = 0.0;  //Thank you Dutch
        double FRes =  1.0 * SampleRate / n;
        
        
        //flatten the noise in the graph
        for(int i = 1; i < complexData.length/2; i++)
        {
         frequency = i * FRes;
         if(max < complexData[i].abs())
         {
            max = complexData[i].abs();
         }
        }
        threshold = max * 0.4;//tolerance 
        
        System.out.println("Threshold: " + threshold);
        for(int i = 1; i < complexData.length/2; i++)
        {
         if(complexData[i].abs() + threshold < max || (complexData[i-1].abs() > complexData[i].abs() || complexData[i+1].abs() > complexData[i].abs()) || complexData[i].abs() < 50000)
         {
            complexData[i] = new Complex(0,0);
         }
        } 

/****************************************************************************************************************************************************/        
//using phase()     
      // System.out.println("(1)Phase\n(2)Abs");
//       int choice = kb.nextInt();
//       
//       
//       if(choice == 1)
//       {
//       File test = new File("PhaseData.csv");
//          
//       
//       
//       creates the file
//       try
//       {
//       test.createNewFile();
//       FileWriter writer = new FileWriter(test);
//       
//       
//         
//         
//         System.out.println("Frequency Resolution: " + FRes);
//         System.out.println("Test " + 1.0 * SampleRate / n);
//         System.out.println("Multiplier: " + FMultiplier);
//         for(int i = 1; i < complexData.length/2; i++)                                           
//         {
//          if(complexData[i].re() != 0 || complexData[i].im() != 0)
//          {
//             System.out.println("i = " + i);
//             System.out.println("frequency: " + i * FMultiplier);
//            frequency = i * FRes;
//            if(frequency > 3000)
//            {
//             System.out.println("broke for " + Math.floor(frequency*100)/100);
//             break;
//            }
//            if(frequency > 20)
//            {
//             System.out.println(complexData[i].phase() * 180/Math.PI + " at " + Math.floor((frequency)*100)/100 + " Hz or " + getNoteName(frequency));
// 
//                writer.write(Math.floor((frequency)*100)/100 + "," + complexData[i].phase() * 180/Math.PI+ "\n"); 
//              
// 
//             spikes++;
//            }
//          }
//         }   //Can't figure out how to get the correct resolution and adjust for the error
//         
//         
//         System.out.println("Spikes: " + spikes);
//          writer.flush();
//          writer.close();
//          totalSamples += n;
//          
//          }
//       catch(Exception E)
//       {
//          System.out.println("caught something");
//          System.out.println(E);
//       }
//          
//       }   
//       else if(choice == 2)
//       {   
      File test = new File("AbsData.csv");
      
/************************************************************************************************************************/
//Using abs() instead of phase()         

         complexData = y;
         total = 0;
         spikes = 0;
         length = 0;
         threshold = 0;
         
         
            try
            {
            test.createNewFile();
            FileWriter writer = new FileWriter(test);
            
         
         int numPeaks = 0;
         song.add(new Note[88]);
         
         ArrayList<String> currNotes = new ArrayList<>();
         for(int i = 1; i < complexData.length/2;i++)
         {
            if(complexData[i-1].abs() > complexData[i].abs() || complexData[i+1].abs() > complexData[i].abs())
            {
               complexData[i] = new Complex(0,0);
            }
  //          System.out.println(complexData[i]);
            if(complexData[i].re() != 0 || complexData[i].im() != 0)
            {
    //           System.out.println(complexData[i].phase() * 180/Math.PI);
               
                 frequency = i * FRes;
                 if(frequency > 3000)
                 {
                  System.out.println("broke for " + Math.floor(frequency*100)/100);
                  break;
                 }
                 if(frequency > 20)
                 {
                  System.out.println(complexData[i].abs() + " at " + Math.floor((frequency)*100)/100 + " Hz or " + getNoteName(frequency,freqMap));
                  writer.write(Math.floor((frequency)*100)/100 + "," + complexData[i].abs() + "\n");
                  Note note = new Note(getNoteName(frequency,freqMap),1);

                  song = addNote(loops,note,song,indexMap.get(note.toString()));
                  currNotes.add(note.toString());
                  
                 }
                 spikes++;
               
            }
               
         }
         System.out.println("Spikes: " + spikes);
            writer.flush();
         writer.close();
         totalSamples += n;
         
         for(String old : prevNotes)
         {
            if(!currNotes.contains(old))
            {
               song.get(loops-1)[indexMap.get(old)].setEnd();
            }
         }
         
         prevNotes = currNotes;
         if(currNotes.size() == 0) //indicator that the beat is empty
         {
            song.get(loops)[0] = new Note("H",0);
         }
         
         }
      catch(Exception E)
      {
         System.out.println("caught something");
         System.out.println(E);
         E.printStackTrace();
         
      }
   
   
   
   
         //}  
   
         
 //beat detection**************************************************************
   // int W = 2048;
//    int M = 50;
//    double avgX = 0;
//    for(int i = 0; i < M; i++)
//    {
//       avgX += Math.pow(x[i].re(),2);
//    }
//    
//    double powCon = 0.8;
//    double[] powArray = new double[W];
//    double prev = avgX;
//    
//    for(int i = 0; i < W; i++)
//    {
//       powArray[i] = powCon * prev * (1-powCon) * avgX;
//       prev = powArray[i];
//    }
   
  //  for(int i = 0; i < W; i++)
//    {
//       System.out.println(powArray[i]);
//    }
//    System.out.println(avgX);
        
         
         
         
 }
        System.out.println("Seconds of Audio: " + 1.0 * totalSamples / SampleRate);
        
        File testScore = new File("FirstScore.xml");
        
        Map<Integer,String> noteType = new TreeMap<>();
        noteType.put(1,"sixteenth");
        noteType.put(2,"eighth");
        noteType.put(4,"quarter");
        noteType.put(8,"half");
        noteType.put(16,"whole");
        
        try
        {
         FileWriter scoreWriter = new FileWriter(testScore);
         scoreWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE score-partwise PUBLIC\n \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\"\n \"http://www.musicxml.org/dtds/partwise.dtd\">\n<score-partwise version=\"3.1\">\n <part-list>\n <score-part id=\"P1\">\n <part-name>Music</part-name>\n </score-part>\n </part-list>\n <part id=\"P1\">\n <measure number=\"1\">\n <attributes>\n <divisions>1</divisions>\n <key>\n <fifths>0</fifths>\n </key>\n <time>\n <beats>4</beats>\n <beat-type>4</beat-type>\n </time>\n <clef>\n <sign>G</sign>\n <line>2</line>\n </clef>\n </attributes>");
         int measureNum = 1;
         count = 1;
         
         for(int measure = 0; measure < (int)(Math.ceil(song.size()/16.0)); measure++)
         {
            if(measure != 0)
            {
               scoreWriter.write("\n<measure number=\"" + (measure+1) + "\">");
               
            }
            for(int beat = 0; beat < 4; beat++)
            {
               for(int sub = 0; sub < 4; sub++)
               {  
                  boolean isChord = false;
                  // if(measure + 1 == 5)
//                   {
//                      System.out.println("yes");
//                   }
                  int index = measure*16+beat*4+sub;
                  
                  for(Note note : song.get(index))
                  {  
                     if(note != null && !note.toString().equals("H"))
                     {
                        
                     if(measure + 1 == 5)
                     {
                        System.out.println("yes");
                     }
                     scoreWriter.write("\n <note>");
                     
                     if(isChord)
                     {
                        scoreWriter.write("\n <chord/>");
                     }
                     else
                     {
                        isChord = true;
                     }
                  
                     scoreWriter.write("\n  <pitch>");
                     scoreWriter.write("\n   <step>" + note.toString().charAt(0)+ "</step>");
                     if(note.toString().length() == 3)
                     {
                        scoreWriter.write("\n    <alter>" + "-1" + "</alter>");
                     }
                     scoreWriter.write("\n   <octave>" + note.toString().charAt(note.toString().length()-1) + "</octave>");
                     scoreWriter.write("\n  </pitch>");
                     
                     int duration = 0;
                     int spot = 0;
                     boolean isEnd = false;
                     int noteSpot = indexMap.get(note.toString());
                     while(!isEnd)
                     {
                        if(index+spot == song.size())
                        {
                           break;
                        }
                        if(song.get(index+spot)[noteSpot] != null && song.get(index+spot)[noteSpot].isEndNote())
                        {
                           isEnd = true;
                        }
                        if(song.get(index+spot)[noteSpot] == null)
                        {
                           break;
                        }
                        spot++;
                        duration++;
                     }
                     
                     
                     
                     if(beat*4+sub + duration <= 16)   //if it fits in the measure
                     {
                        scoreWriter.write("\n  <duration>" + duration / 4.0 + "</duration>");
                        if(note.isEndTie())
                        {
                           scoreWriter.write("\n  <tied type=\"stop\"/>");
                        }
                        scoreWriter.write("\n  <type>" + noteType.get(duration) + "</type>");
                        if(note.isEndTie())
                        {
                           scoreWriter.write("\n  <notations>\n   <tied type=\"stop\"/>\n  </notations>");
                        }
                        
                        for(int i = 0; i < duration; i++)   //remove not from song arraylist
                        {
                           song.get(index+i)[noteSpot] = null;
                        }
                     }
                     else
                     {
                        int length = 16 - beat*4+sub;
                        note.setStartTie();
                        for(int i = 1; i < length; i++)   //remove not from song arraylist
                        {
                           song.get(index+i)[noteSpot] = null;
                        }
                        if(index+length < song.size() && song.get(index+length)[noteSpot] != null)
                        {
                           song.get(index+length)[noteSpot].setEndTie();
                        }
                        scoreWriter.write("\n  <duration>" + length / 4 + "</duration>");
                        if(note.isEndTie())
                        {
                           scoreWriter.write("\n  <tied type=\"stop\"/>");
                        }
                        if(note.isStartTie())
                        {
                           scoreWriter.write("\n  <tied type=\"start\"/>");
                        }
                        if(note.isEndTie() || note.isStartTie())
                        {
                           scoreWriter.write("\n  <notations>");
                           if(note.isEndTie())
                           {
                              scoreWriter.write("\n   <tied type=\"stop\"/>");
                           }
                           if(note.isStartTie())
                           {
                              scoreWriter.write("\n   <tied type=\"start\"/>");
                           }
                           scoreWriter.write("\n  </notations>");
                        }
                        
                        scoreWriter.write("\n  <type>" + noteType.get(length) + "</type>");
                        
                        
                        // ArrayList<Double> tiedBeats = new ArrayList<>();
//                         double length = duration;
//                         for(int i = 0; i < duration; i++)   //remove not from song arraylist
//                         {
//                            song.get(index+i)[noteSpot] = null;
//                         }
//                         double prevDur = beat*4+sub+1;
//                         double remain = 16 - prevDur;
//                         for(double i = 4; i >=.25; i/=2)
//                         {
//                            double tempLength = length / i;
//                            if(tempLength >= 1 && tempLength +prevDur <= 16)
//                            {
//                               tiedBeats.add(i);
//                               length -= i;
//                               if(i+prevDur == 16)
//                               {
//                                  i = 8;
//                                  prevDur=0;
//                               }
//                            }
//                            
//                            if(length == 0)
//                            {
//                               break;
//                            }
//                         }
//                         
//                         scoreWriter.write("\n<duration>" + tiedBeats.get(0) + "</duration>");
// 
//                         scoreWriter.write("\n<type>" + noteType.get((int)(tiedBeats.get(0) * 4)) + "</type>");
//                  
//                            
//                         
//                         scoreWriter.write("\n<tie type=\"start\">");
//                         scoreWriter.write("\n</note>");
//                         scoreWriter.write("\n</measure>");
//                         int tempMeasure = measure+1;
//                         beat = 0;
//                         sub = 0;
//                         scoreWriter.write("\n<measure number=\"" + (tempMeasure+1) + "\">");
//                         
//                         int numLoops = tiedBeats.size()-1;
//                         for(int i = 1; i < numLoops; i++)
//                         {
//                            scoreWriter.write("\n<note>");
//                            scoreWriter.write("\n<pitch>");
//                            scoreWriter.write("\n<step>" + note.toString().charAt(0)+ "</step>");
//                            if(note.toString().length() == 3)
//                            {
//                               scoreWriter.write("\n<alter>" + "-1" + "</alter>");
//                            }
//                            scoreWriter.write("\n<octave>" + note.toString().charAt(note.toString().length()-1) + "</octave>");
//                            scoreWriter.write("\n</pitch>");
//                            
//                            scoreWriter.write("\n<duration>" + tiedBeats.get(0) + "</duration>");
//                            scoreWriter.write("\n<type>" + noteType.get(i) + "</type>");
//                            scoreWriter.write("\n<tie type=\"stop\">");
//                            scoreWriter.write("\n<tie type=\"start\">");
//                            scoreWriter.write("\n</note>");
//                         }
//                            scoreWriter.write("\n<note>");
//                            scoreWriter.write("\n<pitch>");
//                            scoreWriter.write("\n<step>" + note.toString().charAt(0)+ "</step>");
//                            if(note.toString().length() == 3)
//                            {
//                               scoreWriter.write("\n<alter>" + "-1" + "</alter>");
//                            }
//                            scoreWriter.write("\n<octave>" + note.toString().charAt(note.toString().length()-1) + "</octave>");
//                            scoreWriter.write("\n</pitch>");
//                            
//                            scoreWriter.write("\n<duration>" + tiedBeats.get(0) / 4.0 + "</duration>");
//                            scoreWriter.write("\n<type>" + noteType.get((int)(4 * tiedBeats.get(tiedBeats.size()-1))) + "</type>");
//                            scoreWriter.write("\n<tie type=\"stop\">");
//                            scoreWriter.write("\n</note>");
                        
                     }  //end of else
                     
                     scoreWriter.write("\n</note>");
                        
                     }
                  }//note loop end
                  if(!isChord && song.get(index)[0] != null && song.get(index)[0].toString().equals("H"))
                  {
                     scoreWriter.write("\n<note>");
                     scoreWriter.write("\n<rest>\n</rest>");
                     boolean isEnd = false;
                     int duration = 0;
                     int spot = 0;
                     while(!isEnd)
                     {
                        if(index+spot == song.size())
                        {
                           break;
                        }
                        if(song.get(index+spot)[0] != null && index+spot+1 < song.size() && song.get(index+spot+1) != null && song.get(index+spot+1)[0] != null && !song.get(index+spot+1)[0].toString().equals("H"))
                        {
                           isEnd = true;
                        }
                        if(song.get(index+spot)[0] == null)
                        {
                           break;
                        }
                        spot++;
                        duration++;
                     }
                     
                     for(int i = 0; i < duration; i++)   //remove not from song arraylist
                     {
                        song.get(index+i)[0] = null;
                     }
                     if(beat*4+sub + duration <= 16)   //if it fits in the measure
                     {
                        scoreWriter.write("\n<duration>" + duration / 4.0 + "</duration>");
                        scoreWriter.write("\n<type>" + noteType.get(duration) + "</type>");
                     }
                     else
                     {
                        
                     }
                     scoreWriter.write("\n</note>");
                  }
                  
               }//sub loop end
            }//beat loop end 
            scoreWriter.write("\n</measure>");          
         }//measure loop end
         
         scoreWriter.write("\n</part>");
         scoreWriter.write("\n</score-partwise>");
         
         
         scoreWriter.flush();
         scoreWriter.close();
        }
        catch(Exception E)
        {
         System.out.println(E);
         E.printStackTrace();
        }
        
        
        
        
        
        // try
//         {
//          FileWriter scoreWriter = new FileWriter(testScore);
//          scoreWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE score-partwise PUBLIC\n \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\"\n \"http://www.musicxml.org/dtds/partwise.dtd\">\n<score-partwise version=\"3.1\">\n <part-list>\n <score-part id=\"P1\">\n <part-name>Music</part-name>\n </score-part>\n </part-list>\n <part id=\"P1\">\n <measure number=\"1\">\n <attributes>\n <divisions>1</divisions>\n <key>\n <fifths>0</fifths>\n </key>\n <time>\n <beats>4</beats>\n <beat-type>4</beat-type>\n </time>\n <clef>\n <sign>G</sign>\n <line>2</line>\n </clef>\n </attributes>");
//          
//          int restAmount = 0;
//          for(int beat = 0; beat < song.size(); beat++)
//          {
//             if(beat % 16 == 1 && beat > 16)
//             {
//                scoreWriter.write("\n</measure>");
//                scoreWriter.write("\n<measure number=\"" + (beat/16 + 1) + "\">");
//             }
//             int numNotes = 0;
//             boolean needRest = false;
//             for(Note note : song.get(beat))
//             {
//                if(note != null)
//                {
//                   // if(restAmount != 0)
// //                   {
// //                      scoreWriter.write("\n<note>");
// //                      scoreWriter.write("\n<rest>\n</rest>");
// //                      scoreWriter.write("\n<duration>" + (restAmount) / 4.0 + "</duration>");
// //                      restAmount = 0;
// //                   }
//                   numNotes++;
//                   scoreWriter.write("\n<note>");
//                   if(numNotes > 1)
//                   {
//                      scoreWriter.write("\n<chord/>");
//                   }
//                   scoreWriter.write("\n<pitch>");
//                   scoreWriter.write("\n<step>" + note.toString().substring(0,1) + "</step>");
//                   if(note.toString().length() == 3)
//                   {
//                      scoreWriter.write("\n<alter>-1</alter>");
//                   }
//                   scoreWriter.write("\n<octave>" + note.toString().substring(note.toString().length()-1,note.toString().length()) + "</octave>");
//                   scoreWriter.write("\n</pitch>");
//                   int length = 0;
//                   boolean isEnd = false;
//                   int index = indexMap.get(note.toString());
//                   int spot = 0;
//                   while(!isEnd)
//                   {
//                      if(beat+spot == song.size())
//                      {
//                         break;
//                      }
//                      if(song.get(beat+spot)[index] != null && song.get(beat+spot)[index].isEndNote())
//                      {
//                         isEnd = true;
//                      }
//                      if(song.get(beat+spot)[index] == null)
//                      {
//                         break;
//                      }
//                      spot++;
//                      length++;
//                      
//                   }
//                   for(int i = 0; i < length; i++)
//                   {
//                      song.get(beat+i)[index] = null;
//                   }
//                   scoreWriter.write("\n<duration>" + length/4 + "</duration>");
//                   scoreWriter.write("\n<type>");
//                   if(length == 4)
//                   {
//                      scoreWriter.write("quarter");
//                   }
//                   else if(length == 16)
//                   {
//                      scoreWriter.write("whole");
//                   }
//                   // else if(length == 24)
// //                   {
// //                      scoreWriter.write("whole");
// //                   }
//                   scoreWriter.write("</type>");
//                   scoreWriter.write("\n</note>");
//                }
//             }
//             // if(numNotes == 0)
// //             {
// //                restAmount++;
// //             }
//             
//          }
//          scoreWriter.write("\n</measure>");
//          scoreWriter.write("\n</part>");
//          scoreWriter.write("\n</score-partwise>");
//          scoreWriter.flush();
//          scoreWriter.close();
//         }
//         catch(Exception E)
//         {
//          System.out.println(E);
//         }

   
   
   //Testing note translater
  //  for(int i = 0; i < 10; i++)
//    {
//       double ranF = Math.random() * 3000;
//       System.out.println(ranF + " is " + getNoteName(ranF));
//    }
      
      
/************************Graphics playground************************************/
// Frame f = new Frame("Hello");
// Panel p = new Panel();
// Button b = new Button("Press");
// p.add(b); 
// f.setBounds(new Rectangle(800,400));
// Label lab = new Label("Sup", Label.CENTER);
// lab.setSize(600,300);
// f.add(p);
// f.add(lab);
// f.setVisible(true);
// try
// {
// Thread.sleep(5000);
// }
// catch(Exception E)
// {}
// System.exit(0);


//Minim minim = new Minim("Something");


//MyPApplet newApplet = new MyPApplet();


      
      
      // creates a FileWriter Object
      
      
      // Writes the content to the file










        // Note testNote = new Note(440,4);
//         System.out.println(testNote);

        // FFT of original data (does not matter the size but slower)
 //        Complex[] y2 = transform.dft(x);
//         transform.show(y2, "y2 = dft(x)");


      
//         beat detection
//         time signature detect
//         reset
//         for (every set interval)
//         {    
//          figure out frequency with fft
//          figure out duration
//          make new note
//          add to note list
//         }  
//         convert to sheet music



      // JFrame f = new JFrame("A JFrame");
//     f.setSize(250, 250);
//     f.setLocation(300,200);
//     final JTextArea textArea = new JTextArea(10, 40);
//     f.getContentPane().add(BorderLayout.CENTER, textArea);
//     final JButton button = new JButton("Click Me");
//     f.getContentPane().add(BorderLayout.SOUTH, button);
//     button.addActionListener(new ActionListener() {
// 
//         @Override
//         public void actionPerformed(ActionEvent e) {
//             textArea.append("Button was clicked\n");
// 
//         }
//     });
// 
//     f.setVisible(true);


       //  BufferedImage image = ImageIO.read(new File("Quarter Note.PNG"));
//         JFrame frame = new JFrame("My Drawing");
//         Canvas canvas = new AudioConverter();
//         canvas.setSize(400, 400);
//         frame.add(canvas);
//         frame.pack();
//         frame.setVisible(true);

      

   }
   
   // public void paint(Graphics g) 
//    {
//       Graphics2D art = (Graphics2D)g;
//       art.rotate(Math.toRadians(45));
//         art.fillOval(-100, -100, 150, 300);
//         
//    }
    
   public static String getNoteName(double frequency)
   {
      double round = .01 * frequency;
      if(frequency % 32.7 < round && checkPow(frequency,32.7))
         {
//          System.out.println(Math.round(Math.log(frequency/32.7) / Math.log(2)));
//          System.out.println(frequency);
//          System.out.println(frequency/32.7);
//          System.out.println(frequency % 32.7);
//          System.out.println(round);
         return "C" + (int)(Math.round(Math.log(frequency / 32.7)/ Math.log(2)) + 1);
         }
      else if(frequency % 34.65 < round && checkPow(frequency,34.65))
      {
       //   System.out.println(frequency);
//          System.out.println(frequency/34.65);
//          System.out.println(Math.log(frequency / 34.65)/ Math.log(2));
         
         return "Db" + (int)(Math.round(Math.log(frequency / 34.65)/ Math.log(2)) + 1);
      }
      else if(frequency % 36.71 < round && checkPow(frequency,36.71))
         return "D" + (int)(Math.round(Math.log(frequency / 36.71)/ Math.log(2)) + 1);
      else if(frequency % 38.89 < round && checkPow(frequency,38.89))
         return "Eb" + (int)(Math.round(Math.log(frequency / 38.89)/ Math.log(2)) + 1);
      else if(frequency % 41.2 < round && checkPow(frequency,41.2))
         return "E" + (int)(Math.round(Math.log(frequency / 41.2)/ Math.log(2)) + 1);
      else if(frequency % 43.65 < round && checkPow(frequency,43.65))
         return "F" + (int)(Math.round(Math.log(frequency / 43.65)/ Math.log(2)) + 1);
      else if(frequency % 46.25 < round && checkPow(frequency,46.25))
         return "Gb" + (int)(Math.round(Math.log(frequency / 46.25)/ Math.log(2)) + 1);
      else if(frequency % 49 < round && checkPow(frequency,49))
         return "G" + (int)(Math.round(Math.log(frequency / 49)/ Math.log(2)) + 1);
      else if(frequency % 51.91 < round && checkPow(frequency,51.91))
         return "Ab" + (int)(Math.round(Math.log(frequency / 51.91)/ Math.log(2)) + 1);
      else if(frequency % 55 < round && checkPow(frequency,55))
         return "A" + (int)(Math.round(Math.log(frequency / 55)/ Math.log(2)) + 1);
      else if(frequency % 58.27 < round && checkPow(frequency,58.27))
         return "Bb" + (int)(Math.round(Math.log(frequency / 58.27)/ Math.log(2)) + 1);
      else if(frequency % 61.74 < round && checkPow(frequency,61.74))
         return "B" + (int)(Math.round(Math.log(frequency / 61.74)/ Math.log(2)) + 1);
      return "error";
   }
   
   public static String getNoteName(double f, Map<Double, String> noteType)
   {
      double last = 1;
      for(double d : noteType.keySet())
      {
         if(Math.abs((f-d)/d) * 100 < 5 && Math.abs((f-d)/d) * 100 < Math.abs((f-last)/last) * 100)
         {
            last = d;
         }
      }
      return noteType.get(last);
   }
   
   private static boolean checkPow(double frequency, double base)
   {
      double round = .05;
//       System.out.println(frequency);
//       System.out.println(frequency/base);
//       System.out.println(Math.log(frequency/base) / Math.log(2));
//       System.out.println(Math.log(Math.log(frequency/base) / Math.log(2))/Math.log(2));
//       System.out.println("Power: " + Math.log(Math.round(Math.log(frequency/base) / Math.log(2)))/Math.log(2));
      return Math.log(frequency/base) / Math.log(2) % 1 > 1 - round || Math.log(frequency/base) / Math.log(2) % 1 < round;
   }
   
   public static ArrayList<Note[]> addNote(int beat,Note n,ArrayList<Note[]> song, int num)
   {
      // if(song.get(beat)[num] != null && n.toString().equals(song.get(beat)[num].toString()))
//       {
//          return song;
//       }

//       for(int i = 0; i < n.getLength(); i++)
//       {
         if(song.get(beat) == null)
         {
            song.add(new Note[88]);
         }
         song.get(beat)[num] = n;
      // }
      return song;

      
      
   }
   
   
   
}

// public class MyPApplet extends PApplet
// {
//    public void settings()
//    {
//       size(450,300);
//    }
// }