//
// Copyright (c) 2008 Tridium, Inc.
// Licensed under the Academic Free License version 3.0
//
// History:
//   17 Nov 08  Brian Frank  Creation
//

package sedona.vm.sys;

import java.io.*;
import sedona.vm.*;

/**
 * sys::FileStore native methods
 */              
public class FileStore_n
{

  public static int doSize(StrRef name, Context cx)             
  {            
    return (int)toFile(name, cx).length();
  }
  
  public static Object doOpen(StrRef name, StrRef m, Context cx)
  {                    
    try
    {
      File f = toFile(name, cx);        
      String mode = m.toString();
      if (mode.equals("w")) mode = "rw";
      if (mode.equals("m")) mode = "rw";
      return new RandomAccessFile(f, mode);
    }
    catch (IOException e)
    {
      System.out.println("File_n.doOpen: " + e); 
      return null;
    }
  }
  
  public static int doRead(Object fp, Context cx)
  {          
    try
    {
      return ((RandomAccessFile)fp).read();
    }
    catch (IOException e)
    {
      System.out.println("File_n.doRead: " + e); 
      return -1;
    }
  }
  
  public static int doReadBytes(Object fp, byte[] b, int off, int len, Context cx)  
  { 
    try
    {                                   
      return ((RandomAccessFile)fp).read(b, off, len);
    }
    catch (IOException e)
    {
      System.out.println("File_n.doReadBytes: " + e); 
      return 0;
    }
  }
  
  public static byte doWrite(Object fp, int b, Context cx)
  {
    try
    {
      ((RandomAccessFile)fp).write(b);
      return 1;
    }
    catch (IOException e)
    {
      System.out.println("File_n.doWrite: " + e); 
      return 0;
    }
  }
  
  public static byte doWriteBytes(Object fp, byte[] b, int off, int len, Context cx)
  {
    try
    {
      ((RandomAccessFile)fp).write(b, off, len);
      return 1;
    }
    catch (IOException e)
    {
      System.out.println("File_n.doWriteBytes: " + e); 
      return 0;
    }
  }
  
  public static byte doSeek(Object fp, int pos, Context cx)
  {           
    try
    {
      ((RandomAccessFile)fp).seek(pos);
      return 1;
    }
    catch (IOException e)
    {
      System.out.println("File_n.doSeek: " + e); 
      return 0;
    }
  }
  
  public static int doTell(Object fp, Context cx)
  {
    try
    {
      return (int)((RandomAccessFile)fp).getFilePointer();
    }
    catch (IOException e)
    {
      System.out.println("File_n.doTell: " + e); 
      return 0;
    }
  }
  
  public static void doFlush(Object fp, Context cx)
  {                     
    try
    {
      ((RandomAccessFile)fp).getFD().sync();
    }
    catch (IOException e)
    {
      System.out.println("File_n.doFlush: " + e);
    }
  }
  
  public static byte doClose(Object fp, Context cx)
  {
    try
    {
      ((RandomAccessFile)fp).close();
      return 1;
    }
    catch (IOException e)
    {
      System.out.println("File_n.doClose: " + e);
      return 0;
    }
  }
 
  static File toFile(StrRef name, Context cx)
  {
    return new File(name.toString());
  }
  

  public static boolean rename(StrRef from, StrRef to, Context cx)
  {      
    File fromFile = new File(from.toString());
    File toFile = new File(to.toString());
    return fromFile.renameTo(toFile);
  }
  
}

