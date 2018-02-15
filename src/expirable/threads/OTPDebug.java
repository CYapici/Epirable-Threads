package expirable.threads;

class OTPDebug implements Runnable {

  public static int myCount = 0;

  public void run() {

    OTPFactory listener = new OTPFactory();
    while (true) {

      System.err.println(":: total size is ::" + listener.size());

      System.out.println(":: generated is ::" + listener.Generate());

      System.err.println(":: new  size is ::" + listener.size());

      try {
        Thread.sleep(6000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
