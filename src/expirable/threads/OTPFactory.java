package expirable.threads;

import java.util.ArrayList;

public class OTPFactory extends ArrayList<OTP> {

  private static final long serialVersionUID = 4223793387162961567L;
  private static OTPFactory instance;
  public static final int OBJECT_LIFE_TIME = 5;
  public static final int DIGIT_LENGTH = 6;

  public static final OtpGenerator generator = new OtpGenerator();

  public static OTPFactory init() {
    return instance = new OTPFactory();
  }

  public static OTPFactory getInstance() {
    return instance;
  }

  public String Generate() {
    add(new OTP(this));

    return generator.generateNumber(DIGIT_LENGTH);
  }

}
