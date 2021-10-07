import java.util.*;
import java.io.*;
public class Note
{
   private double length;
   private String name;
   private boolean endNote;
   private boolean startTie;
   private boolean endTie;
   
   public Note(String n, double l)
   {
      length = l;
      name = n;
      endNote = false;
      startTie = false;
      endTie = false;
      
   }   
   
   public double getLength()
   {
      return length;
   }
   
   public void changeLength(int l)
   {
      length = l;
   }
   
   public boolean isEndNote()
   {
      return endNote;
   }
    
   public void setEnd()
   {
      endNote = true;
   } 
     
   public String toString()
   {
      return name;
   }
   
   public void setStartTie()
   {
      startTie = true;
   }
   
   public void setEndTie()
   {
      endTie = true;
   }
   
   public boolean isStartTie()
   {
      return startTie;
   }
   
   public boolean isEndTie()
   {
      return endTie;
   }
}